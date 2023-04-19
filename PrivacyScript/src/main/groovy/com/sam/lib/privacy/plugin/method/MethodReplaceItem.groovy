package com.sam.lib.privacy.plugin.method

import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.MethodNode;

/**
 * @author wanghao
 * @date 2023/4/17.
 * @des
 */
class MethodReplaceItem {
    public String replaceClass

    public String replaceMethod

    public String replaceDesc

    public int replaceOpcode

    public String targetOwner

    public String targetMethod

    public String targetDesc

    public int targetOpcode

    public boolean willHook = true

    MethodReplaceItem(List<Object> annotationPair, MethodNode methodNode, String owner) {
        replaceOpcode = Opcodes.INVOKESTATIC
        replaceClass = owner
        replaceMethod = methodNode.name
        replaceDesc = methodNode.desc

        for (int i = 0; i < annotationPair.size(); i = i + 2) {
            def key = annotationPair[i]
            def value = annotationPair[i + 1]
            if (key == "opcode") {
                targetOpcode = value
            } else if (key == "owner") {
                targetOwner = value
            } else if (key == "methodName") {
                targetMethod = value
            } else if (key == "desc") {
                targetDesc = value
            } else if (key == "isHook") {
                willHook = value
            }
        }

        if (isEmpty(targetMethod)) {
            targetMethod = replaceMethod
        }

        if (isEmpty(targetDesc)) {
            //静态方法，oriDesc 跟 targetDesc 一样
            if (targetOpcode == Opcodes.INVOKESTATIC) {
                targetDesc = replaceDesc
            } else {
                //非静态方法，约定第一个参数是实例类名，oriDesc 比 targetDesc 少一个参数，处理一下
                // (Landroid/telephony/TelephonyManager;)Ljava/lang/String ->  ()Ljava/lang/String
                def param = replaceDesc.split('\\)')[0] + ")"
                def result = replaceDesc.split('\\)')[1]
                def index = replaceDesc.indexOf(targetOwner)
                if (index != -1) {
                    param = "(" + param.substring(index + targetOwner.length() + 1)
                }
                targetDesc = param + result
            }
        }

    }


    static boolean isEmpty(String str) {
        return str == null || str.isEmpty()
    }

    @Override
    String toString() {
        return "targetOpcode:" + targetOpcode + " , " + "targetOwner:" + targetOwner + " , " + "targetMethod:" + targetMethod + " , " + "targetDesc:" + targetDesc + " , " + "willHook:" + willHook
    }
}
