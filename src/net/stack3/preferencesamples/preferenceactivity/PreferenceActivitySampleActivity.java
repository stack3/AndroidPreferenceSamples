package net.stack3.preferencesamples.preferenceactivity;

import java.util.ArrayList;
import java.util.Set;

import com.example.preferencesamples.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.PreferenceFragment;
import android.text.TextUtils;

public class PreferenceActivitySampleActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new CustomPreferenceFragment()).commit();
    }
    
    public static class CustomPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference_activity_sample);
            
            EditTextPreference usernamePref = (EditTextPreference)findPreference(getString(R.string.pref_key_username));
            usernamePref.setSummary(usernamePref.getText());
            
            ListPreference genderPref = (ListPreference)findPreference(getString(R.string.pref_key_gender));
            genderPref.setSummary(genderPref.getEntry());
            
            MultiSelectListPreference favoriteColorsPref = (MultiSelectListPreference)findPreference(getString(R.string.pref_key_favorite_colors));
            favoriteColorsPref.setSummary(multiSelectListSummary(R.string.pref_key_favorite_colors));
        }

        @Override
        public void onResume() {
            super.onResume();
            SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
            sharedPreferences.registerOnSharedPreferenceChangeListener(onPreferenceChangeListenter);
        }
        
        @Override
        public void onPause() {
            super.onPause();
            SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(onPreferenceChangeListenter);
        }
        
        private OnSharedPreferenceChangeListener onPreferenceChangeListenter = new OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals(getString(R.string.pref_key_username))) {
                    EditTextPreference pref = (EditTextPreference)findPreference(key);
                    pref.setSummary(pref.getText());
                } else if (key.equals(getString(R.string.pref_key_gender))) {
                    ListPreference pref = (ListPreference)findPreference(key);
                    pref.setSummary(pref.getEntry());
                } else if (key.equals(getString(R.string.pref_key_favorite_colors))) {
                    MultiSelectListPreference pref = (MultiSelectListPreference)findPreference(getString(R.string.pref_key_favorite_colors));
                    pref.setSummary(multiSelectListSummary(R.string.pref_key_favorite_colors));
                }
            }
        };
        
        private String multiSelectListSummary(int keyResId) {
            MultiSelectListPreference pref = (MultiSelectListPreference)findPreference(getString(keyResId));
            ArrayList<CharSequence> summaryArray = new ArrayList<CharSequence>();
            CharSequence[] entries = pref.getEntries();
            CharSequence[] entryValues = pref.getEntryValues();
            Set<String> values = pref.getValues();
            for (CharSequence entryValue : entryValues) {
                if (values.contains(entryValue)) {
                    int index = pref.findIndexOfValue(entryValue.toString());
                    if (index >= 0) {
                        summaryArray.add(entries[index]);
                    }
                }
            }
            return TextUtils.join(", ", summaryArray);
        }
    }    
}
