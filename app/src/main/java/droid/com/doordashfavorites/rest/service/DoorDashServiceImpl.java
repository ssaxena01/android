package droid.com.doordashfavorites.rest.service;

import droid.com.doordashfavorites.rest.RestService;
import droid.com.doordashfavorites.rest.dto.DiscoverDTO;
import rx.Observable;

/**
 * GitHub service implementation
 */
public class DoorDashServiceImpl implements DoorDashService{

    private final RestService.DoorDashAPI service;

    public DoorDashServiceImpl() {
        this.service = RestService.createRestClient(RestService.DoorDashAPI.class);
    }

    @Override
    public Observable<DiscoverDTO> getRestaurantList(String latitude, String longitude) {
        return service.getRestaurants(latitude, longitude)
                .filter( response -> response != null)
                .flatMap(discoverResponses -> Observable.from(discoverResponses))
                .map(DiscoverDTO::new);
    }

    @Override
    public Observable<DiscoverDTO> getRestaurantDetail(String id){
        return service.getRestaurantDetail(id)
                .filter( response -> response != null)
                .map(DiscoverDTO::new);
    }
}
