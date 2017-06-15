package com.ttth.lovetogether.activities;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.ttth.lovetogether.lockscreen.MyServiceScreen;
import com.ttth.lovetogether.R;

import java.util.List;

/**
 * Created by HangPC on 5/24/2017.
 */

public class LockScreenActivity extends AppCompatActivity {
    private TextView tvUnlock, tvNumDay;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        String day = intent.getStringExtra(MyServiceScreen.EXTRA_KEY_NUM_DAY);
        tvUnlock = (TextView) this.findViewById(R.id.tvUnlock);
        tvNumDay = (TextView) this.findViewById(R.id.tv_num_day);
        tvNumDay.setText(day+"\n"+"days");
        tvUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
//
//    @Override
//    public void onAttachedToWindow() {
//        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
//        super.onAttachedToWindow();
//    }
//
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)
                || (keyCode == KeyEvent.KEYCODE_POWER)
                || (keyCode == KeyEvent.KEYCODE_VOLUME_UP)
                || (keyCode == KeyEvent.KEYCODE_CAMERA)) {
            return true;
        }
        if ((keyCode == KeyEvent.KEYCODE_HOME)) {
            Log.e("LookScreen","______");
            Intent intent = new Intent(this, LockScreenActivity.class);
            startActivity(intent);
            return true;
        }

        return false;
    }

    @Override
    protected void onPause() {
        if (isApplicationSentToBackground(this)){
            Intent intent = new Intent(this, LockScreenActivity.class);
            startActivity(intent);
        }
        super.onPause();
    }
    public boolean isApplicationSentToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }
    @Override
    public void onBackPressed() {
        return;
    }
}
