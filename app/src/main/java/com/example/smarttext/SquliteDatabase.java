package com.example.smarttext;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import static java.sql.Types.NULL;

public class SquliteDatabase extends SQLiteOpenHelper {

    private static final String Database_Name="Appdata.db";
    private static final String Table_Name="User_Info";
    String newtableName;
    SQLiteDatabase mdatabase;

    public SquliteDatabase(Context context) {
        super(context,Database_Name,null, 1);
        SQLiteDatabase db=this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+Table_Name+" (Main_user TEXT,Contacts TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("create table "+newtableName+"(Contact_name varchar(30),sender_Message varchar(30),sender_Time varchar(30),user_Message varchar(30),user_Time varchar(30))");
    }
    public boolean addcontact(String Main_user ,String new_Contact)
    {
        newtableName=new_Contact;
        SQLiteDatabase db1;
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        String query="Select Contacts from "+Table_Name+" where Contacts='"+new_Contact+"'";
        Cursor cursor=db.rawQuery(query,null);
        long result=0;
        if(cursor.getCount()<=0)
        {
            contentValues.put("Main_user",Main_user);
            contentValues.put("Contacts",new_Contact );
            result=db.insert(Table_Name,null,contentValues);
        }
        if(result==-1)
            return false;
        else
            return true;

    }
    public boolean insertChat(String sender_Name,String sender_Message,String sender_Time,String user_Message,String user_Time)
    {

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("msName",sender_Name);
        contentValues.put("msMessage",sender_Message);
        contentValues.put("msTime",sender_Time);
        contentValues.put("muMessage",user_Message);
        contentValues.put("muTime",user_Time);
        long result=db.insert(newtableName,null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }
    public String fetch_data(String sender_Name)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String query="Select * from "+sender_Name+"";
        Cursor cursor=db.rawQuery(query,null);
        return "karan";

    }


}
