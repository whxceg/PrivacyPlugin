package com.sam.lib.privacy.plugin.util

import com.sam.lib.privacy.plugin.method.MethodReplaceHolder
import org.apache.commons.codec.digest.DigestUtils
import org.objectweb.asm.tree.MethodNode;

/**
 * @author wanghao
 * @date 2023/4/14.
 * @des
 */
class CommonUtil {

    static String path2ClassName(String pathName) {
        return pathName.replace(File.separator, ".").replace(".class", "")
    }

    static boolean isLegalClass(File file) {
        return (file.isFile() && isLegalClass(file.name))
    }

    static boolean isLegalClass(String name) {
        return (name.endsWith(".class") && !isAndroidGeneratedClass(name))
    }

    private static boolean isAndroidGeneratedClass(String className) {
        return (className.contains('R$') ||
                className.contains('R2$') ||
                className.contains('R.class') ||
                className.contains('R2.class') ||
                className == 'BuildConfig.class')
    }

    static boolean isLegalJar(File file) {
        //需要一个额外的括号，否则编译不通过
        return (file.isFile()
                && file.name != "R.jar"
                && file.length() > 0L
                && file.name.endsWith(".jar"))
    }

    static String generateJarFileName(File jarFile) {
        return getMd5ByFilePath(jarFile) + "_" + jarFile.name
    }

    private static getMd5ByFilePath(File file) {
        return DigestUtils.md5Hex(file.absolutePath).substring(0, 8)
    }

    static String getClassInternalName(String name) {
        return name.replace(".", File.separator)
    }

    /**
     * 过滤有注解的用来 hook 的方法
     */
    static boolean isAnnotationByMethodReplace(String className, MethodNode methodNode) {
        boolean isAnnotation = false
        methodNode.invisibleAnnotations.find { anno ->
            if (anno.desc == MethodReplaceHolder.desc) {
                //break looping
                isAnnotation = true
                return true
            }
            //continue looping
            return false
        }
        return isAnnotation
    }

}
