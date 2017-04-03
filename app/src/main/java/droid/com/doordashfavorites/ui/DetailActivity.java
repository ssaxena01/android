package droid.com.doordashfavorites.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import droid.com.doordashfavorites.R;
import droid.com.doordashfavorites.app.Constants;
import droid.com.doordashfavorites.realm.FavoriteDTO;
import droid.com.doordashfavorites.realm.RealmClient;
import droid.com.doordashfavorites.rest.dto.DiscoverDTO;
import droid.com.doordashfavorites.rest.service.DoorDashServiceImpl;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";
    private TextView descriptionView;
    private TextView statusView;
    private TextView deliveryFeeView;
    private ImageView coverImage;
    private DoorDashServiceImpl doorDashService;
    private String restaurantId;
    private Context context;
    private Button addBtn;
    private DiscoverDTO restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.doorDashService = new DoorDashServiceImpl();
        context = this;
        RealmClient.with(this).getRealm();

        if (getIntent() != null && getIntent().getExtras() != null) {
            String name = getIntent().getExtras().getString(Constants.RESTAURANT_NAME);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(name);
            }

            restaurantId = getIntent().getExtras().getString(Constants.ID);

        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        descriptionView = (TextView) findViewById(R.id.description_view);
        statusView = (TextView) findViewById(R.id.status_view);
        deliveryFeeView = (TextView) findViewById(R.id.price_view);
        coverImage = (ImageView) findViewById(R.id.cover_image);
        addBtn = (Button) findViewById(R.id.add_to_favorite);

        if (isFavorite()) {
            addBtn.setText(R.string.remove_favorite);
            addBtn.setOnClickListener(OnClickToRemove());
        } else {
            addBtn.setOnClickListener(OnClickToAdd());
        }

        if (restaurantId != null) {
            getDetails();
        }
    }

    private View.OnClickListener OnClickToRemove() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeFromDB();
                addBtn.setText(R.string.add_to_favorite);
                addBtn.setOnClickListener(OnClickToAdd());
            }
        };
    }

    private void removeFromDB() {
        RealmClient.getInstance().removeFromDB(restaurantId);
    }

    private boolean isFavorite() {
        return RealmClient.getInstance().favoriteExists(restaurantId);
    }

    private View.OnClickListener OnClickToAdd() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeInDB();
                addBtn.setText(R.string.remove_favorite);
                addBtn.setOnClickListener(OnClickToRemove());
            }
        };
    }

    private void storeInDB() {
        FavoriteDTO favorite = new FavoriteDTO();
        favorite.setDescription(restaurant.getDescription());
        favorite.setImageUrl(restaurant.getImageUrl());
        favorite.setName(restaurant.getName());
        favorite.setStatus(restaurant.getStatus());
        // Persist your data
        RealmClient.getInstance().commitChanges(favorite, restaurantId);
    }

    private void getDetails() {
        doorDashService.getRestaurantDetail(restaurantId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        r -> {
                            this.restaurant = r;
                            populateScreen();
                        },
                        e -> {
                            e.printStackTrace();
                            Log.e(TAG, "error");
//                            showEmptyView();
//                            hideLoaderView();

                        },
                        () -> {
                            //if list is empty show empty state
//                            if(getAdapterSize() > 0){
//                                showGridView();
//                            } else{
//                                showEmptyView();
//                            }
//                            hideLoaderView();
                        }

                );

    }

    private void populateScreen() {
        String url = restaurant.getImageUrl();
        if (url != null) {
            Picasso.with(context)
                    .load(url)
                    .fit().centerCrop()
                    .placeholder(R.drawable.ic_restaurant_menu_black_24dp)
                    .into(coverImage);
        } else {
            Picasso.with(context)
                    .load(R.drawable.ic_restaurant_menu_black_24dp)
                    .fit().centerCrop()
                    .into(coverImage);
        }

        descriptionView.setText(restaurant.getDescription());
        deliveryFeeView.setText(Double.toString(restaurant.getDeliveryFee()));
        statusView.setText(restaurant.getStatus());
    }

}
