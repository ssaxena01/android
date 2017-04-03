package droid.com.doordashfavorites.rest;

import com.squareup.okhttp.OkHttpClient;

import java.util.List;

import droid.com.doordashfavorites.rest.model.DiscoverResponse;
import droid.com.doordashfavorites.rest.model.RestaurantDetailResponse;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;


/**
 * Entry point for all services
 */
public class RestService {

    private static final String baseUrl = "https://api.doordash.com/v2/restaurant";

    public static <T> T createRestClient(Class<T> restClass) {

        // set endpoint url and use OkHTTP as HTTP client
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(baseUrl)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient(new OkHttpClient()));

        RestAdapter rAdapter = builder.build();

        return rAdapter.create(restClass);
    }

    public interface DoorDashAPI{
        @GET("/")
        Observable<List<DiscoverResponse>> getRestaurants(@Query("lat") String latitude,
                                                               @Query("lng") String longitude);

        @GET("/{id}")
        Observable<RestaurantDetailResponse> getRestaurantDetail(@Path("id") String id);
    }
}
