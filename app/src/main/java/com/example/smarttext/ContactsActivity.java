package com.example.smarttext;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.smarttext.Adapters.ContactListRecyclerAdapter;
import com.example.smarttext.utils.Config;
import com.example.smarttext.utils.ContactData;
import com.example.smarttext.utils.Permission;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
public class ContactsActivity extends AppCompatActivity {
    private DatabaseReference fireBaseRef;
    private RecyclerView contactRecycler;
    ArrayList<ContactData> data;
    private ArrayList<ContactData> contactData;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        Permission.getContactReqPermission(this);
        if (!Permission.checkPermissionForContact(this))
        {
            init();
            //TODO: getting Contact Data
            contactData=getContactList();
            int a=contactData.size();
            //FilterData
            final int i=0;
            data=new ArrayList<>();
            for(ContactData conData:contactData)
            {
                final  ContactData data1=conData;
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
                reference.child(Config.NODE_ALL_CONTACT).orderByChild(Config.NODE_PHONE_NO)
                        .equalTo(data1.getPhoneNo()).addListenerForSingleValueEvent(new ValueEventListener() {
                    int i=0;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.d("Data", i+" :"+data1.getPhoneNo());
                        i++;
                        if(dataSnapshot.exists())
                        {
                            //TODO: starting Chat Activity
                            data.add(data1);
                            Log.d("Posion Of data", "onDataChange: exists");
                            String dd=data.get(0).getPhoneNo();
                            dd="5";
                        }
                        else
                        {
                            //TODO: Starting Invite Activity
                            Log.d("Posion Of data ", "onDataChange: not exist");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            };
            ContactListRecyclerAdapter contactAdapter=new ContactListRecyclerAdapter(this,contactData);
            contactRecycler.setAdapter(contactAdapter);
            contactRecycler.setLayoutManager(new LinearLayoutManager(this));
        }


    }

    private void init() {
        fireBaseRef=FirebaseDatabase.getInstance().getReference();
        contactRecycler=findViewById(R.id.activityContactRecyclerView);
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
                        String localData=(pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER))).replaceAll("\\s+", "");
                        if(localData.charAt(0)=='0')
                            localData=replace(localData);
                        if (localData.charAt(0)!='+')
                            localData="+91"+localData;
                        data.add(new ContactData(name,localData));
                        while (pCur.moveToNext()) {
                            String phoneNo = (pCur.getString(pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER))).replaceAll("\\s+", "");
                            if(phoneNo.charAt(0)=='0')
                                phoneNo=replace(phoneNo);
                            if (phoneNo.charAt(0)!='+')
                                phoneNo="+91"+phoneNo;
                            if(!(localData.equals(phoneNo)))
                                data.add(new ContactData(name,phoneNo));
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

}
