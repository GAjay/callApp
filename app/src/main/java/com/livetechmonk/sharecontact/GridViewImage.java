package com.livetechmonk.sharecontact;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by ajay on 15/6/17.
 */

public class GridViewImage extends android.support.v7.widget.AppCompatImageView {

    public GridViewImage(Context context) {
        super(context);
    }

    public GridViewImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridViewImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec); // This is the key that will make the height equivalent to its width
    }
}
