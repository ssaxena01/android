package droid.com.doordashfavorites.rest.dto;

import droid.com.doordashfavorites.rest.model.DiscoverResponse;
import droid.com.doordashfavorites.rest.model.RestaurantDetailResponse;

/**
 * Created by Shalini Saxena on 4/2/17.
 */
public class DiscoverDTO {

    private String name;
    private String description;
    private String imageUrl;
    private String status;
    private String id;
    private double deliveryFee;

    public DiscoverDTO(DiscoverResponse response) {
        this.name = response.getName();
        this.description = response.getDescription();
        this.status = response.getStatus();
        this.imageUrl = response.getImgUrl();
        this.deliveryFee = response.getDeliveryFee() == 0 ? 0 : response.getDeliveryFee() / 100.00;
        this.id = response.getId();
    }

    public DiscoverDTO(RestaurantDetailResponse response) {
        this.name = response.getName();
        this.description = response.getDescription();
        this.status = response.getStatus();
        this.imageUrl = response.getImgUrl();
        this.deliveryFee = response.getDeliveryFee() == 0 ? 0 : response.getDeliveryFee() / 100.00;
        this.id = response.getId();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }
}
