package com.ttth.lovetogether.memory;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ttth.lovetogether.R;


/**
 * Created by HangPC on 5/17/2017.
 */

public class ItemMemoryView extends LinearLayout {
    private TextView tvNameMemory, tvDateMemory, tvDateCome;
    private LinearLayout imgBackground;

    public ItemMemoryView(Context context) {
        super(context);
        init(context);
    }

    public ItemMemoryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ItemMemoryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ItemMemoryView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context) {
        inflate(context, R.layout.item_memory, this);
        tvNameMemory = (TextView) findViewById(R.id.tvNameMemory);
        tvDateMemory = (TextView) findViewById(R.id.tvDateMemory);
        tvDateCome = (TextView) findViewById(R.id.tvDateCome);
        imgBackground = (LinearLayout) findViewById(R.id.imgBackground);
    }

    public void display(ItemMemory itemMemory) {
        tvNameMemory.setText(itemMemory.getName());
        tvDateMemory.setText(itemMemory.getDate());
        tvDateCome.setText(itemMemory.getDateCome());
        BitmapDrawable drawable = new BitmapDrawable(itemMemory.getUriImage());
        imgBackground.setBackgroundDrawable(drawable);
    }

}
