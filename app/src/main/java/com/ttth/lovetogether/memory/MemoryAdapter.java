package com.ttth.lovetogether.memory;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ttth.lovetogether.OnItemClick;
import com.ttth.lovetogether.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by HangPC on 5/22/2017.
 */

public class MemoryAdapter extends RecyclerView.Adapter<MemoryAdapter.MyViewHolder> {
    private ArrayList<ItemMemory> arrMemory;
    private OnItemClick mOnItemClick;
    private OnItemLongClick mOnItemLongClick;
    private Context mContext;
    public void setmOnItemLongClick(OnItemLongClick mOnItemLongClick) {
        this.mOnItemLongClick = mOnItemLongClick;
    }

    public void setmOnItemClick(OnItemClick mOnItemClick) {
        this.mOnItemClick = mOnItemClick;
    }

    public MemoryAdapter(ArrayList<ItemMemory> arrMemory, Context context) {
        this.arrMemory = arrMemory;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_memory, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (arrMemory.size() == 0 || arrMemory == null) return;
        ItemMemory data = arrMemory.get(position);
        holder.tvNameMemory.setText(data.getName());
        holder.tvDateMemory.setText(data.getDate());
        holder.tvDateCome.setText(data.getDateCome());
        File file = new File(data.getUriImage());
        Drawable drawable = Drawable.createFromPath(file.getAbsolutePath());
        holder.imgBackGround.setBackgroundDrawable(drawable);
         holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClick.onClickListener(position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOnItemLongClick.onItemLongClick(position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrMemory == null || arrMemory.size() == 0 ? 0 : arrMemory.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvNameMemory, tvDateMemory, tvDateCome;
        LinearLayout imgBackGround;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvNameMemory = (TextView) itemView.findViewById(R.id.tvNameMemory);
            tvDateMemory = (TextView) itemView.findViewById(R.id.tvDateMemory);
            tvDateCome = (TextView) itemView.findViewById(R.id.tvDateCome);
            imgBackGround = (LinearLayout) itemView.findViewById(R.id.imgBackground);

            Typeface typeFace=Typeface.createFromAsset(mContext.getResources().getAssets(),"VL_Hillda.otf");
            tvNameMemory.setTypeface(typeFace);
            tvDateMemory.setTypeface(typeFace);
        }
    }
    interface OnItemLongClick{
        void onItemLongClick(int position);
    }
}
