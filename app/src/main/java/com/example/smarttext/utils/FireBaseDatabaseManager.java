package com.example.smarttext.utils;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FireBaseDatabaseManager {
    private DatabaseReference fireBaseRef;
    private FirebaseAuth firebaseAuth;
    private String myPhoneNumber;

    public FireBaseDatabaseManager() {
        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null)
        {
            myPhoneNumber= firebaseAuth.getCurrentUser().getPhoneNumber();
        }
        fireBaseRef= FirebaseDatabase.getInstance().getReference();
    }

    public void doChat(String number,LiveChatData chatData)
    {
        fireBaseRef.child(Config.NODE_CHAT).child(myPhoneNumber).child(number).setValue(chatData);
        fireBaseRef.child(Config.NODE_CHAT).child(number).child(myPhoneNumber).setValue(chatData);
    }
    public boolean checkContactPresent(String phoneNumber)
    {
        final boolean[] result = new boolean[1];

        return result[0];
    }
}
