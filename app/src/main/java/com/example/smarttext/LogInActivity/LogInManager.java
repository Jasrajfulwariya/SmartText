package com.example.smarttext.LogInActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smarttext.R;
import com.example.smarttext.utils.Config;
import com.example.smarttext.utils.Permission;

public class LogInManager extends AppCompatActivity {

    private boolean flagPermission=false;
    private Vibrator vibe;
    int phoneNumberLength;
    String phoneNumberString;
    Button vibrateButton;
    EditText loginPhone;
    private String userName="";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_manager);
        vibrateButton = (Button) findViewById(R.id.LoginSubmit);
        if(checkSelfPermission(Permission.ALL_PERMISSION_ARRAY[0])!= PackageManager.PERMISSION_GRANTED
                &&checkSelfPermission(Permission.ALL_PERMISSION_ARRAY[1])!= PackageManager.PERMISSION_GRANTED
                &&checkSelfPermission(Permission.ALL_PERMISSION_ARRAY[2])!= PackageManager.PERMISSION_GRANTED
                &&checkSelfPermission(Permission.ALL_PERMISSION_ARRAY[3])!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,Permission.ALL_PERMISSION_ARRAY,Config.ALL_PERMISSION_VERIFY_CODE);
        }
        else flagPermission=true;
        vibe = (Vibrator) LogInManager.this.getSystemService(VIBRATOR_SERVICE);
        loginPhone=(EditText)findViewById(R.id.LoginEditPhone);
        }

    public void loginSubmit(View view) {
        if(flagPermission)
        {
            userName=((EditText)findViewById(R.id.LoginEditName)).getText().toString().trim();
            phoneNumberString=loginPhone.getText().toString().trim();
            phoneNumberLength=phoneNumberString.length();
            if((phoneNumberLength<10||phoneNumberLength>10)&&userName.equals("")) {
                vibe.vibrate(80);
                Toast.makeText(getApplicationContext(),"Check Your Filed",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Intent verifyOtp=new Intent(this, OtpVerification.class);
                verifyOtp.putExtra(Config.PHONE_NUMBER,"+91"+phoneNumberString);
                startActivity(verifyOtp);
                finish();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Please Give Permission",Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,Permission.ALL_PERMISSION_ARRAY,Config.ALL_PERMISSION_VERIFY_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==Config.ALL_PERMISSION_VERIFY_CODE&&grantResults.length==4)
        {
            flagPermission=true;
        }
    }
}
