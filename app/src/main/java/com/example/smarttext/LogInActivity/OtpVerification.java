package com.example.smarttext.LogInActivity;


import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import com.example.smarttext.MainActivity;
import com.example.smarttext.R;
import com.example.smarttext.utils.Config;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
public class OtpVerification<mCallBacks> extends AppCompatActivity {
    TextView mOtpTimer;
    TextView mResendB;
    String phoneVerify;
    Button motpConform;
    private String mVerificationId;
    int otpFlag =0;
    private EditText codeText;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);
        init();
        allListener();
        timer();
        otpCallBack();
        phoneVerify =getIntent().getExtras().getString(Config.PHONE_NUMBER);
        motpConform=findViewById(R.id.otpConform);
        codeText=findViewById(R.id.otpEdit);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneVerify,        // Phone number to verify
                10,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,
                mCallBacks);
    }


    private void otpCallBack()
    {
        mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
                Intent I1=new Intent(OtpVerification.this, MainActivity.class);
                startActivity(I1);
                finish();
            }
            @Override
            public void onVerificationFailed(FirebaseException e) {

                Toast.makeText(OtpVerification.this,"error in verification",Toast.LENGTH_LONG).show();

            }
            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                mVerificationId = verificationId;
                mResendToken = token;
            }

        };
    }
    private void timer()
    {
        if(otpFlag ==0)
        {
            new CountDownTimer(60000, 1000) {
                public void onTick(long millisUntilFinished) {
                    if(otpFlag ==0)
                        mOtpTimer.setText( ""+millisUntilFinished / 1000);
                }
                public void onFinish() {
                    mOtpTimer.setText("0");
                    otpFlag =1;
                    mResendB.setTextColor(getResources().getColor(R.color.mediumBlue));
                }

            }.start();
        }
        else
        {
            new CountDownTimer(60000, 1000) {
                public void onTick(long millisUntilFinished) {
                    mOtpTimer.setText( ""+millisUntilFinished / 1000);
                }
                public void onFinish() {
                    mResendB.setTextColor(getResources().getColor(R.color.mediumBlue));
                }

            }.start();
        }
    }
    private void allListener()
    {
        mResendB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOtpTimer.getText()=="0")
                {
                    mResendB.setTextColor(getResources().getColor(R.color.GREY));
                    timer();
                    //TODO: Resand Otp;
                }
            }
        });
    }
    private void init()
    {
        // otpLoading=(ProgressBar)findViewById(R.id.otpProgressBar);
        mOtpTimer=findViewById(R.id.otpTimer);
        mResendB=findViewById(R.id.otpResend);
    }

    public void otpToLogin(View view) {
        Intent I1=new Intent(this,MainActivity.class);
        startActivity(I1);
        finish();
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth=FirebaseAuth.getInstance();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = task.getResult().getUser();
                            Intent loggedIn = new Intent(OtpVerification.this,MainActivity.class);
                            startActivity(loggedIn);
                            finish();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Toast.makeText(OtpVerification.this,"error",Toast.LENGTH_LONG).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    public void checkValidity(View view) {
        String code= codeText.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }
}
