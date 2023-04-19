package com.wh.myapplication

import android.app.ActivityManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e("tag", "onCreate")
        testGetRunningAppProcesses()

        findViewById<View>(R.id.btn1).setOnClickListener {
            Log.e("tag", "click btn1 ")
            testGetRunningAppProcesses()
        }
        findViewById<View>(R.id.btn2).setOnClickListener {
            Log.e("tag", "click btn2 ")
            val manager = this.getSystemService(ACTIVITY_SERVICE) as ActivityManager
            for (processInfo in manager.runningAppProcesses) {
                Log.e("tag", processInfo.processName)
            }
        }

        findViewById<View>(R.id.btn3).setOnClickListener {
            Log.e("tag", "click btn3 ")
            testGetRecentTasks()
        }

        findViewById<View>(R.id.btn4).setOnClickListener {
            Log.e("tag", "click btn4 ")
            val id = Settings.System.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
            Log.e("tag", "click btn4 $id")
        }
        var a = A()
        a.say()
        a.say("xxx")
        a.say("bbb", 10)
    }

    private fun testGetRunningAppProcesses() {
        val manager = this.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        for (processInfo in manager.runningAppProcesses) {
            Log.e("tag", processInfo.processName)
        }
    }


    private fun testGetRecentTasks() {
        val manager = this.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        manager.getRecentTasks(10, 1)
    }
}