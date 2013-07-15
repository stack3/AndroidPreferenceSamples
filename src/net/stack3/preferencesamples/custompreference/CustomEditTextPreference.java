package net.stack3.preferencesamples.custompreference;

import android.content.Context;
import android.preference.EditTextPreference;
import android.util.AttributeSet;

public class CustomEditTextPreference extends EditTextPreference {
    public CustomEditTextPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    
    public CustomEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public CustomEditTextPreference(Context context) {
        super(context);
    }
    
    @Override
    protected boolean callChangeListener(Object newValue) {
        String newString = (String)newValue;
        if (newString.length() < 3) {
            return false;
        } 
        
        return true;
   }
}
