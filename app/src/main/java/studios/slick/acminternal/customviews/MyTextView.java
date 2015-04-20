package studios.slick.acminternal.customviews;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import studios.slick.acminternal.R;

/**
 * Created by Darshan on 07-01-2015.
 */
public class MyTextView extends TextView{
    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomFont(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context context, AttributeSet attrs) {
        TypedArray a=context.obtainStyledAttributes(attrs, R.styleable.MyTextView);
        String fontName=a.getString(R.styleable.MyTextView_tvFont);
        if(!isInEditMode()){
            setCustomFont(context,fontName);
        }
        a.recycle();
    }

    private boolean setCustomFont(Context context, String fontName) {
        Typeface tf=null;
        try{
            tf=Typeface.createFromAsset(context.getAssets(),fontName);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        setTypeface(tf);
        return true;
    }
}
