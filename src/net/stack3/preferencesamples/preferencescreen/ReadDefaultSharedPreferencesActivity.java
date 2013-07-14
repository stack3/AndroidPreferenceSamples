package net.stack3.preferencesamples.preferencescreen;

import java.util.Set;

import net.stack3.preferencesamples.R;
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
        Set<String> favoriteColors = prefs.getStringSet(PreferenceScreenSampleActivity.PREF_KEY_FAVORITED_COLORS, null);
        boolean isPushNotification = prefs.getBoolean(PreferenceScreenSampleActivity.PREF_KEY_PUSH_NOTIFICATION, false);
        boolean isReceiveNewsletter = prefs.getBoolean(PreferenceScreenSampleActivity.PREF_KEY_RECEIVE_NEWSLETTER, false);
        
        StringBuilder text = new StringBuilder();
        text.append(String.format("%s: %s\n", getString(R.string.username), username));
        text.append(String.format("%s: %d\n", getString(R.string.gender), genderId));
        if (favoriteColors != null) {
            text.append(String.format("%s: %s\n", getString(R.string.favorite_colors), favoriteColors.toString()));
        }
        text.append(String.format("%s: %b\n", getString(R.string.push_notification), isPushNotification));
        text.append(String.format("%s: %b\n", getString(R.string.receive_newsletter), isReceiveNewsletter));
        
        TextView textView = (TextView)findViewById(R.id.text);
        textView.setText(text);
    }
}
