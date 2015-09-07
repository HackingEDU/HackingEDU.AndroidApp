package co.hackingedu.app;

import co.hackingedu.app.styles.FontChange;

/**
 * Created by Spicycurryman on 9/4/15.
 */
public final class HackingEDUApplication extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FontChange.setDefaultFont(this, "DEFAULT", "Lato-Regular.ttf");
        FontChange.setDefaultFont(this, "MONOSPACE", "Lato-Regular.ttf");
        FontChange.setDefaultFont(this, "SERIF", "Lato-Regular.ttf");
        FontChange.setDefaultFont(this, "SANS_SERIF", "Lato-Regular.ttf");
    }
}