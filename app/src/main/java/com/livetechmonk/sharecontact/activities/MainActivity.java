package com.livetechmonk.sharecontact.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;


import com.livetechmonk.sharecontact.R;
import com.livetechmonk.sharecontact.Utils;
import com.livetechmonk.sharecontact.adapter.Imageadapter;
import com.livetechmonk.sharecontact.models.response.ContactData;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int RequestPermissionCode = 1;
    ArrayList<ContactData> contactDatas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView grid;
        String[] web = {
                "Google",
                "Github",
                "Instagram",
        };
        final int[] imageId = {
                R.mipmap.ic_launcher,
                R.mipmap.ic_launcher_round,
                R.mipmap.ic_launcher_round

        };
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED ) {
            Utils.getContacts(MainActivity.this, contactDatas);
        } else {
            EnableRuntimePermission();
        }
        Imageadapter adapter = new Imageadapter(MainActivity.this, web, imageId);
        grid = (GridView) findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Utils.ShareImage(imageId[position], MainActivity.this);

            }
        });

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
                    Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_EXTERNAL_STORAGE}, RequestPermissionCode);

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
}