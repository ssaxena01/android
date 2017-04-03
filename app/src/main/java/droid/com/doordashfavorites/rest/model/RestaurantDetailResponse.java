package droid.com.doordashfavorites.rest.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Shalini Saxena on 4/2/17.
 */
public class RestaurantDetailResponse implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("cover_img_url")
    private String imgUrl;

    @SerializedName("status")
    private String status;

    @SerializedName("delivery_fee")
    private long deliveryFee;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getStatus() {
        return status;
    }

    public long getDeliveryFee() {
        return deliveryFee;
    }
}
