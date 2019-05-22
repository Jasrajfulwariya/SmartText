package com.example.smarttext;
import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

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
    private ArrayList<ContactData> contactData;
    private ArrayList<ContactData> previousData;
    private EditText searchText;
    ImageButton imgsearch;
    String searchedContact;
    private ContactListRecyclerAdapter contactAdapter;
    String mName;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        searchText=findViewById(R.id.contact_search);
        imgsearch=findViewById(R.id.contactSearchIB);

            init();
                contactData=getContactList();
                previousData=contactData;
                contactAdapter=new ContactListRecyclerAdapter(this,contactData);
                contactRecycler.setAdapter(contactAdapter);
                contactRecycler.setLayoutManager(new LinearLayoutManager(this));
                contactData=new ArrayList<>();
    }

    private boolean cheakEqual(String mName) {
        char a=searchedContact.charAt(0);
        for(int i=0;i<mName.length();i++)
        {
            if(mName.charAt(i)==a)
            {
                int count=0,j;
                for(j=0;j<searchedContact.length();j++) {
                    if (mName.charAt(i+j)!= searchedContact.charAt(j))
                        break;
                }
                if(j==searchedContact.length())
                    return true;
            }
        }
        return false;
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

    public void searchContact(View view) {
        searchedContact=searchText.getText().toString();
        for (ContactData data:previousData) {
            mName = data.getName();
            if (cheakEqual(mName))
                contactData.add(data);
        }
        contactAdapter=new ContactListRecyclerAdapter(getApplicationContext(),contactData);
        contactRecycler.setAdapter(contactAdapter);
        contactRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}
