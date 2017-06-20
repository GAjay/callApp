package com.livetechmonk.sharecontact.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.livetechmonk.sharecontact.R;
import com.livetechmonk.sharecontact.utils.Utils;

/**
 * @author Ajay Kumar
 */

public class Imageadapter extends BaseAdapter {
    private Context mContext;
    private final String[] web;
    private final int[] Imageid;

    public Imageadapter(Context c, String[] web, int[] Imageid) {
        mContext = c;
        this.Imageid = Imageid;
        this.web = web;
    }

    @Override
    public int getCount() {
        return Imageid.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_single, null);
            final ImageView imageView = (ImageView) grid.findViewById(R.id.grid_image);
            imageView.setImageResource(Imageid[position]);
            ImageView shareImage = (ImageView) grid.findViewById(R.id.ic_share);
            TextView textView = (TextView) grid.findViewById(R.id.txtimage);
            if(null != web[position] || web[position] != " "){
                textView.setText(web[position]);
            }
            else{

            }
            RelativeLayout relativeLayout = (RelativeLayout) grid.findViewById(R.id.layout_sharebox);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.ShareImage(Imageid[position], mContext, imageView);
                }
            });
            shareImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.ShareImage(Imageid[position], mContext, imageView);
                }
            });
        } else {
            grid = (View) convertView;
        }


        return grid;
    }
}
