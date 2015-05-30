package studios.slick.acminternal.customviews;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;

import studios.slick.acminternal.R;

/**
 * Created by Darshan on 30/05/15.
 */
public class MyButton extends Button {

    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setCustomFont(context, attrs);
    }


    private void setCustomFont(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyButton);
        String fontName = a.getString(R.styleable.MyButton_bFont);
        if(!isInEditMode()){
            setCustomFont(context,fontName);
        }
        a.recycle();


    }

    private boolean setCustomFont(Context context, String fontName) {
        Typeface tf;
        try{
            tf = Typeface.createFromAsset(context.getAssets(),fontName);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        setTypeface(tf);
        return true;
    }
}
