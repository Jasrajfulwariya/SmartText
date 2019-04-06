package com.example.smarttext;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.smarttext.Adapters.ContactListRecyclerAdapter;
import com.example.smarttext.utils.ContactData;
import com.example.smarttext.utils.FireBaseDatabaseManager;
import com.example.smarttext.utils.Permission;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
public class ContactsActivity extends AppCompatActivity {
    private FireBaseDatabaseManager fireDataManager;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        Permission.getContactReqPermission(this);
        if (!Permission.checkPermissionForContact(this))
        {
            init();
            ArrayList<ContactData> contactData=getContactData();
            contactData= filterContact(contactData);
            RecyclerView contactRecycler=findViewById(R.id.activityContactRecyclerView);
            contactRecycler.setAdapter(new ContactListRecyclerAdapter(getApplicationContext(),contactData));
            contactRecycler.setHasFixedSize(true);
            contactRecycler.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    private void init() {
        fireDataManager=new FireBaseDatabaseManager();
    }

    private ArrayList<ContactData> filterContact(ArrayList<ContactData> contactData) {
            for(int i=0;i<contactData.size();i++)
            {
                if(fireDataManager.checkContactPresent(contactData.get(i).getPhoneNo()))
                {
                    contactData.remove(i);
                }
            }
            return contactData;
    }

    public ArrayList<ContactData> getContactData()
    {
        ContentResolver resolver=getContentResolver();
        ArrayList<ContactData> list=new ArrayList<>();
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
            if(phoneNumber.charAt(0)!='+')
            phoneNumber="+91"+phoneNumber;
            list.add(new ContactData(name,phoneNumber));
        }
        return list;
    }

}
