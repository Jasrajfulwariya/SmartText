package com.example.smarttext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Toast;

public class Sample extends AppCompatActivity {

    EditText mSample;
    Button mSampleButton;
    SquliteDatabase squlite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        mSample=findViewById(R.id.Samplemesage);
        mSampleButton=findViewById(R.id.SampleButton);
        squlite=new SquliteDatabase(this);
    }

    public void fun_1(View view) {
        boolean r1= squlite.addcontact("Karan","Jasraj");
        boolean r2= squlite.insertChat("Jasraj","hi","3:00","Hello","4:00");
    }
    public void fun_2(View view)
    {
        String fetch_data=squlite.fetch_data("Jasraj");
        mSample.setText(fetch_data);

    }

}

