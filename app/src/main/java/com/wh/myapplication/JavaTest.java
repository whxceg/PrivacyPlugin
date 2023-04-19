package com.wh.myapplication;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.DhcpInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.CellInfo;
import android.telephony.TelephonyManager;

import androidx.annotation.RequiresApi;

//import com.sam.lib.annoation.methodreplace.MethodReplace;

import java.util.List;

/**
 * @author wanghao
 * @date 2023/4/17.
 * @des
 */
public class JavaTest {
//
//    @MethodReplace(opcode = 182, owner = "android/telephony/TelephonyManager", methodName = "getDeviceId")
//    public static String getDeviceId(TelephonyManager manager) {
//        check();
//        return manager.getDeviceId();
//    }
//
//    @MethodReplace(opcode = 182, owner = "android/telephony/TelephonyManager", methodName = "getDeviceId")
//    public static String getDeviceId(TelephonyManager manager, int index) {
//        check();
//        return manager.getDeviceId(index);
//    }
//
//    @MethodReplace(opcode = 182, owner = "android/telephony/TelephonyManager", methodName = "getImei")
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public static String getImei(TelephonyManager manager) {
//        check();
//        return manager.getImei();
//    }
//
//    @MethodReplace(opcode = 182, owner = "android/telephony/TelephonyManager", methodName = "getImei")
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public static String getImei(TelephonyManager manager, int index) {
//        check();
//        return manager.getImei(index);
//    }
//
//    @MethodReplace(opcode = 182, owner = "android/telephony/TelephonyManager", methodName = "getSimSerialNumber")
//    public static String getSimSerialNumber(TelephonyManager manager) {
//        check();
//        return manager.getSimSerialNumber();
//    }
//
//    @MethodReplace(opcode = 182, owner = "android/app/ActivityManager", methodName = "getRunningAppProcesses")
//    public static List<ActivityManager.RunningAppProcessInfo> getRunningAppProcesses(ActivityManager manager) {
//        check();
//        return manager.getRunningAppProcesses();
//    }
//
//    @MethodReplace(opcode = 182, owner = "android/app/ActivityManager", methodName = "getRecentTasks")
//    public static List<ActivityManager.RecentTaskInfo> getRecentTasks(ActivityManager manager, int maxNum, int flag) {
//        check();
//        return manager.getRecentTasks(maxNum, flag);
//    }
//
//    @MethodReplace(opcode = 182, owner = "android/app/ActivityManager", methodName = "getRunningTasks")
//    public static List<ActivityManager.RunningTaskInfo> getRunningTasks(ActivityManager manager, int max) {
//        check();
//        return manager.getRunningTasks(max);
//    }
//
//    @MethodReplace(opcode = 182, owner = "android/telephony/TelephonyManager", methodName = "getAllCellInfo")
//    @SuppressLint("MissingPermission")
//    public static List<CellInfo> getAllCellInfo(TelephonyManager manager) {
//        check();
//        return manager.getAllCellInfo();
//    }
//
//    @MethodReplace(opcode = 182, owner = "android/telephony/TelephonyManager", methodName = "getSSID")
//    public static String getSSID(WifiInfo wifiInfo) {
//        check();
//        return wifiInfo.getSSID();
//    }
//
//    @MethodReplace(opcode = 182, owner = "android/net/wifi/WifiInfo", methodName = "getBSSID")
//    public static String getBSSID(WifiInfo wifiInfo) {
//        check();
//        return wifiInfo.getBSSID();
//    }
//
//    @MethodReplace(opcode = 182, owner = "android/net/wifi/WifiInfo", methodName = "getMacAddress")
//    public static String getMacAddress(WifiInfo wifiInfo) {
//        check();
//        return wifiInfo.getMacAddress();
//    }
//
//    @MethodReplace(opcode = 184, owner = "android/provider/Settings$System", methodName = "getString")
//    public static String getString(ContentResolver resolver, String name) {
//        check();
//        return Settings.System.getString(resolver, name);
//    }
//
//    @MethodReplace(opcode = 182, owner = "android/telephony/TelephonyManager", methodName = "getSensorList")
//    public static List<Sensor> getSensorList(SensorManager sensorManager, int type) {
//        check();
//        return sensorManager.getSensorList(type);
//    }
//
//    @MethodReplace(opcode = 182, owner = "android/telephony/TelephonyManager", methodName = "getDhcpInfo")
//    public static DhcpInfo getDhcpInfo(WifiManager manager) {
//        check();
//        return manager.getDhcpInfo();
//    }
//
//    @MethodReplace(opcode = 182, owner = "android/telephony/TelephonyManager", methodName = "getConfiguredNetworks")
//    @SuppressLint("MissingPermission")
//    public static List<WifiConfiguration> getConfiguredNetworks(WifiManager manager) {
//        check();
//        return manager.getConfiguredNetworks();
//    }
//
//    @MethodReplace(opcode = 182, owner = "android/telephony/TelephonyManager", methodName = "getLastKnownLocation")
//    @SuppressLint("MissingPermission")
//    public static Location getLastKnownLocation(LocationManager locationManager, String provider) {
//        check();
//        return locationManager.getLastKnownLocation(provider);
//    }
//
//    @SuppressLint("MissingPermission")
//    @MethodReplace(opcode = 182, owner = "android/telephony/TelephonyManager", methodName = "requestLocationUpdates")
//    public static void requestLocationUpdates(LocationManager locationManager, String provider, long minTime, float minDistance, LocationListener listener) {
//        check();
//        locationManager.requestLocationUpdates(provider, minTime, minDistance, listener);
//    }

    private static void check() {
//        String trace = printMyStackTrace();
//        Log.e("TAG", "check: " + trace);
//        if()

    }

    public static String printMyStackTrace() {
        long t = SystemClock.elapsedRealtimeNanos();
        Throwable throwable = new Throwable();
        StackTraceElement[] stackElements = throwable.getStackTrace();
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement stackElement : stackElements) {
            sb.append(stackElement.getClassName());
            sb.append(".").append(stackElement.getMethodName());
            sb.append("(").append(stackElement.getFileName()).append(":");
            sb.append(stackElement.getLineNumber()).append(")").append("\n");
        }
        sb.append("usetime:").append(SystemClock.elapsedRealtimeNanos() - t).append("\n");
        return sb.toString();
    }
}
