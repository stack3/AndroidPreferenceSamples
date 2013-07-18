package net.stack3.preferencesamples.custompreference;

import net.stack3.preferencesamples.R;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CustomEditTextPreference extends EditTextPreference {
    private int minLength = Integer.MAX_VALUE;
    
    public CustomEditTextPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setup(context, attrs);
    }
    
    public CustomEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context, attrs);
    }
    
    public CustomEditTextPreference(Context context) {
        super(context);
    }
    
    private void setup(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.CustomEditTextPreference, 0, 0);
        minLength = a.getInt(R.styleable.CustomEditTextPreference_minLength, Integer.MAX_VALUE);
        a.recycle();
    }

    private void updateOKButton(int textLength) {
        Dialog dialog = getDialog();
        if (dialog != null) {
            //
            // Disable OK button if the edit text was lesser than minLength. 
            //
            Button okButton = (Button)dialog.findViewById(android.R.id.button1);
            okButton.setEnabled(textLength >= minLength);
        }
    }
    
    @Override
    protected void onAddEditTextToDialogView(View dialogView, EditText editText) {
        super.onAddEditTextToDialogView(dialogView, editText);
        editText.addTextChangedListener(textChangeListener);
    }
    
    @Override
    protected void showDialog(Bundle state) {
        super.showDialog(state);
        String text = getText();
        updateOKButton(text != null ? text.length() : 0);
    }
    
    private TextWatcher textChangeListener = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            updateOKButton(s.length());
        }
        
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                int after) {
        }
        
        @Override
        public void afterTextChanged(Editable s) {
        }
    };
}
