package com.ttth.lovetogether.adapter.maindialog;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ttth.lovetogether.R;
import com.ttth.lovetogether.model.ItemOptionDialog;

import java.util.ArrayList;

/**
 * Created by HangPC on 5/16/2017.
 */

public class DialogOptionAdapter extends ArrayAdapter<ItemOptionDialog> {
    private ArrayList<ItemOptionDialog> arrOption;
    private LayoutInflater inflater;
    private Context mContext;
    public DialogOptionAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<ItemOptionDialog> data) {
        super(context, resource, data);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arrOption = data;
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null){
            view = inflater.inflate(R.layout.item_dialog, parent, false);
            TextView tvContent = (TextView) view.findViewById(R.id.dialog_content);
            viewHolder = new ViewHolder();
            view.setTag(viewHolder);
            viewHolder.tvContent = tvContent;
        }
        viewHolder = (ViewHolder) view.getTag();
        viewHolder.tvContent.setText(arrOption.get(position).getContent());
        Typeface typeFace=Typeface.createFromAsset(mContext.getResources().getAssets(),"VL_Hillda.otf");
        viewHolder.tvContent.setTypeface(typeFace);
        return view;
    }
    class ViewHolder{
        private TextView tvContent;
    }
}
