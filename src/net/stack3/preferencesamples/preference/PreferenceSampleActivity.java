package net.stack3.preferencesamples.preference;

import java.io.ObjectInputStream.GetField;
import java.util.Date;

import com.example.preferencesamples.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PreferenceSampleActivity extends Activity {
    private static final String PREF_NAME = "preference-sample";
    private static final String PREF_KEY_SAVED_COUNT = "savedCount";
    private static final String PREF_KEY_SAVED_AT = "savedAt";
    
    private long savedCount;
    private Date savedAt;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("PreferenceSample");
        setContentView(R.layout.preference_sample_activity);
        
        Button saveButton = (Button)findViewById(R.id.save_button);
        saveButton.setOnClickListener(saveButtonOnClickListener);
        
        loadPreferences();
        updateView();
    }
    
    private OnClickListener saveButtonOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            savePreferences();
            updateView();
        }
    };
    
    private void updateView() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Saved Count: %d\n", savedCount));
        sb.append(String.format("Saved at: %s", savedAt));
        
        TextView textView = (TextView)findViewById(R.id.text);
        textView.setText(sb.toString());
    }
    
    private void loadPreferences() {
        SharedPreferences pref = getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        savedCount = pref.getLong(PREF_KEY_SAVED_COUNT, 0);
        long savedAtInterval = pref.getLong(PREF_KEY_SAVED_AT, 0);
        if (savedAtInterval == 0) {
            savedAt = null;
        } else {
            savedAt = new Date(savedAtInterval);
        }
    }
    
    private void savePreferences() {
        savedAt = new Date();
        
        savedCount++;
        
        SharedPreferences pref = getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = pref.edit();
        prefEditor.putLong(PREF_KEY_SAVED_COUNT, savedCount);
        prefEditor.putLong(PREF_KEY_SAVED_AT, savedAt.getTime());
        prefEditor.commit();
    }
}
