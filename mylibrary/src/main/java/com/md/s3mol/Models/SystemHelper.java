package com.md.s3mol.Models;

import static android.content.Context.ACTIVITY_SERVICE;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

//import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

public class SystemHelper {

    public static SystemInfo getSystemInformation(Activity context)
    {
        SystemInfo systemInfo = new SystemInfo();
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);

        int mHeightPixels = dm.heightPixels;
        int mWidthPixels = dm.widthPixels;
        double x = Math.pow(mWidthPixels / dm.xdpi, 2);
        double y = Math.pow(mHeightPixels / dm.ydpi, 2);
        double screenInches = Math.sqrt(x + y);

       int densityDpi = (int) (dm.density * 160f);


        final TelephonyManager tm = (TelephonyManager) context.getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId;
      //  tmDevice = "" + tm.getDeviceId();
       // tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        UUID androidId_UUID = null;
        try {
            androidId_UUID = UUID
                    .nameUUIDFromBytes(androidId.getBytes("utf8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String unique_id = androidId_UUID.toString();
       // UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String deviceId = unique_id.toString();

       String androidID =  Settings.Secure.getString(context.getContentResolver(),Settings.Secure.ANDROID_ID);
        String ScreenResolution = mHeightPixels + " * " + mWidthPixels + " Pixels";

        systemInfo.setManufacturer(Build.MANUFACTURER);
        systemInfo.setBrand(Build.BRAND);
        systemInfo.setModel(Build.MODEL);
        systemInfo.setBoard(Build.BOARD);
        systemInfo.setHardware(Build.HARDWARE);
        systemInfo.setSerialNo(Build.SERIAL);
        systemInfo.setDeviceId(deviceId);
        systemInfo.setAndroidID(androidID);
        systemInfo.setScreenResolution(ScreenResolution);
        systemInfo.setScreenSize(screenInches + " Inches");
        systemInfo.setScreenDensity( String.valueOf(densityDpi) + " dpi");
        systemInfo.setBootLoader(Build.BOOTLOADER);
        systemInfo.setUser(Build.USER);
        systemInfo.setHost(Build.HOST);
        systemInfo.setVersion(Build.VERSION.RELEASE);
        systemInfo.setAPILevel(Build.VERSION.SDK_INT + "");
        systemInfo.setBuildID(Build.ID);
        systemInfo.setBuildTime(Build.TIME + "");
        systemInfo.setFingerprint(Build.FINGERPRINT);
        systemInfo.setExternalMemoryIsAvailable(externalMemoryAvailable() == true ? "Yes":"No");
        if (externalMemoryAvailable()) {
            systemInfo.setExternalMemory(getAvailableExternalMemorySize());
        }
        systemInfo.setInternalMemory(getTotalInternalMemorySize());
        systemInfo.setRAMInfo(getRamInfo(context));
        systemInfo.setVersionRelease(Build.VERSION.RELEASE);
        return systemInfo;
    }

    public static String getMacAddress(Context context)
    {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        String macAddress = wInfo.getMacAddress();
        return macAddress;
    }
    public static String getIpAddress(Context context)
    {

        WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wm.getConnectionInfo();
        byte[] myIPAddress = BigInteger.valueOf(wInfo.getIpAddress()).toByteArray();
       // ArrayUtils.reverse(myIPAddress);
        InetAddress myInetIP = null;
        try {
            myInetIP = InetAddress.getByAddress(myIPAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String myIP;
        if (myInetIP!=null){
            myIP = myInetIP.getHostAddress();
        }
        else
        {
            myIP = "offline";
        }

        return myIP;
    }
    public static boolean externalMemoryAvailable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    public static String getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        return formatSize(availableBlocks * blockSize);
    }

    public static String getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getBlockCountLong();
        return formatSize(totalBlocks * blockSize);
    }

    public static String getAvailableExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long availableBlocks = stat.getAvailableBlocksLong();
            return formatSize(availableBlocks * blockSize);
        } else {
            return "ERROR";
        }
    }

    public static String getTotalExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long totalBlocks = stat.getBlockCountLong();
            return formatSize(totalBlocks * blockSize);
        } else {
            return "ERROR";
        }
    }

    public static String formatSize(long size) {
        String suffix = null;

        if (size >= 1024) {
            suffix = "KB";
            size /= 1024;
            if (size >= 1024) {
                suffix = "MB";
                size /= 1024;
            }
        }

        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));

        int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',');
            commaOffset -= 3;
        }

        if (suffix != null) resultBuffer.append(suffix);
        return resultBuffer.toString();
    }

    public static String getRamInfo(Activity context)
    {
        ActivityManager actManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        actManager.getMemoryInfo(memInfo);
        long totalMemory = memInfo.totalMem / (1024*1024);
        long totalRam = getTotalRAM();

//       return   String.valueOf(totalMemory);
       return totalMemory+"MB"+"/"+totalRam+"MB";
    }

    public static long getTotalRAM() {
        String str1 = "/proc/meminfo";
        String str2;
        String[] arrayOfString;
        long totalMemory = 0;

        try {
            java.io.FileReader localFileReader = new java.io.FileReader(str1);
            java.io.BufferedReader localBufferedReader = new java.io.BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");

            // Get the total RAM in kilobytes
            totalMemory = Long.parseLong(arrayOfString[1]);
            localBufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return totalMemory / 1024; // Convert kilobytes to megabytes
    }
    public static double getAvailableRam(Context context){
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        double availableMegs = mi.availMem / 0x100000L;

//Percentage can be calculated for API 16+
        double percentAvail = mi.availMem / (double)mi.totalMem * 100.0;
        return availableMegs;
    }
    public static boolean getAvailableStorageInMB() {
        try {
            String path = Environment.getExternalStorageDirectory().getPath();
            StatFs stat = new StatFs(path);

            long blockSize = stat.getBlockSizeLong();
            long availableBlocks = stat.getAvailableBlocksLong();

            long availableSpaceBytes = availableBlocks * blockSize;
            long availableSpaceGB = availableSpaceBytes / (1024 * 1024 * 1024); // Convert to gigabytes

         //   long availableSpaceMB = availableSpaceBytes / (1024 * 1024); // Convert to megabytes

            return availableSpaceGB > 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Error occurred while obtaining storage information
        }
    }
}
