package com.example.smarttext;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import com.example.smarttext.utils.Config;
import com.example.smarttext.utils.ContactData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FirstProfileUpdateActivity extends AppCompatActivity {
    private CircleImageView profileImageShow;
    private ArrayList<ContactData> localData,data;
    private ProgressBar progressBar;
    private int dataFinalNumber;
    private boolean flagImageUpdate =false;
    private SquliteContactinfo contactinfo;
    private FirebaseUser currentUser;
    private int dataNumber=1;
    private String myNumber="",imageProfileUrl="";
    private String TAG="FirstProfileActivity";
    private Uri imageUri=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_profile_update_activity);
        init();
    }
    void init()
    {
      profileImageShow=findViewById(R.id.firstProfileProfileImage);
      data=new ArrayList<>();
      localData=new ArrayList<>();
      progressBar=findViewById(R.id.firstProfileProgressBar);
      contactinfo=new SquliteContactinfo(this);
      currentUser =FirebaseAuth.getInstance().getCurrentUser();
      myNumber= getIntent().getStringExtra(Config.PHONE_NUMBER);
    }
    public void pickImageFromPhone(View v)
    {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,Config.REQ_CODE_GET_PROFILE_IMAGE);
    }
    public void UpdateProfile(View v)
    {
        if(flagImageUpdate)
        {
            StorageReference storage=FirebaseStorage.getInstance().getReference();
            storage.child(Config.NODE_STORAGE_PROFILE_PIC).putFile(imageUri);
            imageProfileUrl=storage.getDownloadUrl().toString();
            progressBar.setVisibility(View.VISIBLE);
            localData=getContactList();
            dataFinalNumber=localData.size();
            for(int i=0;i<localData.size();i++)
            {
                checkUserExist(localData.get(i));
            }
        }
    }
    public void SkipThisStep(View v)
    {
        progressBar.setVisibility(View.VISIBLE);
        //localData=getContactList();
        //dataFinalNumber=localData.size();
        //for(int i=0;i<localData.size();i++)
        //{
        //    checkUserExist(localData.get(i));
        //}
        Intent i=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);

    }
    private ArrayList<ContactData> getContactList() {
        ArrayList<ContactData> data=new ArrayList<>();
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));
                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    if(pCur!=null&&pCur.moveToNext())
                    {
                        ArrayList<String> local=new ArrayList<>();
                        String local1=(pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER)))
                                .replaceAll("\\s+", "");
                        if(local1.charAt(0)=='0')
                            local1=replace(local1);
                        if (local1.charAt(0)!='+')
                            local1="+91"+local1;
                        local.add(local1);
                        data.add(new ContactData(name,local1));
                        while (pCur.moveToNext()) {
                            String phoneNo = (pCur.getString(pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER))).replaceAll("\\s+", "");
                            if(phoneNo.charAt(0)=='0')
                                phoneNo=replace(phoneNo);
                            if (phoneNo.charAt(0)!='+')
                                phoneNo="+91"+phoneNo;
                            int j=0;
                            for (int i=0;i<local.size();i++)
                            {
                                if(!(local.get(i).equals(phoneNo)))
                                    j++;
                            }
                            if(j==local.size())
                            {
                                local.add(phoneNo);
                                data.add(new ContactData(name,phoneNo));
                            }
                        }
                    }
                    pCur.close();
                }
            }
        }
        if(cur!=null){
            cur.close();
        }
        return  data;
    }

    private String replace(String phoneNo) {
        String result="";
        for(int i=1;i<=10;i++)
        {
            result=result+phoneNo.charAt(i);
        }
        return result;
    }
    private void checkUserExist(final ContactData data2)
    {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        reference.child(Config.NODE_ALL_CONTACT).orderByChild(Config.NODE_PHONE_NO)
                .equalTo(data2.getPhoneNo()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    Log.d(TAG, "onDataChange:"+dataSnapshot.getValue());
                    data.add(data2);
                 //   data2.setImageUrl(dataSnapshot.child(currentUser.getUid()).child(data2.getPhoneNo()).getValue().toString());
                    String s=data2.getName();
                    contactinfo.insertContact(data2.getName(),data2.getPhoneNo(),data2.getImageUrl());
                }
                dataNumber++;
                if(dataNumber==dataFinalNumber)
                {
                    Intent i=new Intent(getApplicationContext(),MainActivity.class);
                    setUpNewUser(imageProfileUrl);
                    startActivity(i);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Config.REQ_CODE_GET_PROFILE_IMAGE&&data!=null)
        {
            flagImageUpdate=true;
            profileImageShow.setImageBitmap(loadBitmap(data.getData().toString()));
        }
    }
    private void setUpNewUser(String imageProfileUrl)
    {
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference();
                ref.child(Config.NODE_ALL_CONTACT)
                .child(currentUser.getUid())
                .child(Config.NODE_PHONE_NO)
                .setValue(myNumber);
                ref.child(Config.NODE_ALL_CONTACT)
                .child(currentUser.getUid())
                .child(myNumber)
                .setValue(imageProfileUrl);
    }
    public Bitmap loadBitmap(String url)
    {
        Bitmap bm = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        try
        {
            URLConnection conn = new URL(url).openConnection();
            conn.connect();
            is = conn.getInputStream();
            bis = new BufferedInputStream(is, 8192);
            bm = BitmapFactory.decodeStream(bis);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if (bis != null)
            {
                try
                {
                    bis.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return bm;
    }
}
