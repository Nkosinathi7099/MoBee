package com.example.mobee;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class RobotoBold extends
        androidx.appcompat.widget.AppCompatTextView {

    public RobotoBold(Context context)
    {
        super(context);
        initTypeface(context);
    }

    public RobotoBold(Context context,
                      AttributeSet attrs)
    {
        super(context, attrs);
        initTypeface(context);
    }

    public RobotoBold(Context context,
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
                "Roboto-Bold.ttf");
        this.setTypeface(tf);
    }
}