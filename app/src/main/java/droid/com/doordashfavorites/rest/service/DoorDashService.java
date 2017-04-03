package droid.com.doordashfavorites.rest.service;

import droid.com.doordashfavorites.rest.dto.DiscoverDTO;
import rx.Observable;

interface DoorDashService {

    Observable<DiscoverDTO> getRestaurantList(String latitude, String longitude);

    Observable<DiscoverDTO> getRestaurantDetail(String id);
}
