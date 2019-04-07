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
        fireBaseRef.child(Config.NODE_ALL_CONTACT).orderByChild(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String jas=(String) dataSnapshot.getValue();
                result[0] = dataSnapshot.exists();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return result[0];
    }
}
