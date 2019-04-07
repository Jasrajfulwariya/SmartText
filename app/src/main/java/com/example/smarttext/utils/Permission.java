package com.example.smarttext.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;

import com.example.smarttext.ContactsActivity;

public class Permission {
    public static String[] PERMISSION_CONTACT= new String[]{Manifest.permission.READ_CONTACTS
            ,Manifest.permission.READ_EXTERNAL_STORAGE
            ,Manifest.permission.WRITE_EXTERNAL_STORAGE
            ,Manifest.permission.INTERNET};
    public static void getContactReqPermission(Activity mContext)
    {
        ActivityCompat
                .requestPermissions(mContext,PERMISSION_CONTACT
                        ,Config.REQ_CODE_READ_CONTACT);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean checkPermissionForContact(Activity mContext)
    {

        return false;
    }
}
