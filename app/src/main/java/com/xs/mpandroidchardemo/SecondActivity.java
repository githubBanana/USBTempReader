package com.xs.mpandroidchardemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/4/4.
 */
public class SecondActivity extends AppCompatActivity {

//    private SpeedPointer pointer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
//        pointer = (SpeedPointer) findViewById(R.id.pointer);
//        pointer.setSpeed(8000);
    }

    public void touch(View view) {
        Intent intent = new Intent(this,ViewPagerActivity.class);
        startActivity(intent);
    }
}
