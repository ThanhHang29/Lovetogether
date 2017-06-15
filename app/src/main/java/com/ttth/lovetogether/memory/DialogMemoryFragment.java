package com.ttth.lovetogether.memory;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ttth.lovetogether.R;

/**
 * Created by HangPC on 5/22/2017.
 */

public class DialogMemoryFragment extends DialogFragment  {
    private RelativeLayout flImgBackground;
    private TextView tvNameMemory, tvDateMemory;
    private ImageView btnShareFb;

    public static DialogMemoryFragment newInstance(Bitmap image) {

        Bundle args = new Bundle();

        DialogMemoryFragment fragment = new DialogMemoryFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_choose_memory, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        flImgBackground = (RelativeLayout) view.findViewById(R.id.imgBackground);
        tvNameMemory = (TextView) view.findViewById(R.id.tvNameMemory);
        tvDateMemory = (TextView) view.findViewById(R.id.tvDateMemory);
        btnShareFb = (ImageView) view.findViewById(R.id.shareWithFB);
        btnShareFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sharefb
            }
        });
    }

}
