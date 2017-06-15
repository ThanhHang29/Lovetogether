package com.ttth.lovetogether.adapter.maindialog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ttth.lovetogether.model.ItemBackGround;
import com.ttth.lovetogether.OnItemClick;
import com.ttth.lovetogether.R;
import com.ttth.lovetogether.utils.BitmapAsynctask;

import java.util.ArrayList;

/**
 * Created by HangPC on 5/17/2017.
 */

public class BackGroundAdapter extends RecyclerView.Adapter<BackGroundAdapter.MyViewHolder>  {
    private ArrayList<ItemBackGround>arrImage;
    private OnItemClick mOnItemClickListiner;
    private Context mContext;
    public BackGroundAdapter(ArrayList<ItemBackGround> arrImage, Context context) {
        this.arrImage = arrImage;
        this.mContext = context;
    }

    public void setmOnItemClickListiner(OnItemClick mOnItemClickListiner) {
        this.mOnItemClickListiner = mOnItemClickListiner;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_background, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final ItemBackGround itemBackGround = arrImage.get(position);
        if (itemBackGround == null) return;

        BitmapAsynctask bitmapAsynctask = new BitmapAsynctask(mContext, holder.imgBackGround);
        bitmapAsynctask.execute(itemBackGround.getImage());
//        Picasso.with(mContext).load(itemBackGround.getImage()).into(holder.imgBackGround);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListiner.onClickListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrImage == null || arrImage.size() == 0 ? 0 : arrImage.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
//        LinearLayout imgBackGround;
        private ImageView imgBackGround;
        public MyViewHolder(View itemView) {
            super(itemView);
            imgBackGround = (ImageView) itemView.findViewById(R.id.imgBackground);
        }
    }
//    public interface OnItemClickListiner{
//        void onClickListener(int position);
//    }
}
