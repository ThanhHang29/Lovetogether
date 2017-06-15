package com.ttth.lovetogether;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by HangPC on 5/16/2017.
 */

public class ToastUtils {
    private static boolean isToast = false;
    public static void Toast(Context context, String content){
        if (isToast){
            Toast.makeText(context,content, Toast.LENGTH_SHORT).show();
        }
    }
    public static void toastApp(Context context, String content){
        Toast.makeText(context,content, Toast.LENGTH_SHORT).show();
    }
}
