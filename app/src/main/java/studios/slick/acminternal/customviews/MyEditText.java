package studios.slick.acminternal.customviews;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.EditText;

import studios.slick.acminternal.R;


/**
 * Created by Darshan on 07-01-2015.
 */
public class MyEditText extends EditText {
    public MyEditText(Context context) {
        super(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomFont(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setCustomFont(context, attrs);
    }
    private void setCustomFont(Context context, AttributeSet attrs) {
        TypedArray a=context.obtainStyledAttributes(attrs, R.styleable.MyEditText);
        String fontName=a.getString(R.styleable.MyEditText_etFont);
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
