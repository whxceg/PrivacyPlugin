package com.wh.myapplication;

import android.util.Log;

/**
 * @author wanghao
 * @date 2023/4/18.
 * @des
 */
public class A {
    public void say() {
        Log.e("tag", "say");
    }

    public void say(String s) {
        Log.e("tag", "say 1 " + s);
    }

    public void say(String s, int a) {
        Log.e("tag", "say 2 " + s + "  a:" + a);
    }
}
