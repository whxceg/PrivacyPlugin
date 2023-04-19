package com.sam.lib.privacy.plugin.transform


import com.sam.lib.privacy.plugin.method.MethodReplaceHolder
import com.sam.lib.privacy.plugin.method.MethodReplaceItem
import com.sam.lib.privacy.plugin.util.CommonUtil
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.tree.AnnotationNode
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodNode

/**
 * @author wanghao
 * @date 2023/4/17.
 * @des
 */
class MethodCollectionTransform extends BaseTransform {

    MethodCollectionTransform(Project project) {
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
        cr.accept(classNode, 0)
        classNode.methods.each { methodNode ->
            //编译期注解
            methodNode.invisibleAnnotations.
                    each { annotationNode ->
                        if (annotationNode.desc == MethodReplaceHolder.desc) {
                            collectPrivacyMethod(annotationNode, methodNode, cr.className)
                        }
                    }
        }
        return bytes
    }

    @Override
    protected void startTransform() {
        MethodReplaceHolder.methodReplaceItemList.clear()
    }

    @Override
    protected void endTransform() {
    }

    static collectPrivacyMethod(AnnotationNode annotationNode, MethodNode methodNode, String className) {
        List<Object> values = annotationNode.values
        println("hookMethod : ${values}")
        MethodReplaceItem item = new MethodReplaceItem(values, methodNode, CommonUtil.getClassInternalName(className))
        MethodReplaceHolder.methodReplaceItemList.add(item)
    }
}