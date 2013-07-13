package net.stack3.preferencesamples.preferencescreen;

import net.stack3.preferencesamples.R;
import net.stack3.preferencesamples.model.Gender;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

public class ReadDefaultSharedPreferencesActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_default_shared_preferences_activity);
    }
    
    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String username = prefs.getString(PreferenceScreenSampleActivity.PREF_KEY_USERNAME, ""); 
        String genderIdString = prefs.getString(PreferenceScreenSampleActivity.PREF_KEY_GENDER, "0");
        int genderId = Integer.parseInt(genderIdString);
        Gender gender = Gender.fromId(genderId);
        
        StringBuilder text = new StringBuilder();
        text.append(String.format("%s: %s\n", getString(R.string.username), username));
        text.append(String.format("%s: %s\n", getString(R.string.gender), gender.getName()));
        
        TextView textView = (TextView)findViewById(R.id.text);
        textView.setText(text);
    }
}
