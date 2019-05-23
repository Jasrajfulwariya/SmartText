package com.example.smarttext;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import com.example.smarttext.utils.Config;
import com.example.smarttext.utils.ContactData;

import java.util.ArrayList;

public class SquliteContactinfo extends SQLiteOpenHelper {
    private static final String contact_Databasename="contactDatabase.db";
    private ArrayList<ContactData> data=new ArrayList<>();
    private static final String TABLE_NAME="contact_table";
    public SquliteContactinfo(Context context) {
        super(context, contact_Databasename,null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" (UserName TEXT,UserContact TEXT,UserImageUrl TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    } 
    public boolean insertContact(String name,String contact,String image_url)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        long result=0;
        String query="Select UserContact from "+TABLE_NAME+" where UserContact ='"+contact+"'";
        Cursor cursor=db.rawQuery(query,null);
        if(cursor.getCount()<=0)
        {
            contentValues.put(Config.SQLITE_USERNAME_CI,name);
            contentValues.put(Config.SQLITE_USER_CONTACT_CI,contact );
            contentValues.put(Config.SQLITE_USER_IMAGE_URL_CI,image_url );
            result=db.insert(TABLE_NAME,null,contentValues);
        }
        return result != -1;

    }
    public ArrayList<ContactData> fetchData()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String query="Select * from "+TABLE_NAME;
        Cursor cursor=db.rawQuery(query,null);
        if(cursor!=null&&cursor.moveToNext())
        {
            data.add(new ContactData(cursor.getString(0),cursor.getString(1),cursor.getString(2)));
        }
        if(data.size()==0)
            return null;
        else
            return data;
    }

}
