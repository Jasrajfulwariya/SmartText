package com.example.smarttext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.smarttext.Adapters.ActiveListRecyclerAdapter;

public class calllayout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calllayout);
        RecyclerView callreccle=(RecyclerView)findViewById(R.id.callrecycle);
        callreccle.setLayoutManager(new LinearLayoutManager(this));
        callreccle.setAdapter(new callListRecyclerView(this));
    }
}

