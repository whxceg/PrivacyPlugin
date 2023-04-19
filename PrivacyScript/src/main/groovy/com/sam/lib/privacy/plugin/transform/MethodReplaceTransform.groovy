package com.sam.lib.privacy.plugin.transform


import com.sam.lib.privacy.plugin.method.MethodReplaceHolder
import com.sam.lib.privacy.plugin.method.MethodReplaceItem
import com.sam.lib.privacy.plugin.util.CommonUtil
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.tree.AbstractInsnNode
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodInsnNode

/**
 * @author wanghao
 * @date 2023/4/17.
 * @des
 */
class MethodReplaceTransform extends BaseTransform {

    MethodReplaceTransform(Project project) {
        super(project)
    }

    @Override
    protected boolean shouldHookClassInner(String className) {
        return true
    }

    @Override
    protected byte[] hookClassInner(String className, byte[] bytes) {
        ClassReader cr = new ClassReader(bytes)
        ClassNode classNode = new ClassNode()
        cr.accept(classNode, ClassReader.EXPAND_FRAMES)
        boolean isHook = false
        classNode.methods.each { methodNode ->

            //判断是否是配置替换类中的方法，过滤掉配置方法
            if (!CommonUtil.isAnnotationByMethodReplace(cr.className, methodNode)) {
                methodNode.instructions.each { insnNode ->
                    //找到要修改的方法
                    MethodReplaceItem item = searchHookPoint(insnNode)
                    if (item != null && item.willHook && insnNode instanceof MethodInsnNode) {
                        MethodReplaceHolder.logHookPoint(classNode.name, item, methodNode, insnNode.opcode, insnNode.owner, insnNode.name, insnNode.desc, true)
                        insnNode.opcode = item.replaceOpcode
                        insnNode.owner = item.replaceClass
                        insnNode.name = item.replaceMethod
                        insnNode.desc = item.replaceDesc
                        isHook = true
                    }
                }
            }

        }

        if (isHook) {
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS)
            classNode.accept(cw)
            return cw.toByteArray()
        }
        return bytes
    }

    @Override
    protected void startTransform() {
        MethodReplaceHolder.stringBuilder.setLength(0)
    }

    @Override
    protected void endTransform() {
        //写入文件
        byte[] bytes = MethodReplaceHolder.stringBuilder.toString().getBytes("UTF-8")
        try {
//            println("project.path= ${project.rootDir}")
            def targetFile = new File(project.rootDir, "replaceMethod.txt")
            if (targetFile.exists()) {
                targetFile.delete()
            }
            targetFile.withOutputStream { it ->
                it.write(bytes)
            }
            println("写文件结束，path${targetFile.absolutePath}")
        } catch (Exception e) {
            println "写文件时异常，${e.getMessage()}"
        }
        MethodReplaceHolder.stringBuilder.setLength(0)
    }

    /**
     * 遍历方法指令，寻找 hook 点
     *
     */
    static MethodReplaceItem searchHookPoint(AbstractInsnNode insnNode) {
        def hookPoint = null
        if (insnNode instanceof MethodInsnNode) {
//            println("inSnNode opcode :${insnNode.opcode} , inSnNode owner:${insnNode.owner} ,inSnNode name:${insnNode.name}  ,insnNode.desc:${insnNode.desc}")
            def opcode = insnNode.opcode
            def owner = insnNode.owner
            def name = insnNode.name
            def desc = insnNode.desc
            MethodReplaceHolder.methodReplaceItemList.find { item ->
                if (item.targetOpcode == opcode
                        && item.targetOwner == owner
                        && item.targetMethod == name
                        && item.targetDesc == desc) {
                    hookPoint = item
                }
            }
        }
        return hookPoint
    }


}