package net.stack3.preferencesamples.custompreference;

import net.stack3.preferencesamples.R;
import android.content.Context;
import android.preference.EditTextPreference;
import android.util.AttributeSet;
import android.widget.Toast;

public class CustomEditTextPreference extends EditTextPreference {
    private int minLength = Integer.MAX_VALUE;
    
    public CustomEditTextPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupAttributes(attrs);
    }
    
    public CustomEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupAttributes(attrs);
    }
    
    public CustomEditTextPreference(Context context) {
        super(context);
    }
    
    private void setupAttributes(AttributeSet attrs) {
        minLength = attrs.getAttributeIntValue(
                "http://schemas.android.com/apk/res/net.stack3.preferencesamples", 
                "minLength", 
                Integer.MAX_VALUE);
    }
    
    @Override
    protected boolean callChangeListener(Object newValue) {
        String newString = (String)newValue;
        if (newString.length() < minLength) {
            String messageFormat = getContext().getString(R.string.too_short_string_length_format);
            String message = String.format(messageFormat, minLength);
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            return false;
        } 
        
        return true;
   }
}
