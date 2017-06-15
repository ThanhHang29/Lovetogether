package com.ttth.lovetogether.utils;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class BitmapAsynctask extends AsyncTask<Integer, Void, Bitmap> {
    private Context mContext;
    private ImageView imageView;
    private FrameLayout frameLayout;
    private LinearLayout linearLayout;
    private int data = 0;

    public BitmapAsynctask(Context mContext, ImageView imageView) {
        this.mContext = mContext;
        this.imageView = imageView;
    }

    public BitmapAsynctask(Context mContext, FrameLayout frameLayout) {
        this.mContext = mContext;
        this.frameLayout = frameLayout;
    }

    public BitmapAsynctask(Context mContext, LinearLayout linearLayout) {
        this.mContext = mContext;
        this.linearLayout = linearLayout;
    }

    public static int caculateInsampleSize(BitmapFactory.Options options, int reWidth, int reHeight){
        final int imageHeight = options.outHeight;
        final int imageWidth = options.outWidth;
        int inSampleSize = 10;
        if (imageWidth >  reWidth || imageHeight > reHeight){
            int halfHeight = imageHeight/2;
            int halfWidth = imageWidth/2;
            while ((halfHeight/inSampleSize) >= reHeight && (halfWidth/inSampleSize) >= reWidth){
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
    public static Bitmap decodeImageBitmap(Resources res, int id, int reWidth, int reHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, id, options);
        options.inSampleSize = caculateInsampleSize(options, reWidth, reHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, id, options);
    }

    @Override
    protected Bitmap doInBackground(Integer... params) {
        data = params[0];
        return decodeImageBitmap(mContext.getResources(), data, 250, 250);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (imageView!=null){
            imageView.setImageBitmap(bitmap);
        }
        if (frameLayout!= null){
            frameLayout.setBackgroundDrawable(new BitmapDrawable(bitmap));
        }
        if (linearLayout != null){
            linearLayout.setBackgroundDrawable(new BitmapDrawable(bitmap));
        }
    }
}