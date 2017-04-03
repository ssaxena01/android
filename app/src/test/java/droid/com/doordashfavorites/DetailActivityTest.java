package droid.com.doordashfavorites;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import droid.com.doordashfavorites.app.Constants;
import droid.com.doordashfavorites.ui.DetailActivity;

import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DetailActivityTest {

    @Test
    public void clickingLogin_shouldStartLoginActivity() {
        DetailActivity activity = Robolectric.setupActivity(DetailActivity.class);

        if (activity.getIntent() != null && activity.getIntent().getExtras() != null) {
            String name = activity.getIntent().getExtras().getString(Constants.RESTAURANT_NAME);
            assertTrue(activity.getTitle().toString().equals(name));

        }
    }

}
