package com.example.mobee;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class RobotoMedium extends
        androidx.appcompat.widget.AppCompatTextView {

    public RobotoMedium(Context context)
    {
        super(context);
        initTypeface(context);
    }

    public RobotoMedium(Context context,
                        AttributeSet attrs)
    {
        super(context, attrs);
        initTypeface(context);
    }

    public RobotoMedium(Context context,
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
                "Roboto-Medium.ttf");
        this.setTypeface(tf);
    }
}