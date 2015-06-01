package hackingedu.sf.com.hackingedu;

import android.app.Application;
import com.parse.Parse;

/**
 * Created by vivekvinodh on 5/25/15.
 */
public class App extends Application {

    @Override public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "6iCemurReQldHcZGy9SgMjMKpIM1SVb91lzD8sKi", "j9yKLdSIcXEO8w53Aiz8VwKanWqgmlWXwsb92Cfo");

    }

}
