package com.ttth.lovetogether.memory;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.ttth.lovetogether.BaseFragment;
import com.ttth.lovetogether.utils.DatabaseManager;
import com.ttth.lovetogether.OnItemClick;
import com.ttth.lovetogether.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by HangPC on 5/16/2017.
 */

public class MemoryFragment extends BaseFragment implements DialogAddMemory.GetInforMemory, OnItemClick, MemoryAdapter.OnItemLongClick {
    private ImageView btnAdd;
    private MemoryAdapter mAdapter;
    private ArrayList<ItemMemory> arrMemory;
    private RecyclerView recyclerView;
    private DatabaseManager mManager;

    @Override
    protected int getResLayout() {
        return R.layout.fragment_memory;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrMemory = new ArrayList<>();
        mManager = new DatabaseManager(getContext());
        arrMemory.addAll(mManager.getData());
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        btnAdd = (ImageView) view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogAddMemory dialogAddMemory = new DialogAddMemory();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                dialogAddMemory.setmGetInforMemory(MemoryFragment.this);
                dialogAddMemory.show(transaction, "dialog memory");
            }
        });
        initView();
    }

    private void initView() {
//        arrMemory = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MemoryAdapter(arrMemory, getContext());
        recyclerView.setAdapter(mAdapter);
        mAdapter.setmOnItemClick(this);
        mAdapter.setmOnItemLongClick(this);
    }

    @Override
    public void getInforMemory(String name, String day, String dayCome, String pathImage) {
        mManager.insertData(name, day, pathImage);
        arrMemory.clear();
        arrMemory.addAll(mManager.getData());
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
    private String encodeImageBitmap(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    @Override
    public void onClickListener(int position) {

    }

    @Override
    public void onItemLongClick(int position) {
        createAndShowAlertDialog(position);
    }
    private void createAndShowAlertDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.delete_memory));
        builder.setCancelable(false);
        builder.setMessage(getString(R.string.message_delete));
        Log.e("__________","++++++++++"+position);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mManager.deleteData(arrMemory.get(position).getId());
                arrMemory.clear();
                arrMemory.addAll(mManager.getData());
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
