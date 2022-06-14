package com.example.mobee;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class RobotoRegular extends
        androidx.appcompat.widget.AppCompatTextView {

    public RobotoRegular(Context context)
    {
        super(context);
        initTypeface(context);
    }

    public RobotoRegular(Context context,
                         AttributeSet attrs)
    {
        super(context, attrs);
        initTypeface(context);
    }

    public RobotoRegular(Context context,
                         AttributeSet attrs,
                         int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initTypeface(context);
    }

    private void initTypeface(Context context)
    {
        Typeface tf = Typeface.createFromAsset(
                context.getAssets(),
                "Roboto-Regular.ttf");
        this.setTypeface(tf);
    }
}