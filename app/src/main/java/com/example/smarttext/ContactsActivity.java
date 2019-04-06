package com.example.smarttext;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.smarttext.utils.ContactData;
import com.example.smarttext.utils.Permission;

import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        Permission.getContactReqPermission(this);
        getContactData();
        if(checkSelfPermission(Permission.PERMISSION_CONTACT[0])!= PackageManager.PERMISSION_GRANTED
                &&checkSelfPermission(Permission.PERMISSION_CONTACT[1])!= PackageManager.PERMISSION_GRANTED
                &&checkSelfPermission(Permission.PERMISSION_CONTACT[2])!= PackageManager.PERMISSION_GRANTED
                &&checkSelfPermission(Permission.PERMISSION_CONTACT[3])!= PackageManager.PERMISSION_GRANTED)
        {
            Permission.getContactReqPermission(this);
        }
        else
        {
            getContactData();
        }
    }
    public ArrayList<ContactData> getContactData()
    {
        ContentResolver resolver=getContentResolver();
        ArrayList<ContactData> list=new ArrayList();
        Cursor cursor=resolver.query(ContactsContract
                .Contacts.CONTENT_URI
                ,null,null,null,null);
        while (cursor.moveToNext())
        {
            String id=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            Cursor phoneCursor=resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null
            ,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",new String[] {id}
            ,null);
            phoneCursor.moveToNext();
            String phoneNumber=phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            list.add(new ContactData(name,phoneNumber));
        }
        return list;
    }
    public static void checkPermissionForContact()
    {

    }
}
