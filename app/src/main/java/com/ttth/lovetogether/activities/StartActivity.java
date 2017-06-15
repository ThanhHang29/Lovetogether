package com.ttth.lovetogether.activities;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mz.A;
import com.mz.ZAndroidSystemDK;
import com.ttth.lovetogether.utils.PermissionChecker;
import com.ttth.lovetogether.R;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by HangPC on 5/15/2017.
 */

public class StartActivity extends AppCompatActivity implements Runnable {
    private static final int MY_PERMISSIONS_REQUEST_CODE = 1001;
    private boolean isRunning = true;
    private PermissionChecker mPermissionChecker;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Fabric.with(this, new Crashlytics());
//        checkDevicePermision();
        checkPermissions();
        Thread thread = new Thread(this);
            thread.start();
    }

    private void checkPermissions() {
        if (mPermissionChecker != null) {
            mPermissionChecker.destroy();
        }
        mPermissionChecker = new PermissionChecker(this);
        PermissionChecker.AddPermissionListener listener = new PermissionChecker.AddPermissionListener() {
            @Override
            public void onPermissionGranted(String per) {

            }

            @Override
            public void onPermissionDenied(String per) {

            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mPermissionChecker.addPermission(Manifest.permission.READ_EXTERNAL_STORAGE, listener);
            mPermissionChecker.addPermission(Manifest.permission.READ_PHONE_STATE, listener);
        }

        mPermissionChecker.checkPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mPermissionChecker!=null){
            mPermissionChecker.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void run() {
        int time = 0;
        while (isRunning) {
            time++;
            if (time == 2000) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

//    private void checkDevicePermision() {
//        if (checkPermissions()) {
//            Fabric.with(this, new Crashlytics());
//            Thread thread = new Thread(this);
//            thread.start();
//        } else {
//
//            setPermissions();
//
//        }
//
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode != MY_PERMISSIONS_REQUEST_CODE) {
//            return;
//        }
//        boolean isGranted = true;
//        for (int result : grantResults) {
//            if (result != PackageManager.PERMISSION_GRANTED) {
//                isGranted = false;
//                break;
//            }
//        }
//
//        if (isGranted) {
//            Fabric.with(this, new Crashlytics());
//            Thread thread = new Thread(this);
//            thread.start();
//        } else {
//            Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
//
//        }
//    }
//
//    private void setPermissions() {
//        ActivityCompat.requestPermissions(this, new String[]{
//                Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_CODE);
//    }
//
//    private boolean checkPermissions() {
//        if (ActivityCompat.checkSelfPermission(this,
//                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            return false;
//        }
//        return true;
//    }
}
