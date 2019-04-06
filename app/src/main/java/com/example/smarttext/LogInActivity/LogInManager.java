package com.example.smarttext.LogInActivity;

import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smarttext.R;
import com.example.smarttext.utils.Config;

public class LogInManager extends AppCompatActivity {

    Vibrator vibe;
    int phoneNumberLength;
    String phoneNumberString;
    Button vibrateButton;
    EditText loginPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_manager);
        vibrateButton = (Button) findViewById(R.id.LoginSubmit);
        vibe = (Vibrator) LogInManager.this.getSystemService(VIBRATOR_SERVICE);
        loginPhone=(EditText)findViewById(R.id.LoginEditPhone);
        }

    public void loginSubmit(View view) {
        phoneNumberString=loginPhone.getText().toString().trim();;
        phoneNumberLength=phoneNumberString.length();
        if(phoneNumberLength<10||phoneNumberLength>10) {
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
            Intent verifyOtp=new Intent(this, OtpVerification.class);
            verifyOtp.putExtra(Config.PHONE_NUMBER,"+91"+phoneNumberString);
            startActivity(verifyOtp);
            finish();
        }
    }
}
