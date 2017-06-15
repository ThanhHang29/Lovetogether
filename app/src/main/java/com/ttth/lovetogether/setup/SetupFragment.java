package com.ttth.lovetogether.setup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.ttth.lovetogether.BaseFragment;
import com.ttth.lovetogether.ToastUtils;
import com.ttth.lovetogether.lockscreen.MyServiceScreen;
import com.ttth.lovetogether.R;
import com.ttth.lovetogether.activities.MainActivity;
import com.ttth.lovetogether.main.BackGroundFragment;

/**
 * Created by HangPC on 5/16/2017.
 */

public class SetupFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener{
    private static final int NOTIFICATION_ID = 00000;
    public static final String KEY_DATE_LOVE = "key_date_love";
    public static final String KEY_NUM_DAY = "key_num_day";
    public static final String KEY_NAME_RIGHT = "key_name_right";
    public static final String KEY_NAME_LEFT = "key_name_left";
    private static final String EXTRA_KEY_PRESS = "extra_key_press";
    private static final String KEY_PRESS = "key_press";
    private static final String KEY_PRESS_BLOCK = "key_press_block";
    public static final boolean NOTIFICATION_ACTIVE = true;
    //    public static final int NOTIFICATION_ACTIVE = 66;
//    public static final int NOTIFICATION_CANCLE = 33;
    private Switch btnNotification, btnBlockScrean;
    private TextView tvChangeBackGround, tvSetPass;
    private String numDay, dateLove, nameLeft, nameRight, updateNumDay;
    private ActionNotification mActionNotification;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActionNotification = (ActionNotification) context;
    }

    public static SetupFragment newInstance(String date, String numDay, String nameLeft, String nameRight) {

        Bundle args = new Bundle();
        args.putString(KEY_DATE_LOVE, date);
        args.putString(KEY_NUM_DAY, numDay);
        args.putString(KEY_NAME_LEFT, nameLeft);
        args.putString(KEY_NAME_RIGHT, nameRight);
        SetupFragment fragment = new SetupFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getResLayout() {
        return R.layout.fragment_setup;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (savedInstanceState == null){
            if (bundle!=null){
                numDay = bundle.getString(KEY_NUM_DAY);
                dateLove = bundle.getString(KEY_DATE_LOVE);
                nameLeft = bundle.getString(KEY_NAME_LEFT);
                nameRight = bundle.getString(KEY_NAME_RIGHT);
            }
        }else {
            numDay = savedInstanceState.getString(KEY_NUM_DAY);
            dateLove = savedInstanceState.getString(KEY_DATE_LOVE);
            nameLeft = savedInstanceState.getString(KEY_NAME_LEFT);
            nameRight = savedInstanceState.getString(KEY_NAME_RIGHT);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_NUM_DAY, numDay);
        outState.putString(KEY_DATE_LOVE, dateLove);
        outState.putString(KEY_NAME_LEFT, nameLeft);
        outState.putString(KEY_NAME_RIGHT, nameRight);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnNotification = (Switch) view.findViewById(R.id.btnNotification);
        btnBlockScrean = (Switch) view.findViewById(R.id.btnBlockScreen);
        tvChangeBackGround = (TextView) view.findViewById(R.id.tvChangeBackGround);
        tvSetPass = (TextView) view.findViewById(R.id.tvSetPass);
        TextView title = (TextView) view.findViewById(R.id.tvTitle);
        TextView tvNotification = (TextView) view.findViewById(R.id.tvNotification);
        initView();
        Typeface typeFace=Typeface.createFromAsset(getResources().getAssets(),"VL_Hillda.otf");
        title.setTypeface(typeFace);
        tvNotification.setTypeface(typeFace);
        tvChangeBackGround.setTypeface(typeFace);


    }

    private void initView() {
        SharedPreferences prefBlock = getActivity().getSharedPreferences(EXTRA_KEY_PRESS,Context.MODE_PRIVATE);
        btnBlockScrean.setChecked(prefBlock.getBoolean(KEY_PRESS_BLOCK, false));
        btnNotification.setChecked(prefBlock.getBoolean(KEY_PRESS, false));
        btnNotification.setOnCheckedChangeListener(this);
        btnBlockScrean.setOnCheckedChangeListener(this);
        tvChangeBackGround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new BackGroundFragment(), R.id.flFragment, true);
            }
        });
        tvSetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new SetPassFragment(), R.id.flFragment, true);
            }
        });
        if(!KEY_PRESS.equalsIgnoreCase("")||KEY_PRESS.equals(null)){
          checkNotifi(btnNotification.isChecked());
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.btnNotification:
                checkNotifi(isChecked);
                break;
            case R.id.btnBlockScreen:
                if (isChecked) {
                    getActivity().startService(new Intent(getActivity(), MyServiceScreen.class));
                    ToastUtils.toastApp(getActivity(), "Lockscreen");
                } else {
                    getActivity().stopService(new Intent(getActivity(), MyServiceScreen.class));
                }
                break;
        }
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(EXTRA_KEY_PRESS, Context.MODE_PRIVATE).edit();
        editor.putBoolean(KEY_PRESS, btnNotification.isChecked());
        editor.putBoolean(KEY_PRESS_BLOCK, btnBlockScrean.isChecked());
        editor.apply();
        editor.commit();
    }
    private void checkNotifi(boolean isChecked){
        if (isChecked) {
//            mActionNotification.isNotification(isChecked);
            ((MainActivity)getActivity()).setNotification(numDay,dateLove, nameLeft, nameRight);
            mActionNotification.setActionNotification(true);
        } else {
            ((MainActivity)getActivity()).setNotification(numDay,dateLove, nameLeft, nameRight);
            ((MainActivity)getActivity()).cancelNotification();
            mActionNotification.setActionNotification(false);
        }
//        ToastUtils.Toast(getActivity(), "Thông báo"+isChecked);
        Log.e("_________","++++++++"+isChecked);
    }



    @Override
    public void onPause() {
        super.onPause();
//        ToastUtils.Toast(getContext(),"hhhhhh");
    }
    public interface ActionNotification{
        int setActionNotification(boolean isNotification);
    }
}
