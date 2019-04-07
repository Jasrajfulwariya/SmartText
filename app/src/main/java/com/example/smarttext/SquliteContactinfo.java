package com.example.smarttext;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SquliteContactinfo extends SQLiteOpenHelper {
    private static final String contact_Databasename="contact_Database.db";
    private static final String mcontact_table="contact_table";
    public SquliteContactinfo(Context context) {
        super(context, contact_Databasename,null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+mcontact_table+" (user_Name TEXT,user_Contact TEXT,user_Image_url TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean insert_contact(String name,String contact,String image_url)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        long result=0;
        String query="Select user_Contact from "+mcontact_table+" where user_Contact='"+contact+"'";
        Cursor cursor=db.rawQuery(query,null);
        if(cursor.getCount()<=0)
        {
            contentValues.put("user_Name",name);
            contentValues.put("user_Contact",contact );
            contentValues.put("user_Image_url",image_url );
            result=db.insert(mcontact_table,null,contentValues);
        }
        if(result==-1)
            return false;
        else
            return true;

    }
    public Cursor fetch_data(String sender_Name)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String query="Select * from "+mcontact_table+"";
        Cursor cursor=db.rawQuery(query,null);
        return cursor;

    }

}
