package net.stack3.preferencesamples;

public class MenuItem {
    private String title;
    private Class<?> activityClass;
    
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Class<?> getActivityClass() {
        return activityClass;
    }
    public void setActivityClass(Class<?> activityClass) {
        this.activityClass = activityClass;
    }
    @Override
    public String toString() {
        return title;
    }
}
