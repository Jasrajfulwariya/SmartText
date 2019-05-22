package com.example.smarttext;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.smarttext.utils.Config;
import com.example.smarttext.utils.LiveChatData;
import com.example.smarttext.utils.SubChatRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainChat extends AppCompatActivity {
    CircleImageView profileImage;
    boolean firstFlag=false;
    TextView username;
    RecyclerView mChatRecyclerView;
    ArrayList<LiveChatData> mData=new ArrayList<>();
    EditText mGettingMessage;
    FirebaseAuth mAuth;
    SubChatRecyclerAdapter chatRecyclerAdapter;
    String senderPhoneNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);
        mAuth=FirebaseAuth.getInstance();
        mGettingMessage=findViewById(R.id.subChatMessageSendBox);
        profileImage=findViewById(R.id.subChatProfilePic);
        username=findViewById(R.id.subChatUserName);
        senderPhoneNo=getIntent().getStringExtra(Config.PHONE_NUMBER);
        String senderName=getIntent().getStringExtra(Config.USER_NAME);
        username.setText(senderName);
        setAdapter();
        receiveMassage();
        //Todo: SetProfilePic
    }
    public void setAdapter()
    {
        mChatRecyclerView=findViewById(R.id.subChatRecView);
        chatRecyclerAdapter=new SubChatRecyclerAdapter(getApplicationContext(),mData);
        mChatRecyclerView.setAdapter(chatRecyclerAdapter);
        mChatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    public void exitChat(View view) {
        finish();
    }

    public void doVideoCall(View view) {
        //TODO: Doing Video Calling Using This Button
    }

    public void doMessageChat(View view) {
        LiveChatData newSandData=new LiveChatData();
        newSandData.setData(mGettingMessage.getText().toString());
        newSandData.setSedRec(0);
        newSandData.setTime(Calendar.getInstance().getTime().toString());
        mData.add(newSandData);
        newSandData=new LiveChatData();
        newSandData.setData(mGettingMessage.getText().toString());
        newSandData.setSedRec(1);
        newSandData.setTime(Calendar.getInstance().getTime().toString());
        FirebaseDatabase.getInstance().getReference()
                .child(Config.NODE_ALL_USER_CHAT).child(senderPhoneNo)
                .child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).setValue(newSandData);
        chatRecyclerAdapter.notifyItemChanged(mData.size());
    }
    public void receiveMassage()
    {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference()
                .child(Config.NODE_ALL_USER_CHAT).child(FirebaseAuth.getInstance()
                .getCurrentUser().getPhoneNumber()).child(senderPhoneNo);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(firstFlag)
                {
                        mData.add(dataSnapshot.getValue(LiveChatData.class));
                        chatRecyclerAdapter.notifyItemChanged(mData.size());
                }
                firstFlag=true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void sendImage(View view) {
        //TODO: Sending Image
    }
}
