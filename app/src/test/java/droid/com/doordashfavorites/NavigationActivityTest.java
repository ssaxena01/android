package droid.com.doordashfavorites;

import android.app.Activity;
import android.view.Menu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import droid.com.doordashfavorites.ui.NavigationActivity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.robolectric.Shadows.shadowOf;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class NavigationActivityTest {

    @Test
    public void onCreateShouldInflateTheMenu() {
        Activity activity = Robolectric.setupActivity(NavigationActivity.class);

        final Menu menu = shadowOf(activity).getOptionsMenu();
        assertThat(menu.findItem(R.id.discover).getTitle()).isEqualTo(R.string.discover_label);
        assertThat(menu.findItem(R.id.favorites).getTitle()).isEqualTo(R.string.favorites_label);
    }

}
