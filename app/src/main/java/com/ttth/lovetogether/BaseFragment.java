package com.ttth.lovetogether;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by HangPC on 5/16/2017.
 */

public class BaseFragment extends Fragment {
    protected boolean mSaveInstanceState;
    protected boolean isHasActionBar(){
        return true;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSaveInstanceState = false;
    }

    @LayoutRes
    protected int getResLayout(){
        return 0;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int reslayout = getResLayout();
        return inflater.inflate(reslayout, container, false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mSaveInstanceState = true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSaveInstanceState = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSaveInstanceState = false;
    }
    public void replaceFragment(Fragment fragment, int getResLayoutId, boolean isAddBackStack){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(getResLayoutId, fragment);
        if (isAddBackStack){
            String tag = getActivity().getSupportFragmentManager().getClass().getName();
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }
}
