package com.ttth.lovetogether.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ttth.lovetogether.BaseFragment;
import com.ttth.lovetogether.HomePagerAdapter;
import com.ttth.lovetogether.memory.MemoryFragment;
import com.ttth.lovetogether.ClickActionbar;
import com.ttth.lovetogether.R;
import com.ttth.lovetogether.setup.SetupFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HangPC on 5/17/2017.
 */

public class HomeFragment extends BaseFragment {
    public static final String KEY_NUM_DAY = "key_update_num_day";
    private ViewPager mViewPager;
    private ClickActionbar mClickActionbar;

    @Override
    protected int getResLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mClickActionbar = (ClickActionbar) context;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        initView();
    }


    private void initView() {
        SharedPreferences  prefDate, prefName;
        prefDate = getActivity().getSharedPreferences(MainFragment.KEY_DAY_SAVE, Context.MODE_PRIVATE);
        String numDay = prefDate.getString(MainFragment.EXTRA_KEY_NUMDAY, "0");
        String date = prefDate.getInt(MainFragment.KEY_SET_SETDAY,1)+
                "-"+prefDate.getInt(MainFragment.KEY_SET_MONTH, 1)+"-"+
                prefDate.getInt(MainFragment.KEY_SET_YEAR, 2016);
        prefName  = getActivity().getSharedPreferences(MainFragment.EXTRA_KEY_SAVE_NAME, Context.MODE_PRIVATE);
        String nameLeft= prefName.getString(MainFragment.KEY_NAME_LEFT,getString(R.string.name_left));
        String nameRight = prefName.getString(MainFragment.KEY_NAME_RIGHT, getString(R.string.name_right));


        MainFragment mainFragment = new MainFragment();
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new MemoryFragment());
        fragmentList.add(mainFragment);
        fragmentList.add(new SetupFragment().newInstance(date, numDay, nameLeft, nameRight));
        final HomePagerAdapter fragmentAdapter = new HomePagerAdapter(getChildFragmentManager(),fragmentList);
        mViewPager.setAdapter(fragmentAdapter);
        mViewPager.setCurrentItem(1);
        mClickActionbar.addActionbar(mViewPager);

    }

}
