package droid.com.doordashfavorites.app;

import android.app.Application;

import io.realm.Realm;

public class DoorDashApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
    }

}
