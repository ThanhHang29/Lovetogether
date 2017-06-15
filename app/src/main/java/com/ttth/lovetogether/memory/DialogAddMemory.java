package com.ttth.lovetogether.memory;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.theartofdev.edmodo.cropper.CropImage;
import com.ttth.lovetogether.ToastUtils;
import com.ttth.lovetogether.main.DateDialog;
import com.ttth.lovetogether.R;

import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

/**
 * Created by HangPC on 5/17/2017.
 */

public class DialogAddMemory extends DialogFragment implements View.OnClickListener, DateDialog.GetNumDay {
    private static final String EXTRA_DATE = "extra_date" ;
    private static final int SELECT_PICTURE = 2;
    private static final int RESULT_CROP = 0;
    private EditText edtNameMemory;
    private TextView tvDate,tvChooseImage, btnOk, btnNo;
    private RelativeLayout rfChooseImage;
    private ImageView imageMemory;
    private String date;
    private GetInforMemory mGetInforMemory;
    private String selectedBitmap;

    public void setmGetInforMemory(GetInforMemory mGetInforMemory) {
        this.mGetInforMemory = mGetInforMemory;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getDataFromBundle(savedInstanceState);
    }

//    private void getDataFromBundle(Bundle bundle) {
//        if (bundle == null)return;
//        bundle.getString(EXTRA_DATE);
//    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putString(EXTRA_DATE, date);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_memory, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Calendar today = Calendar.getInstance();
        date = today.get(Calendar.DAY_OF_MONTH)+"/"+(today.get(Calendar.MONTH)+1)+"/"+today.get(Calendar.YEAR);
        edtNameMemory = (EditText) view.findViewById(R.id.edtNameMemory);
        tvDate = (TextView) view.findViewById(R.id.tvDateMemory);
        tvChooseImage = (TextView) view.findViewById(R.id.tvChooseImage);
        imageMemory = (ImageView) view.findViewById(R.id.image_memory);
        rfChooseImage = (RelativeLayout) view.findViewById(R.id.chooseImage);
        btnOk = (TextView) view.findViewById(R.id.btn_ok);
        btnNo = (TextView) view.findViewById(R.id.btn_no);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        initView();

        Typeface typeFace=Typeface.createFromAsset(getResources().getAssets(),"VL_Hillda.otf");
        edtNameMemory.setTypeface(typeFace);
        tvDate.setTypeface(typeFace);
        tvChooseImage.setTypeface(typeFace);
        tvTitle.setTypeface(typeFace);
        btnOk.setTypeface(typeFace);
        btnNo.setTypeface(typeFace);


    }

    private void initView() {
        btnNo.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        tvDate.setOnClickListener(this);
        rfChooseImage.setOnClickListener(this);

        tvDate.setText(date);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chooseImage:
                imageMemory.setVisibility(View.VISIBLE);
                clickBtnChooseImage();
                break;
            case R.id.tvDateMemory:
                showDatePicker();
                break;
            case R.id.btn_ok:
                clickBtnOk();
                break;
            case R.id.btn_no:
                dismiss();
                break;
        }
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
            String selectedImagePath = getPath(selectedImageUri);
            CropImage.activity(selectedImageUri).start(getActivity(), this);
            tvChooseImage.setText(selectedImagePath);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                this.selectedBitmap = resultUri.getPath();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
//    private void cropImage(String picUri){
//        File file = new File(picUri);
//        Uri contentUri = Uri.fromFile(file);
//        getContext().grantUriPermission("com.android.camera",contentUri,
//                Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(contentUri, "image/*");
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//        intent.putExtra("crop", "true");
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        intent.putExtra("outputX", 280);
//        intent.putExtra("outputY", 280);
//
//        intent.putExtra("return-data", true);
//        startActivityForResult(intent, RESULT_CROP);
//    }
    public  String getPath(Uri uri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getContext().getContentResolver().query(uri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    private void clickBtnOk() {
        if (edtNameMemory.getText().toString().isEmpty()){
            ToastUtils.toastApp(getActivity(),getString(R.string.title_empty));
        }else if (tvChooseImage.getText().equals(getString(R.string.choose_image))){
            ToastUtils.toastApp(getActivity(), getString(R.string.must_choose));
        }else {
            mGetInforMemory.getInforMemory(edtNameMemory.getText().toString(), tvDate.getText().toString(), "", selectedBitmap);
            dismiss();
        }
    }

    private void showDatePicker() {
        DateDialog dateDialog = new DateDialog();
        FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
        dateDialog.setGetNumDay(DialogAddMemory.this);
        dateDialog.show(transaction,"date picker");
    }

    @Override
    public void setNumDay(String numDay) {
    }

    @Override
    public void getSetDay(int setYear, int setMonth, int setDay) {
        date = setDay+"/"+(setMonth+1)+"/"+setYear;
        tvDate.setText(date);
    }
    interface GetInforMemory{
        void getInforMemory(String name, String day, String dayCome, String pathImage);
    }
}
