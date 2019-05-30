package com.example.smarttext.utils;

import android.Manifest;
import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;

public class Permission {
    public static String[] ALL_PERMISSION_ARRAY = new String[]{Manifest.permission.READ_CONTACTS
            ,Manifest.permission.READ_EXTERNAL_STORAGE
            ,Manifest.permission.WRITE_EXTERNAL_STORAGE
            ,Manifest.permission.INTERNET
            ,Manifest.permission.VIBRATE};

}
