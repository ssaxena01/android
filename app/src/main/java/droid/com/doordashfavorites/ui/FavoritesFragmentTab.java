package droid.com.doordashfavorites.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.HashSet;

import droid.com.doordashfavorites.R;
import droid.com.doordashfavorites.realm.FavoriteDTO;
import droid.com.doordashfavorites.realm.RealmClient;
import droid.com.doordashfavorites.realm.RestaurantId;
import droid.com.doordashfavorites.ui.adapter.FavoriteAdapter;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;


public class FavoritesFragmentTab extends Fragment {

    private WeakReference<Context> context;
    private Realm realm;
    private RealmList<FavoriteDTO> filteredFavorites = new RealmList<>();
    private RecyclerView discoverList;
    private FavoriteAdapter adapter;
    private RealmResults<RestaurantId> idList;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = new WeakReference<>(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.realm =  RealmClient.with((Activity) context.get()).getRealm();
        RealmResults<FavoriteDTO> favoriteList = RealmClient.getInstance().getFavorites();
        idList =  RealmClient.getInstance().getAllIds();
        HashSet<String> set = createSet(idList);
        populateFilterList(favoriteList, set);
    }

    private HashSet<String> createSet(RealmResults<RestaurantId> idList) {
        HashSet<String> set = new HashSet<>();
        for(RestaurantId id : idList){
             set.add(id.getId());
        }

        return set;
    }

    private void populateFilterList(RealmResults<FavoriteDTO> favoriteList, HashSet<String> set) {
        if(favoriteList != null && !favoriteList.isEmpty()) {
            for (FavoriteDTO dto : favoriteList) {

                String restId = dto.getId();
                if(restId != null && set.contains(restId)) {
                    filteredFavorites.add(dto);
                }
            }
        }
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.discover_tab_layout, container, false);

        discoverList = (RecyclerView) rootView.findViewById(R.id.discover_list);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(context.get());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        discoverList.setLayoutManager(layoutManager);

        adapter = new FavoriteAdapter(context.get());
        if(filteredFavorites != null && !filteredFavorites.isEmpty())  {
            adapter.addAll(filteredFavorites);
        }
        discoverList.setAdapter(adapter);

        return rootView;
    }


}
