package droid.com.doordashfavorites.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Shalini Saxena on 4/2/17.
 */
public class RestaurantId extends RealmObject {

    public RestaurantId(){

    }

    @PrimaryKey
    private String id;

    public RestaurantId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
