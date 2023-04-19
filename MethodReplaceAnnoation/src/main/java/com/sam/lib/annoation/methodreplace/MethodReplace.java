package com.sam.lib.annoation.methodreplace;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wanghao
 * @date 2023/4/17.
 * @des
 */
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.CLASS)
public @interface MethodReplace {

    int opcode(); //指令操作码

    String owner(); //方法所有者类名

    String methodName() default ""; // 方法名称

    String desc() default ""; // 方法描述

    boolean isHook() default true; //是否进行hook

}
