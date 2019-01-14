package com.vn.kienphung.music_52.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

public class PermissionCheck {
    public static final int REQUEST_CODE_ONE = 1;
    public static final int REQUEST_CODE_TWO = 2;
    public static final int REQUEST_CODE_THREE = 3;
    public static final int REQUEST_CODE_FOUR = 4;
    public static final int REQUEST_CODE_FIVE = 5;

    public static boolean readAndWriteExternalStorage(Context context) {
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity)
                    context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_ONE);
            return false;
        } else {
            return true;
        }

    }

    public static boolean audioRecord(Context context) {
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity)
                    context, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_CODE_TWO);
            return false;
        } else {
            return true;
        }

    }

    public static boolean readAndWriteContacts(Context context) {
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity)
                    context, new String[]{Manifest.permission.READ_CONTACTS,
                    Manifest.permission.WRITE_CONTACTS}, REQUEST_CODE_THREE);
            return false;
        } else {
            return true;
        }

    }

    public static boolean vibrate(Context context) {
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity)
                    context, new String[]{Manifest.permission.VIBRATE}, REQUEST_CODE_FOUR);
            return false;
        } else {
            return true;
        }

    }

    public static boolean sendSms(Context context) {
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity)
                    context, new String[]{Manifest.permission.SEND_SMS}, REQUEST_CODE_FIVE);
            return false;
        } else {
            return true;
        }

    }
}
