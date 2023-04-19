package com.sam.lib.privacy.plugin.method

import com.sam.lib.privacy.plugin.util.CommonUtil
import org.objectweb.asm.tree.MethodNode

/**
 * @author wanghao
 * @date 2023/4/17.
 * @des
 */
class MethodReplaceHolder {

    static List<MethodReplaceItem> methodReplaceItemList = new ArrayList<>()

    static StringBuilder stringBuilder = new StringBuilder()

    /**
     * 静态扫描。记录包含隐私合规方法的类、方法、字节码指令等相关信息
     */
    static void logHookPoint(String className, MethodReplaceItem item, MethodNode methodNode, Integer opcode, String owner, String name, String descriptor, boolean inject) {
        println("hook ${className}.${methodNode.name}  ${owner}.${name} inject:${inject}")
        stringBuilder.append("hook ${className}.${methodNode.name}  ${owner}.${name}")
        stringBuilder.append("\r\ntargetClass= ${CommonUtil.path2ClassName(className)}  invokeMethod= ${methodNode.name}  ${methodNode.desc}")
        stringBuilder.append("\r\n")
        stringBuilder.append("opcode=${opcode}, owner=${owner}, name=${name}, descriptor=${descriptor}")
        if (inject) {
            stringBuilder.append("\r\n")
            stringBuilder.append('------>')
            stringBuilder.append("\r\n")
            stringBuilder.append("opcode=${item.replaceOpcode}, owner=${item.replaceClass}, name=${item.replaceMethod}, descriptor=${item.replaceDesc}")
        }

        stringBuilder.append("\r\n\r\n\r\n")

    }

    static String desc = "Lcom/sam/lib/annoation/methodreplace/MethodReplace;"
}
