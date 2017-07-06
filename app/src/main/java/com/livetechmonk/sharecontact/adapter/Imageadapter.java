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
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_single, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.grid_image);
            holder.shareImage = (ImageView) convertView.findViewById(R.id.ic_share);
            holder.textView = (TextView) convertView.findViewById(R.id.txtimage);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(null != web[position] || web[position] != " "){
            holder.textView.setText(web[position]);
        }
        holder.imageView.setImageResource(Imageid[position]);
        RelativeLayout relativeLayout = (RelativeLayout) convertView.findViewById(R.id.layout_sharebox);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.ShareImage(Imageid[position], mContext, holder.imageView);
            }
        });
        holder.shareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.ShareImage(Imageid[position], mContext, holder.imageView);
            }
        });

        return convertView;
    }

    private class ViewHolder {

        private ImageView imageView;
        private ImageView shareImage;
        private TextView textView;
    }
}