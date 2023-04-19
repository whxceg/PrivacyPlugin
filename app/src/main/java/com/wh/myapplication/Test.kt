package com.wh.myapplication

import android.app.ActivityManager
import android.app.ActivityManager.RecentTaskInfo
import android.app.ActivityManager.RunningAppProcessInfo
import android.util.Log
//import com.sam.lib.annoation.methodreplace.MethodReplace

///**
// * @date 2023/4/17.
// * @author wanghao
// * @des
// * 注意这里方法没有写在类里面，注解替换方法需要是静态方法，而kotlin不支持在类里面定义静态方法，所有这里放到类外面
// */
//@MethodReplace(opcode = 182, owner = "android/app/ActivityManager")
//fun getRunningAppProcesses(activityManager: ActivityManager): List<RunningAppProcessInfo?>? {
//    Log.e("Tag", "replace getRunningAppProcesses ")
//    return activityManager.runningAppProcesses
//}
//
//
//@MethodReplace(opcode = 182, owner = "android/app/ActivityManager")
//fun getRecentTasks(
//    activityManager: ActivityManager,
//    maxNum: Int,
//    flag: Int
//): List<RecentTaskInfo?>? {
//
//    Log.e("Tag", "replace getRecentTasks ")
//    return activityManager.getRecentTasks(maxNum, flag)
//}
//
//
//@MethodReplace(opcode = 182, owner = "android/app/ActivityManager")
//fun getRunningTasks(
//    activityManager: ActivityManager,
//    maxNum: Int
//): List<ActivityManager.RunningTaskInfo?>? {
//
//    Log.e("Tag", "replace getRecentTasks 2")
//    return activityManager.getRunningTasks(maxNum)
//}

//@MethodReplace(opcode = 182, owner = "com/wh/myapplication/A")
fun say(a: A, s: String) {
    Log.e("Tag", "replace a 五参数")
    return a.say(s)
}