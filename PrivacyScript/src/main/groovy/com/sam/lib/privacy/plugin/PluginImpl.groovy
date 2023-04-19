package com.sam.lib.privacy.plugin

import com.android.build.gradle.AppExtension
import com.sam.lib.privacy.plugin.transform.MethodCollectionTransform
import com.sam.lib.privacy.plugin.transform.MethodReplaceTransform

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * @author wanghao
 * @date 2023/4/14.
 * @des
 */
class PluginImpl implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def android = project.extensions.getByType(AppExtension)
        //注册两个Transform 第一个用于收集需要替换的方法，第二个用户处理替换流程
        android.registerTransform(new MethodCollectionTransform(project))
        android.registerTransform(new MethodReplaceTransform(project))
    }

}
