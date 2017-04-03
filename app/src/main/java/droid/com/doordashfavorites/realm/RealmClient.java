package droid.com.doordashfavorites.realm;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class RealmClient {

    private static RealmClient instance;
    private final Realm realm;

    public RealmClient(Application application) {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        //Need to persist data across app restarts. DON'T drop it
//        Realm.deleteRealm(realmConfiguration);
        Realm.setDefaultConfiguration(realmConfiguration);
        realm = Realm.getDefaultInstance();
    }

    public static RealmClient with(Activity activity) {

        if (instance == null) {
            instance = new RealmClient(activity.getApplication());
        }
        return instance;

    }

    public static RealmClient with(Fragment context) {
        if (instance == null) {
            instance = new RealmClient(context.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmClient getInstance() {
        return instance;
    }

    public Realm getRealm() {
        return realm;
    }

    public void commitChanges(FavoriteDTO favorite, String restaurantId) {
        realm.beginTransaction();
        favorite.setId(restaurantId);
        realm.copyToRealmOrUpdate(favorite);
        realm.commitTransaction();
    }

    public boolean favoriteExists(String restaurantId) {
        FavoriteDTO fav = realm.where(FavoriteDTO.class).equalTo("id", restaurantId).findFirst();

        if (fav != null) {
            return true;
        }

        return false;
    }

    public void removeFromDB(String restaurantId) {

        realm.beginTransaction();
        RealmResults<FavoriteDTO> result = realm.where(FavoriteDTO.class).equalTo("id", restaurantId).findAll();
        result.deleteAllFromRealm();
        realm.commitTransaction();
    }

    public RealmResults<FavoriteDTO> getFavorites() {
        return realm.where(FavoriteDTO.class).findAll();
    }

    public void saveInDB(String id) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(new RestaurantId(id));
        realm.commitTransaction();
    }

    public RealmResults<RestaurantId> getAllIds() {
        return realm.where(RestaurantId.class).findAll();
    }

    public boolean getId(RestaurantId value) {
        String id = value.getId();
        RestaurantId restaurantId = realm.where(RestaurantId.class).equalTo("id", id).findFirst();

        if (restaurantId != null) {
            return true;
        }

        return false;
    }

    public void clearRestaurantId() {

        realm.beginTransaction();
        RealmResults<RestaurantId> result = realm.where(RestaurantId.class).findAll();
        result.deleteAllFromRealm();
        realm.commitTransaction();
    }
}
