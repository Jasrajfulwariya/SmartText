package com.example.smarttext;

import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigInteger;

public class Loginmanager extends AppCompatActivity {

    Vibrator vibe;
    int phoneNumberLength;
    String phoneNumberString,phonewhithId;
    Button vibrateButton;
    EditText loginPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vibrateButton = (Button) findViewById(R.id.LoginSubmit);
        vibe = (Vibrator) Loginmanager.this.getSystemService(VIBRATOR_SERVICE);
        loginPhone=(EditText)findViewById(R.id.LoginEditPhone);
        phoneNumberString=loginPhone.getText().toString();
        phonewhithId="+91"+phoneNumberString;
        phoneNumberLength=phoneNumberString.length();
        if(phoneNumberLength==10)
            vibrateButton.setBackgroundColor(R.drawable.logintext);
    }

    public void loginSubmit(View view) {
        phoneNumberString=loginPhone.getText().toString();;
        phoneNumberLength=phoneNumberString.length();
        if(phoneNumberLength<10||phoneNumberLength>10)
        {
            vibrateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    vibe.vibrate(80);//80 represents the milliseconds (the duration of the vibration)
                }
            });
            Toast.makeText(getApplicationContext(),"INVALID PHONE NUMBER",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent I1=new Intent(this,otpverification.class);
            I1.putExtra("phone_number",phonewhithId);
            startActivity(I1);
        }
    }
}
