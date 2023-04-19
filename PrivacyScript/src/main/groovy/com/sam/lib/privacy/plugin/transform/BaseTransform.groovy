package com.sam.lib.privacy.plugin.transform

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.sam.lib.privacy.plugin.util.CommonUtil
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.gradle.api.Project

import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream

/**
 * @author wanghao
 * @date 2023/4/17.
 * @des
 */
abstract class BaseTransform extends Transform {

    Project project

    BaseTransform(Project project) {
        this.project = project
        println "-----注入了 ${getClass().simpleName} ----"
    }

    @Override
    String getName() {
        return getClass().simpleName
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        long startTime = System.currentTimeMillis()
        println("${getName()} start--------------->")
        // Transform的inputs有两种类型，一种是目录，一种是jar包，要分开遍历
        def outputProvider = transformInvocation.getOutputProvider()
        def inputs = transformInvocation.inputs

        startTransform()

        inputs.each { input ->

            input.directoryInputs.each { dirInput ->
//                println("forEachDir ------> " + dirInput + "         , TransformOutputProvider: " + outputProvider)
                forEachDir(dirInput, outputProvider)
            }

            input.jarInputs.each {
//                println("forEachJar ------> " + it + "         , TransformOutputProvider: " + outputProvider)
                forEachJar(it, outputProvider)
            }

        }
        endTransform()
        println("${getName()} end --------------- use time -> ${System.currentTimeMillis() - startTime} ms")
    }

    void forEachDir(DirectoryInput input, TransformOutputProvider out) {
        def dir = input.file
        File dest = out.getContentLocation(input.name, input.contentTypes, input.scopes, Format.DIRECTORY)
        def srcDirPath = dir.absolutePath
        def destDirPath = dest.absolutePath
        com.android.utils.FileUtils.getAllFiles(dir).each { File file ->
            transformClassFile(file, srcDirPath, destDirPath)
        }
    }

    void forEachJar(JarInput jarInput, TransformOutputProvider outputProvider) {
        //防止同名被覆盖
        File destFile = outputProvider.getContentLocation(CommonUtil.generateJarFileName(jarInput.file), jarInput.contentTypes, jarInput.scopes, Format.JAR)
        if (destFile.exists()) {
            FileUtils.forceDelete(destFile)
        }
        CommonUtil.isLegalJar(jarInput.file) ? transformJar(jarInput.file, destFile) : FileUtils.copyFile(jarInput.file, destFile)
    }

    void transformClassFile(File classFile, String srcDirPath, String destDirPath) {
        //目标路径
        def destFilePath = classFile.absolutePath.replace(srcDirPath, destDirPath)
        def destFile = new File(destFilePath)
        if (destFile.exists()) {
            destFile.delete()
        }
        String className = CommonUtil.path2ClassName(classFile.absolutePath.replace(srcDirPath + File.separator, ""))
        byte[] sourceBytes = IOUtils.toByteArray(new FileInputStream(classFile))
        def modifyBytes = null
        if (CommonUtil.isLegalClass(classFile) && shouldHookClass(className)) {
            modifyBytes = hookClass(className, sourceBytes)
        }
        if (modifyBytes == null) {
            modifyBytes = sourceBytes
        }
        FileUtils.writeByteArrayToFile(destFile, modifyBytes, false)
    }

    void transformJar(File jarFile, File destFile) {
        JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(destFile))
        JarFile inputJarFile = new JarFile(jarFile, false)
        try {
            def entries = inputJarFile.entries()
            while (entries.hasMoreElements()) {
                def jarEntry = entries.nextElement()
                def entryName = jarEntry.getName()
                def inputStream = inputJarFile.getInputStream(jarEntry)
                try {
                    byte[] sourceByteArray = IOUtils.toByteArray(inputStream)
                    def modifiedByteArray = null
                    if (!jarEntry.isDirectory() && CommonUtil.isLegalClass(entryName)) {
                        String className = CommonUtil.path2ClassName(entryName)
                        if (shouldHookClass(className)) {
                            modifiedByteArray = hookClass(className, sourceByteArray)
                        }
                    }
                    if (modifiedByteArray == null) {
                        modifiedByteArray = sourceByteArray
                    }
                    jarOutputStream.putNextEntry(new JarEntry(entryName))
                    jarOutputStream.write(modifiedByteArray)
                    jarOutputStream.closeEntry()
                } finally {
                    IOUtils.closeQuietly(inputStream)
                }
            }
        } finally {
            jarOutputStream.flush()
            IOUtils.closeQuietly(jarOutputStream)
            IOUtils.closeQuietly(inputJarFile)
        }
    }

    boolean shouldHookClass(String className) {
        //默认过滤 androidx、android.support
//        println("className  -> " + className)
        def excludes = ['android.support', 'androidx']
        if (excludes != null) {
            for (String string : excludes) {
                if (className.startsWith(string)) {
                    return false
                }
            }
        }
        return shouldHookClassInner(className)
    }

    byte[] hookClass(String className, byte[] sourceBytes) {
        byte[] classBytesCode
        try {
            classBytesCode = hookClassInner(className, sourceBytes)
        } catch (Throwable e) {
            e.printStackTrace()
            classBytesCode = null
            println "throw exception when modify class ${className}"
        }
        return classBytesCode
    }

    protected abstract boolean shouldHookClassInner(String className)

    protected abstract byte[] hookClassInner(String className, byte[] bytes)

    protected abstract void startTransform()

    protected abstract void endTransform()


}
