package net.stack3.preferencesamples.model;

import java.util.ArrayList;
import java.util.List;

import net.stack3.preferencesamples.R;
import android.content.res.Resources;

public class Gender {
    private static ArrayList<Gender> allGenders; 
    private int id;
    private String name;
    
    public Gender(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public static List<Gender> getAllGenders() {
        return allGenders;
    }
    
    public static void loadGenders(Resources resources) {
        if (allGenders == null) {
            allGenders = new ArrayList<Gender>();
            String[] nameArray = resources.getStringArray(R.array.gender_names);
            String[] idArray = resources.getStringArray(R.array.gender_ids);
            if (nameArray.length != idArray.length) {
                throw new Error("Length does not match.");
            }
            for (int i = 0; i < nameArray.length; i++) {
                int id = Integer.parseInt(idArray[i]);
                Gender gender = new Gender(id, nameArray[i]);
                allGenders.add(gender);
            }
        }
    }

    public static Gender fromId(int id) {
        List<Gender> genders = getAllGenders();
        for (Gender gender : genders) {
            if (gender.getId() == id) {
                return gender;
            }
        }
        return null;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
}
