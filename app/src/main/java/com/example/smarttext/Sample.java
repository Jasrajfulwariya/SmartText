package com.example.smarttext;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
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
    SquliteContactinfo contact_squlite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        mSample=findViewById(R.id.Samplemesage);
        mSampleButton=findViewById(R.id.SampleButton);
        squlite=new SquliteDatabase(this,"Amit");
        contact_squlite=new SquliteContactinfo(this);
    }

    public void fun_1(View view) {
        boolean r1= squlite.addcontact("Karan","Amit");
        boolean r2= squlite.insertChat("Jasraj","hi","3:00","Hello","4:00");
    }
    public void fun_2(View view)
    {
        Cursor cs=squlite.fetch_data("Jasraj");
        StringBuffer buffer=new StringBuffer();
        while (cs.moveToNext())
        {
            buffer.append("Id"+cs.getString(0));
            buffer.append("Id"+cs.getString(1)+"\n");

        }
        showmesages("data",buffer.toString());
    }
    public void showmesages(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void fun_3(View view) {
        boolean r1= contact_squlite.insert_contact("Karan","8950845757","@123");

    }

    public void fun_4(View view) {
        Cursor cs=contact_squlite.fetch_data("Jasraj");
        StringBuffer buffer=new StringBuffer();
        while (cs.moveToNext())
        {
            buffer.append("Id"+cs.getString(0));
            buffer.append("Id"+cs.getString(1)+"\n");

        }
        showmesages("data",buffer.toString());

    }
}

