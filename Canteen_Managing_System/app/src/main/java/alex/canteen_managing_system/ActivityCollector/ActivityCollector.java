package alex.canteen_managing_system.ActivityCollector;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin L on 2017/11/6.
 */

public class ActivityCollector{

    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public static void finishAll(){
        for (Activity activity : activities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
        activities.clear();
    }
}