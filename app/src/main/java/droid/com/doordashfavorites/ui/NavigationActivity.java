package droid.com.doordashfavorites.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import droid.com.doordashfavorites.app.Constants;
import droid.com.doordashfavorites.R;

public class NavigationActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);


        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(OnNavigationItemSelected());

        // Initializing Drawer Layout and ActionBarToggle
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

        navigationView.getMenu().getItem(0).setChecked(true);
        setupDiscoverTab();

    }

    private void setupDiscoverTab() {
        toolbarTitle.setText(R.string.discover_label);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        DiscoverFragmentTab discoverFragment = new DiscoverFragmentTab();
        fragmentTransaction.replace(R.id.content_frame, discoverFragment, Constants.TAB_TAG_DISCOVER);
        fragmentTransaction.commit();
    }

    private NavigationView.OnNavigationItemSelectedListener OnNavigationItemSelected() {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                //Closing drawer on item click
                drawerLayout.closeDrawers();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();


                switch (menuItem.getItemId()) {

                    case R.id.favorites:
                        toolbarTitle.setText(R.string.favorites_label);
                        FavoritesFragmentTab favoritesFragment = new FavoritesFragmentTab();
                        fragmentTransaction.replace(R.id.content_frame, favoritesFragment, Constants.TAB_TAG_FAVORITES);
                        fragmentTransaction.commit();
                        return true;

                    default:
                    case R.id.discover:
                        setupDiscoverTab();
                        return true;
                }
            }
        };
    }
}
