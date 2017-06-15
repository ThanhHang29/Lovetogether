package com.ttth.lovetogether.main;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.WallpaperManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.theartofdev.edmodo.cropper.CropImage;
import com.ttth.lovetogether.BaseFragment;
import com.ttth.lovetogether.R;
import com.ttth.lovetogether.ToastUtils;
import com.ttth.lovetogether.adapter.maindialog.DialogOptionAdapter;
import com.ttth.lovetogether.model.ItemOptionDialog;
import com.ttth.lovetogether.utils.BitmapAsynctask;
import com.ttth.lovetogether.views.CircleImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * Created by HangPC on 5/16/2017.
 */

public class MainFragment extends BaseFragment implements  View.OnClickListener,
        AdapterView.OnItemClickListener, DateDialog.GetNumDay{
    private static final String NUM_DAY = "num_day";
    private static final String AVATAR_LEFT = "avatar_left";
    private static final String AVATAR_RIGHT = "avatar_right";
    private static final String TAKE_PHOTO = "take_photo";
    public static final String EXTRA_KEY_NUMDAY = "extra_key_numday";
    public static final String KEY_DAY_SAVE = "key_day_save";
    public static final String KEY_SET_YEAR = "key_set_year";
    public static final String KEY_SET_MONTH = "key_set_month";
    public static final String KEY_SET_SETDAY = "key_set_day";
    public static final String EXTRA_KEY_SAVE_NAME = "extra_key_save_name";
    public static final String KEY_NAME_LEFT = "key_name_left";
    public static final String KEY_NAME_RIGHT = "key_name_right";
    private static final int SELECT_PICTURE = 0;
    private static final int RESULT_CROP = 1;
    private static final String EXTRA_KEY_SAVE_AVA_LEFT = "extra_key_save_ava_left";
    private static final String KEY_SAVE_AVA_LEFT = "key_save_ava_left";
    private static final String EXTRA_KEY_SAVE_AVA_RIGHT = "extra_key_save_ava_right";
    private static final String KEY_SAVE_AVA_RIGHT = "key_save_ava_right";
    private ImageView imgNumDay;
    private CircleImageView avatarLeft, avatarRight;
    private TextView tvNumDay, tvNameLeft, tvNameRight, tvDay;
    private EditText edtChangeName;
    private ArrayList<ItemOptionDialog> arrOptionDay, arrOptionAvatar, arrOptionTakePhoto;
    private Dialog dialog, dialogChangeName;
    private String clickPosition;
    private File imageFile;
    private Bitmap bitmap;
    private String numDay;
    private DateDialog dateDialog;
    private GetUpDateNotification mUpDateNotification;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mUpDateNotification = (GetUpDateNotification) context;
    }


    @Override
    protected int getResLayout() {
        return R.layout.fragment_main;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgNumDay = (ImageView) view.findViewById(R.id.image_day);
        avatarLeft = (CircleImageView) view.findViewById(R.id.avatar_left);
        avatarRight = (CircleImageView) view.findViewById(R.id.avatar_right);
        tvNumDay = (TextView) view.findViewById(R.id.tv_num_day);
        tvNameLeft = (TextView) view.findViewById(R.id.name_left);
        tvNameRight = (TextView) view.findViewById(R.id.name_right);
        tvDay = (TextView) view.findViewById(R.id.tvDay);
        initView();
        retriveDateSave();
        Log.e("_____","/////");
//        sendData();
        Typeface typeFace=Typeface.createFromAsset(getResources().getAssets(),"VL_Hillda.otf");
        tvNumDay.setTypeface(typeFace);
        tvNameLeft.setTypeface(typeFace);
        tvDay.setTypeface(typeFace);
        tvNameRight.setTypeface(typeFace);

    }

    private void initView() {
        getDataDialogDay();
        getDataDialogAvatar();
        imgNumDay.setOnClickListener(this);
        avatarLeft.setOnClickListener(this);
        avatarRight.setOnClickListener(this);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_KEY_NUMDAY, numDay);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_day:
                showDialog(arrOptionDay);
                clickPosition = NUM_DAY;
                break;
            case R.id.avatar_left:
                showDialog(arrOptionAvatar);
                clickPosition = AVATAR_LEFT;
                break;
            case R.id.avatar_right:
                showDialog(arrOptionAvatar);
                clickPosition = AVATAR_RIGHT;
                break;
            case R.id.btn_ok:
                setChangeName();
                break;
            case R.id.btn_no:
                dialogChangeName.dismiss();
                visibleInfor();
                break;
        }
    }
    public void showDialog(ArrayList<ItemOptionDialog>arrData){
        dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_main, null);
        dialog.setContentView(view);
        ListView lvList = (ListView) view.findViewById(R.id.lvList);
        DialogOptionAdapter mAdapter = new DialogOptionAdapter(getActivity(), android.R.layout.simple_list_item_1, arrData);
        lvList.setAdapter(mAdapter);
        lvList.setOnItemClickListener(this);
        dialog.show();
    }

    private void getDataDialogDay(){
        arrOptionDay = new ArrayList<>();
        arrOptionDay.add(new ItemOptionDialog(getString(R.string.change_day)));
        arrOptionDay.add(new ItemOptionDialog(getString(R.string.change_background)));
        arrOptionDay.add(new ItemOptionDialog(getString(R.string.take_screen)));
    }
    private void getDataDialogAvatar(){
        arrOptionAvatar = new ArrayList<>();
        arrOptionAvatar.add(new ItemOptionDialog(getString(R.string.change_avatar)));
        arrOptionAvatar.add(new ItemOptionDialog(getString(R.string.change_name)));
    }
    private void getDataDialogTakePhoto(){
        arrOptionTakePhoto = new ArrayList<>();
        arrOptionTakePhoto.add(new ItemOptionDialog(getString(R.string.watch_image)));
        arrOptionTakePhoto.add(new ItemOptionDialog(getString(R.string.save_device)));
        arrOptionTakePhoto.add(new ItemOptionDialog(getString(R.string.set_background)));
//        arrOptionTakePhoto.add(new ItemOptionDialog(getString(R.string.share_fb)));
    }

    private void chooseOptionNumDay(int position){
        if (position == 0){
            ToastUtils.Toast(getActivity(),"đổi ngày");
            dialog.dismiss();
            changeDate();
        }
        if (position == 1){
            ToastUtils.Toast(getActivity(),"đổi hình nền");
            dialog.dismiss();
            addBackGroundFragment();
        }
        if (position == 2){
            ToastUtils.Toast(getActivity(),"chụp màn hình");
            dialog.dismiss();
            getDataDialogTakePhoto();
            takeScreenshot();
            clickPosition = TAKE_PHOTO;
        }
    }

    private void addBackGroundFragment() {
        String tag = getActivity().getSupportFragmentManager().getClass().getName();
        BackGroundFragment backGroundFragment = new BackGroundFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flFragment,backGroundFragment);
        transaction.addToBackStack(tag);
        transaction.commit();
    }

    private void chooseOptionAvatar(int position){
        if (position == 0){
            ToastUtils.Toast(getActivity(),"đổi ảnh");
            dialog.dismiss();
            chooseImage();
        }
        if (position == 1){
            ToastUtils.Toast(getActivity(),"đổi tên");
            dialog.dismiss();
            showDialogChangeName();
        }
    }
    private void invisibleInfor(){
        tvNumDay.setVisibility(View.INVISIBLE);
        tvDay.setVisibility(View.INVISIBLE);
        tvNameLeft.setVisibility(View.INVISIBLE);
        tvNameRight.setVisibility(View.INVISIBLE);
        avatarLeft.setVisibility(View.INVISIBLE);
        avatarRight.setVisibility(View.INVISIBLE);
    }
    private void visibleInfor(){
        tvNumDay.setVisibility(View.VISIBLE);
        tvDay.setVisibility(View.VISIBLE);
        tvNameLeft.setVisibility(View.VISIBLE);
        tvNameRight.setVisibility(View.VISIBLE);
        avatarLeft.setVisibility(View.VISIBLE);
        avatarRight.setVisibility(View.VISIBLE);
    }
    @Override
    @SuppressLint("NewApi")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT_PICTURE) {
            if (resultCode == RESULT_OK) {
                Uri selectedImageUri = data.getData();
                CropImage.activity(selectedImageUri).start(getActivity(),this);
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            Log.e("_________////////","+++++++++++"+result);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                if (clickPosition.equals(AVATAR_LEFT)){
                    avatarLeft.setImageURI(resultUri);
                    String encodeImageBitMap = resultUri.getPath();
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences(EXTRA_KEY_SAVE_AVA_LEFT, Context.MODE_PRIVATE).edit();
                    editor.putString(KEY_SAVE_AVA_LEFT, encodeImageBitMap);
                    editor.apply();
                }
                if (clickPosition.equals(AVATAR_RIGHT)){
                    avatarRight.setImageURI(resultUri);
                    String encodeImageBitMap = resultUri.getPath();
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences(EXTRA_KEY_SAVE_AVA_RIGHT, Context.MODE_PRIVATE).edit();
                    editor.putString(KEY_SAVE_AVA_RIGHT, encodeImageBitMap);
                    editor.apply();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void chooseImage(){
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, SELECT_PICTURE);
    }
    public String getPath( Uri uri) {
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
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (clickPosition.equals(NUM_DAY)){
            chooseOptionNumDay(position);
        }else if (clickPosition.equals(TAKE_PHOTO)){
            chooseOptionTakePhoto(position);
        }
        if (clickPosition.equals(AVATAR_LEFT)||clickPosition.equals(AVATAR_RIGHT)){
            chooseOptionAvatar(position);
        }
    }
    private void changeDate(){
        dateDialog = new DateDialog();
        android.app.FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
        dateDialog.setGetNumDay(MainFragment.this);
        dateDialog.show(ft,"Date picker");
    }

    @Override
    public void setNumDay(String numDay) {
        this.numDay = numDay;
        tvNumDay.setText(this.numDay);
        mUpDateNotification.setUpdateNumDay(numDay);
    }

    @Override
    public void getSetDay(int setYear, int setMonth, int setDay) {
        Log.e("-----------","======="+setDay+"-"+setMonth+"-"+setYear);
        mUpDateNotification.setUpdateDay(setDay+"-"+setMonth+"-"+setYear);

        SharedPreferences.Editor editor = getActivity().getSharedPreferences(KEY_DAY_SAVE, Context.MODE_PRIVATE).edit();
        editor.putString(EXTRA_KEY_NUMDAY, numDay);
        editor.putInt(KEY_SET_YEAR, setYear);
        editor.putInt(KEY_SET_MONTH, setMonth);
        editor.putInt(KEY_SET_SETDAY, setDay);
        editor.apply();
        editor.commit();
    }
    private void retriveDateSave(){
        SharedPreferences prefsDay, prefsName, prefAvaLeft, prefAvaRight;
        prefsDay = getActivity().getSharedPreferences(KEY_DAY_SAVE, Context.MODE_PRIVATE);
        numDay = prefsDay.getString(EXTRA_KEY_NUMDAY, "0");
        int setYear, setMonth, setDay;
        setYear = prefsDay.getInt(KEY_SET_YEAR, 0);
        setMonth = prefsDay.getInt(KEY_SET_MONTH, 1);
        setDay = prefsDay.getInt(KEY_SET_SETDAY, 2);
        Calendar today = Calendar.getInstance();
        Calendar thatDay = Calendar.getInstance();
        thatDay.set(Calendar.DAY_OF_MONTH, setDay);
        thatDay.set(Calendar.MONTH,setMonth);
        thatDay.set(Calendar.YEAR, setYear);
//        numDay = String.valueOf(((today.getTimeInMillis() - thatDay.getTimeInMillis())/(24*60*60*1000)+1));
        tvNumDay.setText(numDay);

        prefsName = getActivity().getSharedPreferences(EXTRA_KEY_SAVE_NAME, Context.MODE_PRIVATE);
        String nameLeft, nameRight;
        nameLeft = prefsName.getString(KEY_NAME_LEFT, getString(R.string.name_left));
        nameRight = prefsName.getString(KEY_NAME_RIGHT, getString(R.string.name_right));
        tvNameLeft.setText(nameLeft);
        tvNameRight.setText(nameRight);

        prefAvaLeft = getActivity().getSharedPreferences(EXTRA_KEY_SAVE_AVA_LEFT, Context.MODE_PRIVATE);
        prefAvaRight = getActivity().getSharedPreferences(EXTRA_KEY_SAVE_AVA_RIGHT, Context.MODE_PRIVATE);
        String uriImageLeft, uriImageRight;
        uriImageLeft = prefAvaLeft.getString(KEY_SAVE_AVA_LEFT, "");
        uriImageRight = prefAvaRight.getString(KEY_SAVE_AVA_RIGHT, "");
        File fileLeft, fileRight;
        Uri uriLeft, uriRight;
        if (!uriImageLeft.equalsIgnoreCase("")){
            fileLeft = new File(uriImageLeft);
            uriLeft = Uri.fromFile(fileLeft);
            avatarLeft.setImageURI(uriLeft);
        }else {
            BitmapAsynctask bitmapAsynctask = new BitmapAsynctask(getActivity(), avatarLeft);
            bitmapAsynctask.execute(R.drawable.ic_heart);
            avatarLeft.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_heart));
        }
        if (!uriImageRight.equalsIgnoreCase("")){
            fileRight = new File(uriImageRight);
            uriRight = Uri.fromFile(fileRight);
            avatarRight.setImageURI(uriRight);
        }else {
            BitmapAsynctask bitmapAsynctask = new BitmapAsynctask(getActivity(), avatarRight);
            bitmapAsynctask.execute(R.drawable.ic_heart);
            avatarRight.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_heart));
        }
    }

    private void showDialogChangeName(){
        TextView btnOk, btnNo;
        dialogChangeName = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_change_name, null);
        dialogChangeName.setContentView(view);
        dialogChangeName.setCancelable(false);
        edtChangeName = (EditText) view.findViewById(R.id.edt_change_name);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        btnOk = (TextView) view.findViewById(R.id.btn_ok);
        btnNo = (TextView) view.findViewById(R.id.btn_no);
        btnOk.setOnClickListener(this);
        btnNo.setOnClickListener(this);

        Typeface typeFace=Typeface.createFromAsset(getResources().getAssets(),"VL_Hillda.otf");
        btnNo.setTypeface(typeFace);
        btnOk.setTypeface(typeFace);
        edtChangeName.setTypeface(typeFace);
        tvTitle.setTypeface(typeFace);

        dialogChangeName.show();
        invisibleInfor();
    }
    private void setChangeName(){
        String name = edtChangeName.getText().toString();
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(EXTRA_KEY_SAVE_NAME,Context.MODE_PRIVATE).edit();
        if (name.isEmpty()){
            ToastUtils.toastApp(getActivity(),getString(R.string.toast_edt_empty));
        }else {
            if (clickPosition.equals(AVATAR_LEFT)){
                tvNameLeft.setText(name);

                mUpDateNotification.setUpdateNameLeft(tvNameLeft.getText().toString());

                editor.putString(KEY_NAME_LEFT,name);
                editor.apply();
                editor.commit();
            }
            if (clickPosition.equals(AVATAR_RIGHT)){
                tvNameRight.setText(name);
                mUpDateNotification.setUpdateNameRight(tvNameRight.getText().toString());

                editor.putString(KEY_NAME_RIGHT,name);
                editor.apply();
                editor.commit();
            }
            visibleInfor();
            dialogChangeName.dismiss();
        }
    }

    private void takeScreenshot(){
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            View v1 = getActivity().getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            showDialog(arrOptionTakePhoto);

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void chooseOptionTakePhoto(int position){
        if (position == 0){
            ToastUtils.Toast(getActivity(),"xem ảnh");
            openScreenshot(imageFile);
        }
        if (position == 1){
            ToastUtils.Toast(getActivity(),"lưu vào máy");
            dialog.dismiss();
            saveImage();
        }
        if (position == 2){
            ToastUtils.Toast(getActivity(),"làm hình nền");
            dialog.dismiss();
            setWallpaper();
        }
        if (position == 3){
            ToastUtils.Toast(getActivity(),"chia sẻ lên facebook");
            dialog.dismiss();
        }
    }

    private void setWallpaper() {
        WallpaperManager myWallpaperManager
                = WallpaperManager.getInstance(getActivity().getApplicationContext());
        try {
            myWallpaperManager.setBitmap(bitmap);
            ToastUtils.toastApp(getContext(),getString(R.string.set_wallpaper));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void saveImage() {
       final String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/ScreenshotLove";
        File folder = new File(path);
        if (!folder.exists()) folder.mkdirs();
        File file = new File(path, imageFile.getName());
        Log.e("__________","+++++++++"+imageFile.getName());
        addImageToGallery(file.getPath(), getContext());
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 85, outputStream);
            outputStream.flush();
            outputStream.close();
            ToastUtils.toastApp(getContext(),getString(R.string.saved_image));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }
    public static void addImageToGallery(final String filePath, final Context context) {

        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, filePath);

        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }
    public interface GetUpDateNotification{
        void setUpdateNumDay(String updateNumDay);
        void setUpdateDay(String dateLove);
        void setUpdateNameLeft(String nameLeft);
        void setUpdateNameRight(String nameRight);
    }
}
