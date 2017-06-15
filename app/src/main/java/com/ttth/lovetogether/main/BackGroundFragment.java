package com.ttth.lovetogether.main;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.theartofdev.edmodo.cropper.CropImage;
import com.ttth.lovetogether.BaseFragment;
import com.ttth.lovetogether.OnItemClick;
import com.ttth.lovetogether.adapter.maindialog.BackGroundAdapter;
import com.ttth.lovetogether.model.ItemBackGround;
import com.ttth.lovetogether.R;
import com.ttth.lovetogether.activities.MainActivity;

import java.io.File;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by HangPC on 5/17/2017.
 */

public class BackGroundFragment extends BaseFragment implements OnItemClick {
    private static final int SELECT_PICTURE = 0;
    private static final int RESULT_CROP = 1;
    private BackGroundAdapter mAdapter;
    private RecyclerView recyclerView;
    private ArrayList<ItemBackGround> arrImage;
    private SetBackground mSetBackground;
    private TextView btnChooseImage;
    private Uri contentUri;
    @Override
    protected int getResLayout() {
        return R.layout.fragment_choose_backgorud;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mSetBackground = (SetBackground) context;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        btnChooseImage = (TextView) view.findViewById(R.id.btnChooseImage);
        TextView tvTitle = (TextView) view.findViewById(R.id.title_actionbar);
        ((MainActivity)getActivity()).isHasActionbar(false);
        initData();
        initView();

        Typeface typeFace=Typeface.createFromAsset(getResources().getAssets(),"VL_Hillda.otf");
        tvTitle.setTypeface(typeFace);
        btnChooseImage.setTypeface(typeFace);
    }

    private void initView() {
        mAdapter = new BackGroundAdapter(arrImage, getContext());
        mAdapter.setmOnItemClickListiner(this);
        recyclerView.setAdapter(mAdapter);

        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBtnChooseImage();
            }
        });
    }

    private void initData() {
        arrImage = new ArrayList<>();
        arrImage.add(new ItemBackGround(R.drawable.bg1));
        arrImage.add(new ItemBackGround(R.drawable.bg2));
        arrImage.add(new ItemBackGround(R.drawable.bg3));
        arrImage.add(new ItemBackGround(R.drawable.bg4));
        arrImage.add(new ItemBackGround(R.drawable.bg5));
        arrImage.add(new ItemBackGround(R.drawable.bg6));
        arrImage.add(new ItemBackGround(R.drawable.bg7));
        arrImage.add(new ItemBackGround(R.drawable.bg8));
        arrImage.add(new ItemBackGround(R.drawable.bg9));
        arrImage.add(new ItemBackGround(R.drawable.bg10));
        arrImage.add(new ItemBackGround(R.drawable.bg11));
        arrImage.add(new ItemBackGround(R.drawable.bg12));
    }

    @Override
    public void onClickListener(int position) {
        Log.e("____","+++++++"+arrImage.get(position).getImage());
        mSetBackground.setBackground(arrImage.get(position).getImage());
    }
    public interface SetBackground{
        void setBackground(int image);
        void setBackground(String path);
    }
    private void clickBtnChooseImage() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent,
//                "Select Picture"), SELECT_PICTURE);
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, SELECT_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == SELECT_PICTURE) {
            Uri selectedImageUri = data.getData();
            CropImage.activity(selectedImageUri).start(getActivity(), this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                String path = resultUri.getPath();
                mSetBackground.setBackground(path);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
    private void cropImage(String picUri){
        File file = new File(picUri);
        Uri contentUri = Uri.fromFile(file);
        getContext().grantUriPermission("com.android.camera",contentUri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(contentUri, "image/*");

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 2);
        intent.putExtra("outputX", 280);
        intent.putExtra("outputY", 280);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_CROP);
    }
    public  String getPath(Context context, Uri uri) {
        if( uri == null ) {
            return null;
        }
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(uri,  proj, null, null, null);
            int column_index = 0;
            if (cursor != null) {
                column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return uri.getPath();
    }

}
