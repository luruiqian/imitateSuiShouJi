package com.cashbook.cashbook.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cashbook.cashbook.R;
import com.cashbook.cashbook.adapter.ImageScaleAdapter;

public class ImageScaleActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_scale);
        mRecyclerView = findViewById(R.id.rv);
        ImageScaleAdapter imageScaleAdapter = new ImageScaleAdapter(this,mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(imageScaleAdapter);
    }
}