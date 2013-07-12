package net.stack3.preferencesamples.preferenceactivity;

import java.util.List;

import com.example.preferencesamples.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

public class PreferenceActivitySampleActivity extends PreferenceActivity {
    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.preference_activity_sample_fragment, target);
    }
    
    public static class CustomPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference_activity_sample);
        }
    }    
}
