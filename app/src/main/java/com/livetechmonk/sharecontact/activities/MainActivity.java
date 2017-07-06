package com.livetechmonk.sharecontact.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;


import com.livetechmonk.sharecontact.R;
import com.livetechmonk.sharecontact.models.request.ContactDatarequest;
import com.livetechmonk.sharecontact.utils.ApiClient;
import com.livetechmonk.sharecontact.utils.CustomProgressDialog;
import com.livetechmonk.sharecontact.utils.Utils;
import com.livetechmonk.sharecontact.adapter.Imageadapter;
import com.livetechmonk.sharecontact.models.response.ContactData;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final int RequestPermissionCode = 1;
    ArrayList<ContactData> contactDatas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView grid;
        String[] web = {
                "Kurti",
                "T-Shirt",
                "T-Shirt",
                "T-Shirt",
                "T-Shirt",
                "Shirt",
                "Shirt",
                "Shirt",
                "Shirt",
                "Pant",
                "Pant",
                "Saree",
                "Shirt",
                "Jogger",
                "Men's Short",
                "Kurti",
                "Kurti",
                "Kurti",
                "Saree",
                "Kurti",

        };
        final int[] imageId = {
                R.drawable.one,
                R.drawable.second,
                R.drawable.third,
                R.drawable.fourth,
                R.drawable.fifth,
                R.drawable.six,
                R.drawable.seven,
                R.drawable.eight,
                R.drawable.nine,
                R.drawable.ten,
                R.drawable.eleven,
                R.drawable.twelve,
                R.drawable.thirteen,
                R.drawable.fourteen,
                R.drawable.fifteen,
                R.drawable.sixteen,
                R.drawable.seventeen,
                R.drawable.eighteen,
                R.drawable.nineteen,
                R.drawable.twenty,

        };

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            if (Utils.isNetworkAvailable(MainActivity.this)) {
                AsyncTaskRunner runner = new AsyncTaskRunner();
                runner.execute();
            } else {
                Log.d("TAG", "no net");
                final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                // Setting Dialog Title
                alertDialog.setTitle(getString(R.string.alert_heading));
                // Setting Dialog Message
                alertDialog.setMessage(getString(R.string.internet_try));
                // Setting OK Button
                alertDialog.setButton(getString(R.string.alert_ok_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                // Showing Alert Message
                alertDialog.show();
            }
        } else {
            EnableRuntimePermission();
        }
        final ProgressDialog progressDialog = CustomProgressDialog.ctor(MainActivity.this,
                ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.show();
        Imageadapter adapter = new Imageadapter(MainActivity.this, web, imageId);
        grid = (GridView) findViewById(R.id.grid);
        grid.setAdapter(adapter);
        progressDialog.dismiss();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewMessageActivity.class);
                startActivity(intent);
            }
        });
    }

    public void EnableRuntimePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                Manifest.permission.READ_CONTACTS) && ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(MainActivity.this, "Permission allows us to Access"
                    , Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE}, RequestPermissionCode);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {
        switch (RC) {
            case RequestPermissionCode:
                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {
                    Utils.getContacts(MainActivity.this, contactDatas);
                } else {
                    Toast.makeText(MainActivity.this, "Permission Canceled for application "
                            , Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int RequestCode, int ResultCode, Intent ResultIntent) {
        super.onActivityResult(RequestCode, ResultCode, ResultIntent);
        switch (RequestCode) {
            case (7):
                if (ResultCode == Activity.RESULT_OK) {
//                    Uri uri;
//                    Cursor cursor1, cursor2;
//                    String TempNameHolder, TempNumberHolder, TempContactID, IDresult = "";
//                    int IDresultHolder;
//                    uri = ResultIntent.getData();
//                    cursor1 = getContentResolver().query(uri, null, null, null, null);
//                    if (cursor1.moveToFirst()) {
//                        TempNameHolder = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//                        TempContactID = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts._ID));
//                        IDresult = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
//                        IDresultHolder = Integer.valueOf(IDresult);
//                        if (IDresultHolder == 1) {
//                            cursor2 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + TempContactID, null, null);
//                            while (cursor2.moveToNext()) {
//
//                                TempNumberHolder = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//
//                                name.setText(TempNameHolder);
//
//                                number.setText(TempNumberHolder);
//
//                            }
//                        }
//
//                    }
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {
        private String resp;
        @Override
        protected String doInBackground(String... params) {
            String deviceId = Utils.deviceID(MainActivity.this);
            contactDatas = Utils.getContacts(MainActivity.this, contactDatas);
            ContactDatarequest contactDatarequest = new ContactDatarequest(deviceId, contactDatas);
            ApiClient.ApiInterface apiService = ApiClient.getClient();

            Log.d("contact",contactDatarequest.toString());
            Call<ResponseBody> call = apiService.shareContact(contactDatarequest);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        String s = null;
                        Log.d("TAG", response.toString());

                    } else {
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    // Log error here since request failed

                }
            });
            return null;
        }
    }

}