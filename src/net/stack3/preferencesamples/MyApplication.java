package net.stack3.preferencesamples;

import net.stack3.preferencesamples.model.Gender;
import android.app.Application;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        
        Gender.loadGenders(getResources());
    }
}
