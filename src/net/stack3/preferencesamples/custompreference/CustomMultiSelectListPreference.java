package net.stack3.preferencesamples.custompreference;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.stack3.preferencesamples.R;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class CustomMultiSelectListPreference extends DialogPreference {
    private static final String ATTR_NAMESPACE = "http://schemas.android.com/apk/res/net.stack3.preferencesamples";
    
    private ListView listView;
    private List<String> entries;
    private List<String> entryValues;
    private Set<String> values;
    private int minChecked;
    private int maxChecked;
    
    public CustomMultiSelectListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.CustomMultiSelectListPreference, 0, 0);
        CharSequence[] rawEntries = a.getTextArray(R.styleable.CustomMultiSelectListPreference_android_entries);
        CharSequence[] rawEntryValues = a.getTextArray(R.styleable.CustomMultiSelectListPreference_android_entryValues);
        a.recycle();
        
        if (rawEntries.length != rawEntryValues.length) {
            throw new Error("entries and entryValues do not match item count.");
        }
        
        entries = new ArrayList<String>();
        entryValues = new ArrayList<String>();
        for (int i = 0; i < rawEntries.length; i++) {
            entries.add(rawEntries[i].toString());
            entryValues.add(rawEntryValues[i].toString());
        }
        values = new HashSet<String>();
        
        minChecked = attrs.getAttributeIntValue(
                ATTR_NAMESPACE, 
                "minChecked", 
                0);
        maxChecked = attrs.getAttributeIntValue(
                ATTR_NAMESPACE, 
                "maxChecked", 
                Integer.MAX_VALUE);
    }

    public List<String> getEntries() {
        return entries;
    }
    
    public List<String> getEntryValues() {
        return entryValues;
    }
    
    public Set<String> getValues() {
        return values;
    }
    
    public void setupListViewItemChecked() {
        for (int i = 0; i < entryValues.size(); i++) {
            String entryValue = entryValues.get(i);
            boolean checked = values.contains(entryValue);
            listView.setItemChecked(i, checked);
        }
    }

    private Set<String> getStringSetOfCheckedItems() {
        HashSet<String> stringSet = new HashSet<String>();
        SparseBooleanArray checkedPositions = listView.getCheckedItemPositions();
        for (int i = 0; i < entryValues.size(); i++) {
            String entryValue = entryValues.get(i);
            if (checkedPositions.get(i)) {
                stringSet.add(entryValue);
            }
        }
        return stringSet;
    }    
    
    private void updateOKButton() {
        Dialog dialog = getDialog();
        
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
    protected Object onGetDefaultValue(TypedArray a, int index) {
        CharSequence[] defalutValues = a.getTextArray(index);
        Set<String> stringSet = new HashSet<String>();
        if (defalutValues != null) {
            for (CharSequence defaultValue : defalutValues) {
                stringSet.add(defaultValue.toString());
            }
        }
        return stringSet;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected void onSetInitialValue(boolean restorePersistedValue,
            Object defaultValue) {
        SharedPreferences prefs = getSharedPreferences();
        if (restorePersistedValue) {
            values = prefs.getStringSet(getKey(), new HashSet<String>());
        } else {
            values = (Set<String>)defaultValue;
        }
    }
    
    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(), 
                android.R.layout.simple_list_item_multiple_choice, 
                entries);
        
        listView = (ListView)view.findViewById(android.R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onItemClickListener);
    }
    
    @Override
    protected void showDialog(Bundle state) {
        super.showDialog(state);
        
        setupListViewItemChecked();
        updateOKButton();
    }
    
    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        
        if (positiveResult) {
            SharedPreferences.Editor editor = getSharedPreferences().edit();
            editor.putStringSet(getKey(), getStringSetOfCheckedItems());
            editor.commit();
        }
    }
    
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
            values = getStringSetOfCheckedItems();
            updateOKButton();
        }
    };
}
