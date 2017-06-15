package com.ttth.lovetogether.activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.mz.A;
import com.mz.ZAndroidSystemDK;
import com.ttth.lovetogether.ClickActionbar;
import com.ttth.lovetogether.main.BackGroundFragment;
import com.ttth.lovetogether.main.HomeFragment;
import com.ttth.lovetogether.main.MainFragment;
import com.ttth.lovetogether.setup.SetupFragment;
import com.ttth.lovetogether.utils.AppRate;
import com.ttth.lovetogether.utils.BitmapAsynctask;
import com.ttth.lovetogether.views.CustomTabLayout;
import com.ttth.lovetogether.R;

import java.io.File;


public class MainActivity extends AppCompatActivity implements
        BackGroundFragment.SetBackground, ClickActionbar,
        MainFragment.GetUpDateNotification,SetupFragment.ActionNotification,
        ActivityCompat.OnRequestPermissionsResultCallback{
    private static final String EXTRA_KEY_SAVE_IMAGE = "extra_key_save_image";
    private static final String KEY_SAVE_BACKGROUND = "key_save_background";
    private static final String KEY_SAVE_BACKGROUND_DEVICE = "key_save_background_device";
    private static final String EXTRA_KEY_SAVE_IMAGE_DEVICE = "extra_key_save_image_device ";
    public static final int NOTIFICATION_ID = 99;
    private static final String KEY_RESUTL_IMAGE = "key_result_image";
    private static final String KEY_RESUTL_IMAGE_DEVICE = "key_result_device";
    private static final String EXTRA_KEY_SAVE_RESUTL = "key_save_image";
//    private TabLayout tabLayout;
    private CustomTabLayout tabLayout;
    private LinearLayout mainBackground;
    private NotificationManager mNotificationManager;
    private String updateNumDay="", updateAvatarLeft, updateAvatarRight, updateDay;
    private HomeFragment mHomeFragment;
    private  NotificationCompat.Builder mBuilder;
    private RemoteViews contentView;
    private int rerutlNotification, resutlImg;
    private View view1, view3, view2;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addFragment();
        initView();
        isHasActionbar(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
//            window.setStatusBarColor(Color.BLUE);
        }
//        AppRate.app_launched(this);
        ZAndroidSystemDK.init(this);
        A.f(this);

    }


    private void initView() {
        tabLayout = (CustomTabLayout) this.findViewById(R.id.tabs);
        mainBackground = (LinearLayout) this.findViewById(R.id.mainBackGround);
        restoreImgBackground();
    }

    private void addFragment() {
        mHomeFragment = new HomeFragment();
        Fragment fragmentCurrent = getSupportFragmentManager().findFragmentById(R.id.flFragment);
        if (fragmentCurrent == null){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.flFragment, mHomeFragment);
            transaction.commit();

        }
    }

    @Override
    public void setBackground(int image) {
        if (image!=0){
            resutlImg= 3;
            BitmapAsynctask bitmapAsynctask = new BitmapAsynctask(this, mainBackground);
            bitmapAsynctask.execute(image);
            mainBackground.setBackgroundResource(image);
            SharedPreferences.Editor editor = getSharedPreferences(EXTRA_KEY_SAVE_IMAGE, Context.MODE_PRIVATE).edit();
            editor.putInt(KEY_SAVE_BACKGROUND,image);
            editor.apply();
            editor.commit();
            saveSetImg(resutlImg);
        }
    }

    @Override
    public void setBackground(String path) {
        if (!path.equalsIgnoreCase("")){
            resutlImg = 4;
            File file = new File(path);
            Drawable drawable = Drawable.createFromPath(file.getAbsolutePath());
            mainBackground.setBackgroundDrawable(drawable);
            SharedPreferences.Editor editor = getSharedPreferences(EXTRA_KEY_SAVE_IMAGE_DEVICE, Context.MODE_PRIVATE).edit();
            editor.putString(KEY_SAVE_BACKGROUND_DEVICE,path);
            editor.apply();
            editor.commit();
            saveSetImg(resutlImg);
        }
    }
    private void saveSetImg(int result){
        SharedPreferences.Editor editor = getSharedPreferences(EXTRA_KEY_SAVE_RESUTL, Context.MODE_PRIVATE).edit();
        editor.putInt(KEY_RESUTL_IMAGE, result);
        editor.apply();
        editor.commit();
    }
    private void restoreImgBackground(){
        SharedPreferences prefImage, prefImageBitmap, prefeResult;
        prefImage = getSharedPreferences(EXTRA_KEY_SAVE_IMAGE, Context.MODE_PRIVATE);
        prefImageBitmap = getSharedPreferences(EXTRA_KEY_SAVE_IMAGE_DEVICE, Context.MODE_PRIVATE);
        prefeResult = getSharedPreferences(EXTRA_KEY_SAVE_RESUTL, Context.MODE_PRIVATE);
        if (prefeResult.getInt(KEY_RESUTL_IMAGE, 0)==3){
         mainBackground.setBackgroundResource(prefImage.getInt(KEY_SAVE_BACKGROUND, R.drawable.ic_background));
        }else if (prefeResult.getInt(KEY_RESUTL_IMAGE, 0) == 4){
            String path = prefImageBitmap.getString(KEY_SAVE_BACKGROUND_DEVICE, "");
            if (!path.equalsIgnoreCase("")){
                File file = new File(path);
                Drawable drawable = Drawable.createFromPath(file.getAbsolutePath());
                mainBackground.setBackgroundDrawable(drawable);
            }else {
                mainBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_background));
            }
        }else if (prefeResult.getInt(KEY_RESUTL_IMAGE, 0)==0){
            mainBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_background));
        }

    }

    public void isHasActionbar(boolean isActionBar){
        if (isActionBar){
            tabLayout.setVisibility(View.VISIBLE);
        }else {
            tabLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        tabLayout.setVisibility(View.VISIBLE);
       if (getSupportFragmentManager().getBackStackEntryCount() > 0){
           getSupportFragmentManager().popBackStack();
       }else {
           finish();
       }
    }

    @Override
    public void syncActionbar(int position) {

    }

    @Override
    public void addActionbar(ViewPager viewPager) {
        tabLayout.setupWithViewPager(viewPager);
        view1 = getLayoutInflater().inflate(R.layout.custom_tab, null);
        view1.findViewById(R.id.icon).setBackgroundResource(R.drawable.ic_edit);

        view2 = getLayoutInflater().inflate(R.layout.custom_textab, null);
        tvTitle = (TextView) view2.findViewById(R.id.title_actionbar);
        tvTitle.setText(getString(R.string.title_actionbar));
        tvTitle.setTextColor(getResources().getColor(R.color.white));
        Typeface typeFace=Typeface.createFromAsset(getResources().getAssets(),"VL_Hillda.otf");
        tvTitle.setTypeface(typeFace);

        view3 = getLayoutInflater().inflate(R.layout.custom_tab, null);
        view3.findViewById(R.id.icon).setBackgroundResource(R.drawable.ic_setting);
        for (int i = 0; i < tabLayout.getTabCount(); i++){
            tabLayout.getTabAt(0).setCustomView(view1);
            tabLayout.getTabAt(1).setCustomView(view2);
            tabLayout.getTabAt(2).setCustomView(view3);
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0){
                    view1.findViewById(R.id.icon).setBackgroundResource(R.drawable.ic_edit_selected);
                    tvTitle.setTextColor(getResources().getColor(R.color.white_light));
                    view3.findViewById(R.id.icon).setBackgroundResource(R.drawable.ic_setting);
                }
                if (position == 1){
                    view1.findViewById(R.id.icon).setBackgroundResource(R.drawable.ic_edit);
                    tvTitle.setTextColor(getResources().getColor(R.color.white));
                    view3.findViewById(R.id.icon).setBackgroundResource(R.drawable.ic_setting);
                }
                if (position == 2){
                    view1.findViewById(R.id.icon).setBackgroundResource(R.drawable.ic_edit);
                    tvTitle.setTextColor(getResources().getColor(R.color.white_light));
                    view3.findViewById(R.id.icon).setBackgroundResource(R.drawable.ic_setting_selected);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    public void setNotification(String numDay, String dateLove, String nameLeft, String nameRight) {
        mBuilder = new NotificationCompat.Builder(getApplicationContext());
        mBuilder.setSmallIcon(R.drawable.ic_heart1);
        mBuilder.setOngoing(true);
        contentView = new RemoteViews(getPackageName(), R.layout.custom_notification);
        contentView.setTextViewText(R.id.tv_num_day, numDay);
        contentView.setTextViewText(R.id.date_love, dateLove);
        contentView.setTextViewText(R.id.name_left, nameLeft);
        contentView.setTextViewText(R.id.name_right, nameRight);
        mBuilder.setCustomContentView(contentView);

        Intent intent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID,mBuilder.build());
    }
    public void cancelNotification(){
        mNotificationManager.cancel(NOTIFICATION_ID);
    }

    @Override
    public void setUpdateNumDay(String updateNumDay) {
        this.updateNumDay = updateNumDay;
        contentView.setTextViewText(R.id.tv_num_day, this.updateNumDay);
        mBuilder.setCustomContentView(contentView);
        if (rerutlNotification == 1){
            mNotificationManager.notify(NOTIFICATION_ID,mBuilder.build());
        }else {
            cancelNotification();
        }
    }

    @Override
    public void setUpdateDay(String dateLove) {
        this.updateDay = dateLove;
        contentView.setTextViewText(R.id.date_love, this.updateDay);
        mBuilder.setCustomContentView(contentView);
        if (rerutlNotification == 1){
            mNotificationManager.notify(NOTIFICATION_ID,mBuilder.build());
        }else {
            cancelNotification();
        }
    }

    @Override
    public void setUpdateNameLeft(String nameLeft) {
        this.updateAvatarLeft = nameLeft;
        contentView.setTextViewText(R.id.name_left, this.updateAvatarLeft);
        mBuilder.setCustomContentView(contentView);
        if (rerutlNotification == 1){
            mNotificationManager.notify(NOTIFICATION_ID,mBuilder.build());
        }else {
            cancelNotification();
        }
    }

    @Override
    public void setUpdateNameRight(String nameRight) {
        this.updateAvatarRight = nameRight;
        contentView.setTextViewText(R.id.name_right, this.updateAvatarRight);
        mBuilder.setCustomContentView(contentView);
        if (rerutlNotification == 1){
            mNotificationManager.notify(NOTIFICATION_ID,mBuilder.build());
        }else {
            cancelNotification();
        }

    }

    @Override
    public int setActionNotification(boolean isNotification) {
        if (isNotification){
            rerutlNotification = 1;
        }
        return rerutlNotification;
    }
}
