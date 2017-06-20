package com.livetechmonk.sharecontact.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.livetechmonk.sharecontact.R;
import com.livetechmonk.sharecontact.models.request.SupportRequest;
import com.livetechmonk.sharecontact.utils.ApiClient;
import com.livetechmonk.sharecontact.utils.CustomProgressDialog;
import com.livetechmonk.sharecontact.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ajay on 15/6/17.
 */

public class NewMessageActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        Button button = (Button) findViewById(R.id.btn_submit_form);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyBoard(v, NewMessageActivity.this);
                Log.d("button", "clicked");
                //System.out.println(validateData());
                if (validateData()) {
                    if (Utils.isNetworkAvailable(NewMessageActivity.this)) {
                        sendSupportData();
                    } else {
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(NewMessageActivity.this);

                        alertDialog.setTitle(getString(R.string.alert_heading));
                        // Setting Dialog Message
                        alertDialog.setMessage(getString(R.string.internet_try));
                        // Setting Icon to Dialog
                        // Setting Positive "Yes" Button
                        alertDialog.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                sendSupportData();
                                // Write your code here to invoke YES event
                                dialog.dismiss();
                            }
                        });
                        // Setting Negative "NO" Button
                        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to invoke NO event
                                dialog.dismiss();
                            }
                        });
                        // Showing Alert Message
                        alertDialog.show();
                    }
                }
            }
        });
    }

    public boolean validateData() {
        EditText editTextViewName = (EditText) findViewById(R.id.etxt_name);
        EditText editTextViewEmail = (EditText) findViewById(R.id.etxt_email);
        EditText editTextViewPhone = (EditText) findViewById(R.id.etxt_phone);
        if (!(TextUtils.isEmpty(editTextViewName.getText().toString()) && TextUtils.isEmpty(editTextViewEmail.getText().toString())
                && TextUtils.isEmpty(editTextViewPhone.getText().toString()))) {

            if (!Utils.isValidMail(editTextViewEmail.getText().toString())) {
                final AlertDialog alertDialog = new AlertDialog.Builder(NewMessageActivity.this).create();
                // Setting Dialog Title
                alertDialog.setTitle(getString(R.string.alert_heading));
                // Setting Dialog Message
                alertDialog.setMessage(getString(R.string.valid_email));
                // Setting OK Button
                alertDialog.setButton(getString(R.string.alert_ok_button),
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                            }
                        });
                // Showing Alert Message
                alertDialog.show();
                return false;
            }
            if (!Utils.isValidMobile(editTextViewPhone.getText().toString())) {
                final AlertDialog alertDialog = new AlertDialog.Builder(NewMessageActivity.this).create();
                // Setting Dialog Title
                alertDialog.setTitle(getString(R.string.alert_heading));
                // Setting Dialog Message
                alertDialog.setMessage(getString(R.string.valid_phone));
                // Setting OK Button
                alertDialog.setButton(getString(R.string.alert_ok_button),
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                            }
                        });
                // Showing Alert Message
                alertDialog.show();
                return false;
            }
        } else {
            final AlertDialog alertDialog = new AlertDialog.Builder(NewMessageActivity.this).create();
            // Setting Dialog Title
            alertDialog.setTitle(getString(R.string.alert_heading));
            // Setting Dialog Message
            alertDialog.setMessage(getString(R.string.required_all));
            // Setting OK Button
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.dismiss();
                }
            });
            // Showing Alert Message
            alertDialog.show();
            return false;
        }
        return true;
    }

    public void sendSupportData() {
        final ProgressDialog progressDialog = CustomProgressDialog.ctor(NewMessageActivity.this,
                ContextCompat.getColor(NewMessageActivity.this, R.color.colorPrimary));
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.show();

        final EditText editTextViewName = (EditText) findViewById(R.id.etxt_name);
        final EditText editTextViewEmail = (EditText) findViewById(R.id.etxt_email);
        final EditText editTextViewPhone = (EditText) findViewById(R.id.etxt_phone);
        final EditText editTextViewMessage = (EditText) findViewById(R.id.etxt_message);
        SupportRequest strRequestBody = new SupportRequest(editTextViewName.getText().toString(),
                editTextViewPhone.getText().toString(), editTextViewEmail.getText().toString(),
                editTextViewMessage.getText().toString());
        System.out.println(strRequestBody.toString());
        ApiClient.ApiInterface apiService = ApiClient.getClient();

        System.out.println(strRequestBody);
        Call<ResponseBody> call = apiService.support(strRequestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (null != progressDialog) {
                    progressDialog.dismiss();
                }
                if (response.isSuccessful()) {
                    String s = null;
                    final AlertDialog alertDialog = new AlertDialog.Builder(NewMessageActivity.this).create();
                    // Setting Dialog Title
                    alertDialog.setTitle(getString(R.string.alert_thank));
                    // Setting Dialog Message
                    alertDialog.setMessage(getString(R.string.thankyou));
                    // Setting OK Button
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    // Showing Alert Message
                    alertDialog.show();
                    editTextViewEmail.setText("");
                    editTextViewMessage.setText("");
                    editTextViewName.setText("");
                    editTextViewPhone.setText("");
                    Log.d("TAG", response.toString());

                } else {
                    handleWebserviceFailureResponse(null);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Log error here since request failed
                if (null != progressDialog) {
                    progressDialog.dismiss();
                }
                handleWebserviceFailureResponse(t);
            }
        });
    }

    private void handleWebserviceFailureResponse(Throwable t) {

        // checking if error is instance of IO class, then its internet error. other wise
        // some error from server side.
        if (null != t && t instanceof IOException) {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(NewMessageActivity.this);

            alertDialog.setTitle(getString(R.string.alert_heading));
            // Setting Dialog Message
            alertDialog.setMessage(getString(R.string.internet_try));
            // Setting Icon to Dialog
            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton(getString(R.string.alert_retry_button), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    sendSupportData();
                    // Write your code here to invoke YES event
                    dialog.dismiss();
                }
            });
            // Setting Negative "NO" Button
            alertDialog.setNegativeButton(getString(R.string.alert_cancel_button), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to invoke NO event
                    dialog.dismiss();
                }
            });
            // Showing Alert Message
            alertDialog.show();

        } else {
            final AlertDialog alertDialog = new AlertDialog.Builder(NewMessageActivity.this).create();
            // Setting Dialog Title
            alertDialog.setTitle(getString(R.string.alert_heading));
            // Setting Dialog Message
            alertDialog.setMessage(getString(R.string.something_wrong));
            // Setting OK Button
            alertDialog.setButton(getString(R.string.alert_ok_button), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.dismiss();
                }
            });
            // Showing Alert Message
            alertDialog.show();
        }
    }
}
