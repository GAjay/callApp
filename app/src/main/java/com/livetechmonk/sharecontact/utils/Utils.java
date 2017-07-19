package com.livetechmonk.sharecontact.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.livetechmonk.sharecontact.models.response.ContactData;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by ajay on 15/6/17.
 */

public class Utils {

    public static boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidMobile(String phone) {
        System.out.println(phone.length());
        if (phone.length() < 10 || phone.length() > 12) {
            return false;

        } else {
            return android.util.Patterns.PHONE.matcher(phone).matches();
        }
    }

    public static boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() !=
                null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static void hideKeyBoard(View view, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void ShareImage(int imagepath, Context context, ImageView imageView) {

//        Bitmap imgBitmap= BitmapFactory.decodeResource(context.getResources(),imagepath);
//        String imgBitmapPath= MediaStore.Images.Media.insertImage(context.getContentResolver(),imgBitmap,new Date(),null);
//        Uri imgBitmapUri=Uri.parse(imgBitmapPath);
//        Intent shareIntent=new Intent(Intent.ACTION_SEND);
//        shareIntent.setType("image/*");
//        shareIntent.putExtra(Intent.EXTRA_STREAM,imgBitmapUri);
//        context.startActivity(Intent.createChooser(shareIntent,"share image using"));

//        Bitmap mBitmap = null;
//        Bitmap icon = mBitmap;
//        Intent share = new Intent(Intent.ACTION_SEND);
//        share.setType("image/*");
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
//        try {
//            f.createNewFile();
//            FileOutputStream fo = new FileOutputStream(f);
//            fo.write(bytes.toByteArray());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/temporary_file.jpg"));
//        context.startActivity(Intent.createChooser(share, "Share Image"));

        // Get access to bitmap image from view

        // Get access to the URI for the bitmap
        Uri bmpUri = getLocalBitmapUri(imageView);
        if (bmpUri != null) {
            // Construct a ShareIntent with link to image
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
            shareIntent.setType("image/*");
            // Launch sharing dialog for image
            context.startActivity(Intent.createChooser(shareIntent, "Share Image"));
        } else {
            // ...sharing failed, handle error

        }
    }

    // Returns the URI path to the Bitmap displayed in specified ImageView
    public static Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable) {
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    @SuppressLint("HardwareIds")
    public static String deviceID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    /**
     * A method for get all contact of user
     *
     * @param context
     * @param contactDatas
     */
    public static ArrayList<ContactData>  getContacts(Context context, ArrayList<ContactData> contactDatas) {
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
       // Log.d(TAG, contactDatas.toString());
       // Log.d(TAG, "tito" + i);
        cur.close();
        return contactDatas;

    }


}
