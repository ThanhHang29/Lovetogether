package com.ttth.lovetogether.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ttth.lovetogether.R;

/**
 * Created by HangPC on 5/30/2017.
 */

public class CustomTabLayout extends TabLayout {
    private Typeface mTypeface;

    public CustomTabLayout(Context context) {
        super(context);
        init();
    }

    public CustomTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mTypeface = Typeface.createFromAsset(getContext().getAssets(), "VL_Hillda.otf"); // here you will provide fully qualified path for fonts
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        final ViewGroup tabStrip = (ViewGroup)getChildAt(0);
        final int tabCount = tabStrip.getChildCount();
        ViewGroup tabView;
        int tabChildCount;
        View tabViewChild;

        for(int i=0; i<tabCount; i++){
            tabView = (ViewGroup)tabStrip.getChildAt(i);
            tabChildCount = tabView.getChildCount();
            for(int j=0; j<tabChildCount; j++){
                tabViewChild = tabView.getChildAt(j);
                if(tabViewChild instanceof AppCompatTextView){
                    if(mTypeface != null){
                        ((TextView) tabViewChild).setTypeface(mTypeface, Typeface.BOLD);
                        ((TextView)tabViewChild).setTextSize(getResources().getDimension(R.dimen.text_size_title));
                    }
                }
            }
        }
    }

//    @Override
//    public void setupWithViewPager(ViewPager viewPager) {
//        super.setupWithViewPager(viewPager);
//
//        if (mTypeface != null) {
//            this.removeAllTabs();
//            ViewGroup slidingTabStrip = (ViewGroup) getChildAt(0);
//
//            PagerAdapter adapter = viewPager.getAdapter();
//
//            for (int i = 0, count = adapter.getCount(); i < count; i++) {
//                Tab tab = this.newTab();
//                this.addTab(tab.setText(adapter.getPageTitle(i)));
//                AppCompatTextView view = (AppCompatTextView) ((ViewGroup) slidingTabStrip.getChildAt(i)).getChildAt(1);
//                view.setTypeface(mTypeface, Typeface.NORMAL);
//            }
//        }
//    }
}
