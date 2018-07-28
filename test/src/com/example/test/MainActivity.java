package com.example.test;


import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import android.accounts.AccountsException;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Instrumentation;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;

import android.media.AudioManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.InputDevice;
import android.view.InputEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.google.i18n.phonenumbers.BuildMetadataProtoFromXml;
import java.io.File;


import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends Activity {
    
    private static final String TAG = "input-file";
    private static final String INPUT_FILE = "input-file";
    private static final String OUTPUT_DIR = "output-dir";
    private static final String DATA_PREFIX = "data-prefix";
    private static final String MAPPING_CLASS = "mapping-class";
    private static final String COPYRIGHT = "copyright";
    private static final String LITE_BUILD = "lite-build";
    private Context mContext;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        BuildMetadataProtoFromXml build = new BuildMetadataProtoFromXml();

        String path = Environment.getExternalStoragePublicDirectory("")
                + "/resources/";

        File file = new File(path + "PhoneNumberMetadata.xml");
        if (file.exists()) {
            Log.e("xxxxx", "aaaaaa" + path);
        }

        String[] args = { " ",
                "--" + INPUT_FILE + "=" + path + "PhoneNumberMetadata.xml",
                "--" + OUTPUT_DIR + "=" + path,
                "--" + DATA_PREFIX + "=PhoneNumberMetadataProto",
                "--" + MAPPING_CLASS + "=CountryCodeToRegionCodeMap",
                "--" + COPYRIGHT + "=2010", "--" + LITE_BUILD + "=false" };

        String a = "";
        for (int i = 0; i < args.length; i++) {
            Log.e("xxxxx", "aaaaaa" + args[i]);
            a = a + args[i] + "\n";
        }
        //setKeyValuePair(this,"srs_cfg:trumedia_enable=1");
        build.setArgs(args);
        build.start();
        Log.e("wuliang",getKeyValuePair(this, "srs_cfg:trumedia_enable"));
        String str = new String("aaaa");
        int b = java.lang.Integer.valueOf(str.trim()).intValue();
 
        Log.e("wuliang",getKeyValuePair(this,"srs_cfg:lib_version"));
        
        
        
        SharedPreferences mSharedPreferences = getSharedPreferences("adb_file", Context.MODE_PRIVATE);
        mSharedPreferences.edit().putBoolean("adb_read", true);
        mSharedPreferences.edit().commit();
        
        
        mSharedPreferences.getBoolean("adb_read", false);
        
        
        
    }
    
    
    

    public String getKeyValuePair(Context context, String key) {
        String keyValuePair = ((AudioManager) context.getSystemService("audio")).getParameters(key);
        return keyValuePair;
    }

    
    public void setKeyValuePair(Context context, String keyValuePair) {
        ((AudioManager) context.getSystemService("audio")).setParameters(keyValuePair);
    }

    
    
    
    
    // prize-wuliang-20180723 adb enable start
    private void initPrizeAdbStatus() {
        SharedPreferences sharedPreferences = getSharedPreferences("adb_file",
                Context.MODE_PRIVATE);
        boolean adbRead = sharedPreferences.getBoolean("adb_read", false);
        boolean adbEnable = getadbEnable();
        if (!adbRead) {
            /*if (adbEnable) {
                Settings.Global.putInt(mContext.getContentResolver(),
                        Settings.Global.PACKAGE_VERIFIER_INCLUDE_ADB, 1);
                Settings.Global.putInt(getContentResolver(),
                        Settings.Global.ADB_ENABLED, 1);
            } else {
                Settings.Global.putInt(mContext.getContentResolver(),
                        Settings.Global.ADB_ENABLED, 0);
                Settings.Global.putInt(getContentResolver(),
                        Settings.Global.PACKAGE_VERIFIER_INCLUDE_ADB, 0);
            }*/
            Editor editor= sharedPreferences.edit();
            editor.putBoolean("adb_read", true);
            editor.commit();
        }
        Log.d(TAG, "adbRead = " + adbRead + " adbEnable = " + adbEnable);
    }

    private boolean getadbEnable() {
        int result = 0;
        try {
            FileInputStream mFileInputStream = new FileInputStream(
                    "data/misc/prize/adb_enable");
            InputStreamReader mInputStreamReader = new InputStreamReader(
                    mFileInputStream, "UTF-8");
            char[] input = new char[mFileInputStream.available()];
            mInputStreamReader.read(input);
            mInputStreamReader.close();
            mFileInputStream.close();
            String str = new String(input);
            result = java.lang.Integer.valueOf(str.trim()).intValue();
            Log.d(TAG,
                    "getadbEnable: data/misc/prize/adb_enable result = "
                            + result);
        } catch (Exception e) {
            Log.d(TAG, "getadbEnable: data/misc/prize/adb_enable fail"
                    + e);
        }
        return result == 1 ? true : false;
    }
    
    
    private void setadbEnableVendor(String result) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(
                    "vendor/protected_f/adb_enable");
            fileOutputStream.write(result.getBytes());
            fileOutputStream.close();
            Log.d(TAG,
                    "getadbEnable: vendor/protected_f/adb_enable result = "
                            + result);
        } catch (Exception e) {
            Log.d(TAG, "getadbEnable: vendor/protected_f/adb_enable fail"
                    + e);
        }  
    }
    
    // prize-wuliang-20180723 adb enable end
    

    
    
    
    
    
    private void setRemindStatus(boolean isCheck) {
        SharedPreferences sharedPreferences = getSharedPreferences(
                "prize_remind_status_file", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("prize_remind_status", isCheck);
        editor.commit();
    }
    
    private boolean getRemindStatus() {
        SharedPreferences sharedPreferences = getSharedPreferences(
                "prize_remind_status_file", Activity.MODE_PRIVATE);
        return sharedPreferences.getBoolean("prize_remind_status", false);
    }
    
    

    private String getMacID() {
        StringBuilder macAddress = new StringBuilder();

        return macAddress.toString();
    }

    private String tryGetWifiMac(Context context) {
        WifiManager wm = (WifiManager) context.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wi = wm.getConnectionInfo();
        if (wi == null || wi.getMacAddress() == null) {
            return null;
        }
        if ("02:00:00:00:00:00".equals(wi.getMacAddress().trim())) {
            return getNewMac();
        } else {
            return wi.getMacAddress().trim();
        }
    }

    /**
     * ͨ������ӿ�ȡ
     * 
     * @return
     */
    private static String getNewMac() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface
                    .getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0"))
                    continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return null;
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private int getScreenHeight() {
        WindowManager manager = getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        return height;
    }

    private void sendExpandStatusBar() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int moveOffset = getScreenHeight() / 2;
                    injectPointerEvent(10, 0, 10, moveOffset, 10);
                    Thread.sleep(20);
                    injectPointerEvent(10, 0, 10, moveOffset, 30);
                    Thread.sleep(100);
                    injectPointerEvent(10, 0, 10, moveOffset, 30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void sendPointerSync(MotionEvent event) {
        if ((event.getSource() & InputDevice.SOURCE_CLASS_POINTER) == 0) {
            event.setSource(InputDevice.SOURCE_TOUCHSCREEN);
        }

        try {
            Class<?> classInputManager = Class
                    .forName("android.hardware.input.InputManager");
            Method getInstance = classInputManager.getMethod("getInstance",
                    new Class[] {});
            Object inputManager = getInstance.invoke(null, new Object[] {});

            Method injectInputEvent = classInputManager.getMethod(
                    "injectInputEvent", new Class[] { InputEvent.class,
                            int.class });
            // 2 === InputManager.INJECT_INPUT_EVENT_MODE_WAIT_FOR_FINISH
            injectInputEvent.invoke(inputManager, new Object[] { event, 2 });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void expandStatusBar() {
        long downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis();

        int startY = 0;
        int startX = 100;
        MotionEvent eventDown = MotionEvent.obtain(downTime, eventTime,
                MotionEvent.ACTION_DOWN, startX, startY, 0);
        sendPointerSync(eventDown);

        // action move
        for (int i = 0; i < 20; i++) {
            startY += (float) 20;
            downTime = SystemClock.uptimeMillis();
            eventTime = SystemClock.uptimeMillis();
            MotionEvent eventMove = MotionEvent.obtain(downTime, eventTime,
                    MotionEvent.ACTION_MOVE, startX, startY, 0);
            sendPointerSync(eventMove);
        }

        downTime = SystemClock.uptimeMillis();
        eventTime = SystemClock.uptimeMillis();
        MotionEvent eventUp = MotionEvent.obtain(downTime, eventTime,
                MotionEvent.ACTION_UP, startX, startY, 0);
        sendPointerSync(eventUp);
    }

    public void injectPointerEvent(float fromX, float fromY, float toX,
            float toY, int count) {

        Instrumentation mInst = new Instrumentation();

        long downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis();
        MotionEvent eventDown = MotionEvent.obtain(downTime, eventTime,
                MotionEvent.ACTION_DOWN, fromX, fromY, 0);
        mInst.sendPointerSync(eventDown);

        // action move
        int INDEX = count;
        float offsetY = (toY - fromY) / INDEX;
        float offsetX = (toX - fromX) / INDEX;

        for (int i = 0; i < INDEX; i++) {
            fromX += offsetX;
            fromY += offsetY;
            downTime = SystemClock.uptimeMillis();
            eventTime = SystemClock.uptimeMillis();
            MotionEvent eventMove = MotionEvent.obtain(downTime, eventTime,
                    MotionEvent.ACTION_MOVE, fromX, fromY, 0);
            mInst.sendPointerSync(eventMove);
        }

        downTime = SystemClock.uptimeMillis();
        eventTime = SystemClock.uptimeMillis();
        MotionEvent eventUp = MotionEvent.obtain(downTime, eventTime,
                MotionEvent.ACTION_UP, toX, toY, 0);
        mInst.sendPointerSync(eventUp);
    }

}
