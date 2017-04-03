package droid.com.doordashfavorites.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

import droid.com.doordashfavorites.R;
import droid.com.doordashfavorites.app.Constants;
import droid.com.doordashfavorites.realm.RealmClient;
import droid.com.doordashfavorites.rest.service.DoorDashServiceImpl;
import droid.com.doordashfavorites.ui.adapter.DiscoverAdapter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DiscoverFragmentTab extends Fragment {

    private static final String TAG = "DiscoverTab";
    private WeakReference<Context> context;
    private RecyclerView discoverList;
    private DoorDashServiceImpl doorDashService;
    private DiscoverAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = new WeakReference<>(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.doorDashService = new DoorDashServiceImpl();
        RealmClient.with(this).getRealm();
        RealmClient.getInstance().clearRestaurantId();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.discover_tab_layout, container, false);

        discoverList = (RecyclerView) rootView.findViewById(R.id.discover_list);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(context.get());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        discoverList.setLayoutManager(layoutManager);

        adapter = new DiscoverAdapter(context.get());
        discoverList.setAdapter(adapter);

        getNearByRestaurants();

        return rootView;
    }

    private void getNearByRestaurants() {

        doorDashService.getRestaurantList(Constants.LATITUDE, Constants.LONGITUDE)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        r -> {
                            adapter.addToAdapter(r);
                            adapter.notifyDataSetChanged();
                            updateFavoriteDB(r.getId());
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

    //Update the db such that stored favorite are marked as valid response from the API,
    // if they exist in the Discover response
    private void updateFavoriteDB(String id) {
        RealmClient.getInstance().saveInDB(id);
    }

}
