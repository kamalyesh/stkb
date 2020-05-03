package in.kkamalyesh.apps.stk.virtualkeyboard;

import android.app.ActivityManager;

public class KServiceManager {
    public static boolean checkServiceRunning(ActivityManager manager, Class<?> serviceClass){
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
        {
            if (serviceClass.getName().equals(service.service.getClassName())){
                return true;
            }
        }
        return false;
    }
}
