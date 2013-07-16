package net.stack3.preferencesamples.custompreference;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.stack3.preferencesamples.R;

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class CustomMultiSelectListPreference extends DialogPreference {
    private static final String ATTR_NAMESPACE = "http://schemas.android.com/apk/res/net.stack3.preferencesamples";
    
    private CharSequence[] entries;
    private CharSequence[] entryValues;    
    private int minChecked;
    private int maxChecked;
    
    public CustomMultiSelectListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.CustomMultiSelectListPreference, 0, 0);
        entries = a.getTextArray(R.styleable.CustomMultiSelectListPreference_android_entries);
        //entryValues = a.getTextArray(R.styleable.CustomMultiSelectListPreference_anrdoid_entryValues);
        a.recycle();
        
        minChecked = attrs.getAttributeIntValue(
                ATTR_NAMESPACE, 
                "minChecked", 
                0);
        maxChecked = attrs.getAttributeIntValue(
                ATTR_NAMESPACE, 
                "maxChecked", 
                Integer.MAX_VALUE);
    }

    public void setEntries(CharSequence[] entries) {
        this.entries = entries;
    }
    
    public void setEntries(int entriesResId) {
        setEntries(getContext().getResources().getTextArray(entriesResId));
    }
    
    public void setEntryValues(CharSequence[] entryValues) {
        this.entryValues = entryValues;
    }    
    
    public void setEntryValues(int entryValuesResId) {
        setEntryValues(getContext().getResources().getTextArray(entryValuesResId));
    }

    private void updateOKButton() {
        Dialog dialog = getDialog();
        ListView listView = (ListView)dialog.findViewById(android.R.id.list);
        
        //listView = (ListView)dialog.findViewById(android.R.id.input);
        if (dialog != null && listView != null) {
            //
            // Disable OK button if the edit text was lesser than minLength. 
            //
            int numChecked = listView.getCheckedItemCount();
            Button okButton = (Button)dialog.findViewById(android.R.id.button1);
            okButton.setEnabled(minChecked <= numChecked && numChecked <= maxChecked);
        }
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        List<String> items = new ArrayList<String>();
        for (int i = 0; i < entries.length; i++) {
            items.add(entries[i].toString());
        }
        
        ListView listView = (ListView)view.findViewById(android.R.id.list);
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(), 
                android.R.layout.simple_list_item_multiple_choice, 
                items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onItemClickListener);
    }
    
    @Override
    protected void showDialog(Bundle state) {
        super.showDialog(state);
        
        updateOKButton();
    }
    
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
            updateOKButton();
        }
    };
}
