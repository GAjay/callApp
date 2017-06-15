package com.livetechmonk.sharecontact;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;

import com.livetechmonk.sharecontact.models.response.ContactData;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by ajay on 15/6/17.
 */

public class Utils {

    public static void ShareImage(int imagepath, Context context) {

        Bitmap imgBitmap= BitmapFactory.decodeResource(context.getResources(),imagepath);
        String imgBitmapPath= MediaStore.Images.Media.insertImage(context.getContentResolver(),imgBitmap,"title",null);
        Uri imgBitmapUri=Uri.parse(imgBitmapPath);
        Intent shareIntent=new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM,imgBitmapUri);
        context.startActivity(Intent.createChooser(shareIntent,"share image using"));
    }

    @SuppressLint("HardwareIds")
    public static String deviceID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    /**
     * A method for get all contact of user
     * @param context
     * @param contactDatas
     */
    public static void getContacts(Context context, ArrayList<ContactData> contactDatas) {
        String TAG = "Utlis";
        String deviceId = deviceID(context);
        Log.d("deviceId", deviceId);
        ContentResolver cr = context.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        int i = 0;
        if (cur.getCount() > 0) {
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
                    while (pCur.moveToNext()) {
                        i++;
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        ContactData contactData = new ContactData(name, phoneNo);
                        //Log.d(TAG,contactData.toString());
                        contactDatas.add(contactData);
                    }

                    pCur.close();
                }
            }
        }
        Log.d(TAG, contactDatas.toString());
        Log.d(TAG, "tito" + i);
        cur.close();

    }


}
