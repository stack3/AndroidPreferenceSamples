package net.stack3.preferencesamples.model;

import net.stack3.preferencesamples.R;
import android.content.ContextWrapper;
import android.content.res.Resources;

public class Gender {
    private int intValue;
    
    public static Gender fromGenderIdString(String idString) {
        Gender gender = new Gender();
        gender.intValue = Integer.parseInt(idString);
        return gender;
    }
    
    public String getName(ContextWrapper context) {
        return getName(context.getResources());
    }
    
    public String getName(Resources resources) {
        String[] stringArray = resources.getStringArray(R.array.gender_names);
        if (intValue < stringArray.length) {
            return stringArray[intValue];
        } else {
            return null;
        }
    }
}
