package net.stack3.preferencesamples.custompreference;

import java.util.ArrayList;
import java.util.Set;

import net.stack3.preferencesamples.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.MultiSelectListPreference;
import android.preference.PreferenceFragment;
import android.text.TextUtils;

public class CustomPreferenceSampleActivity extends Activity {
    public static final String PREF_KEY_USERNAME = "username";
    public static final String PREF_KEY_FAVORITED_COLORS = "favorite_colors";
    
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
            addPreferencesFromResource(R.xml.custom_preference_screen_sample);
            
            EditTextPreference usernamePref = (EditTextPreference)findPreference(PREF_KEY_USERNAME);
            usernamePref.setSummary(usernamePref.getText());
            
            MultiSelectListPreference favoriteColorsPref = (MultiSelectListPreference)findPreference(PREF_KEY_FAVORITED_COLORS);
            favoriteColorsPref.setSummary(multiSelectListSummary(PREF_KEY_FAVORITED_COLORS));
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
                if (key.equals(PREF_KEY_USERNAME)) {
                    EditTextPreference pref = (EditTextPreference)findPreference(key);
                    pref.setSummary(pref.getText());
                } else if (key.equals(PREF_KEY_FAVORITED_COLORS)) {
                    MultiSelectListPreference pref = (MultiSelectListPreference)findPreference(PREF_KEY_FAVORITED_COLORS);
                    pref.setSummary(multiSelectListSummary(PREF_KEY_FAVORITED_COLORS));
                }
            }
        };
        
        private String multiSelectListSummary(String prefKey) {
            MultiSelectListPreference pref = (MultiSelectListPreference)findPreference(prefKey);
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
