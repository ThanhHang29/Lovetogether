package com.ttth.lovetogether.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HangPC on 5/30/2017.
 */

public class PermissionChecker {
    public static final int REQUEST_CODE_GET_PERMISSION = 112;
    private Context mContext;
    private Map<String, AddPermissionListener> permissions = new HashMap<>();

    /**
     *
     * @param mContext must be {@link Activity }
     */
    public PermissionChecker(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * add permission to check and run callback
     *
     * @param permission to check
     * @param listener call back after check and request permission
     */
    public void addPermission(String permission, AddPermissionListener listener) {
        if (!permissions.containsKey(permission)) {
            permissions.put(permission, listener);
        }
    }


    /**
     *  start call check Permission
     */
    public void checkPermission() {
        if (Build.VERSION_CODES.M <= Build.VERSION.SDK_INT) {
            check();
        } else {
            for (String permission : permissions.keySet()) {
                AddPermissionListener listener = permissions.get(permission);
                if (listener != null) {
                    listener.onPermissionGranted(permission);
                }
            }
        }
    }


    /**
     *  check whether if Permission is granted or denied
     *  and call {@link ActivityCompat#requestPermissions(Activity, String[], int)}
     */
    private void check() {
        List<String> per = new ArrayList<>();

        for (String permission : permissions.keySet()) {
            AddPermissionListener listener = permissions.get(permission);
            if (ContextCompat.checkSelfPermission(mContext, permission)!= PackageManager.PERMISSION_GRANTED) {
                per.add(permission);
            } else {
                if (listener != null) {
                    listener.onPermissionGranted(permission);
                }
            }
        }

        if (per.size() > 0) {
            String[] arr = new String[per.size()];
            ActivityCompat.requestPermissions((Activity) mContext,
                    per.toArray(arr),
                    REQUEST_CODE_GET_PERMISSION);
        }
    }


    /**
     * call in {@link Activity#onRequestPermissionsResult(int, String[], int[])}
     * to recieve results and call listener {@link AddPermissionListener}
     *
     * @param requestCode
     * @param pers
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode,
                                           String pers[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_GET_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0) {
                    for (int i = 0; i < pers.length; i++) {
                        String permission = pers[i];
                        AddPermissionListener listener = permissions.get(permission);
                        if (listener != null) {
                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                                // permission was granted, yay! Do the
                                // contacts-related task you need to do.
                                listener.onPermissionGranted(permission);
                            } else {

                                // permission denied, boo! Disable the
                                // functionality that depends on this permission.
                                listener.onPermissionDenied(permission);
                            }
                        }
                    }
                }
                break;
            }
        }
    }

    public void destroy() {
        mContext = null;
        permissions.clear();
        permissions = null;
    }


    public interface AddPermissionListener {
        void onPermissionGranted(String per);

        void onPermissionDenied(String per);
    }
}
