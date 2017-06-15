package com.ttth.lovetogether.lockscreen;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.ttth.lovetogether.main.MainFragment;
import com.ttth.lovetogether.activities.LockScreenActivity;

/**
 * Created by HangPC on 5/24/2017.
 */

public class MyServiceScreen extends Service{
    public static final String EXTRA_KEY_NUM_DAY = "extra_key_num_day";
    private static final String TAG = "Myservice";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mReceiver,intentFilter);
        return START_STICKY;
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            SharedPreferences preferences = getSharedPreferences(MainFragment.KEY_DAY_SAVE, Context.MODE_PRIVATE);
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
                Intent intentLock = new Intent(context, LockScreenActivity.class);
                intentLock.putExtra(EXTRA_KEY_NUM_DAY,preferences.getString(MainFragment.EXTRA_KEY_NUMDAY,"0"));
                intentLock.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intentLock);
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
