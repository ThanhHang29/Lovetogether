package com.ttth.lovetogether.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.ttth.lovetogether.memory.ItemMemory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by HangPC on 5/26/2017.
 */

public class DatabaseManager {
    private static final String TABLE_NAME = "MEMORY";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_NAME = "NAME";
    private static final String COLUMN_DATE = "DATE";
    private static final String COLUMN_IMAGE = "IMAGE";
    private Context mContext;
    private SQLiteDatabase database;
    public static final String PATH = Environment.getDataDirectory().getPath()+"/data/com.ttth.lovetogether/database/LISTMEMORY.sqlite";

    public DatabaseManager(Context mContext) {
        this.mContext = mContext;
        readDatabase(mContext);
    }

    public void readDatabase(Context mContext) {
        try {
            InputStream inputStream = mContext.getAssets().open("LISTMEMORY.sqlite");
            File file = new File(PATH);
            if (!file.exists()){
                File parent = file.getParentFile();
                parent.mkdirs();
                file.createNewFile();
                FileOutputStream outputStream = new FileOutputStream(file);
                byte []b= new byte[1024];
                int count = inputStream.read(b);
                while (count!=-1){
                    outputStream.write(b,0,count);
                    count = inputStream.read(b);
                }
                outputStream.close();
            }
            inputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void openDatabase(){
        database = mContext.openOrCreateDatabase(PATH,mContext.MODE_APPEND,null);
    }
    public void closeDatabase(){
        database.close();
    }
    public ArrayList<ItemMemory> getData(){
        ArrayList<ItemMemory>arrMemory = new ArrayList<>();
        openDatabase();
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null,null);
        cursor.moveToFirst();
        int indextID = cursor.getColumnIndex(COLUMN_ID);
        int intdextName = cursor.getColumnIndex(COLUMN_NAME);
        int intdextDate = cursor.getColumnIndex(COLUMN_DATE);
        int intdextImage = cursor.getColumnIndex(COLUMN_IMAGE);
        while (!cursor.isAfterLast()){
            int id = cursor.getInt(indextID);
            String name = cursor.getString(intdextName);
            String date = cursor.getString(intdextDate);
            String image = cursor.getString(intdextImage);
            ItemMemory memory = new ItemMemory(id, name, date,"", image);
            arrMemory.add(memory);
            cursor.moveToNext();
        }
        closeDatabase();
        return arrMemory;
    }
    public long insertData(String name, String date, String image){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_IMAGE, image);
        openDatabase();
        long id = database.insert(TABLE_NAME, null, contentValues);
        closeDatabase();
        return id;
    }
    public int deleteData(int id){
        openDatabase();
        String[] selectionArg={id+""};
        int row = database.delete(TABLE_NAME,COLUMN_ID+"=?",selectionArg);
        closeDatabase();
        return row;
    }
}
