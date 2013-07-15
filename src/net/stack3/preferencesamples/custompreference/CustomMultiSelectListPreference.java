package net.stack3.preferencesamples.custompreference;

import android.content.Context;
import android.preference.MultiSelectListPreference;
import android.util.AttributeSet;

public class CustomMultiSelectListPreference extends MultiSelectListPreference {
    public CustomMultiSelectListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public CustomMultiSelectListPreference(Context context) {
        super(context);
    }
}
