package com.livetechmonk.sharecontact.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.livetechmonk.sharecontact.R;

/**
 * Created by ajay on 20/6/17.
 */

public class CustomProgressDialog extends ProgressDialog {

    private static String mText;
    private static int mColor;
    private CustomProgressDialog(Context context) {
        super(context);
    }
    public static ProgressDialog ctor(Context context, int color) {
        mText ="";
        mColor= R.color.colorPrimary;
        com.livetechmonk.sharecontact.utils.CustomProgressDialog dialog = new com.livetechmonk.
                sharecontact.utils.CustomProgressDialog(context);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        return dialog;
    }

    public static ProgressDialog ctor(Context context,String data, int color) {
        mText = data;
        mColor=color;
        com.livetechmonk.sharecontact.utils.CustomProgressDialog dialog = new com.livetechmonk.
                sharecontact.utils.CustomProgressDialog(context);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        return dialog;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_bar_layout);
        ProgressBar progress1 = (ProgressBar) findViewById(R.id.progess_bar);
        if (null!=progress1) {
            progress1.getIndeterminateDrawable().setColorFilter(mColor, android.graphics.PorterDuff.Mode.MULTIPLY);
        }
        TextView tv = (TextView)findViewById(R.id.progress_text);
        if(TextUtils.isEmpty(mText)){
            tv.setVisibility(View.GONE);
        }else{
            tv.setVisibility(View.VISIBLE);
            tv.setText(mText);
        }


    }
}
