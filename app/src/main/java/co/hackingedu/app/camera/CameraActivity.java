package co.hackingedu.app.camera;

/**
 * Created by Spicycurryman on 9/5/15.
 */

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import co.hackingedu.app.R;


public class CameraActivity extends FragmentActivity {
    private static final String CAMERA_FRAGMENT = "camera_fragment";
    private CameraFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_camera);

        FragmentManager fm = getFragmentManager();
        fragment = (CameraFragment) fm.findFragmentByTag(CAMERA_FRAGMENT);
        if (fragment == null) {
            fragment = new CameraFragment();
            fragment.setRetainInstance(true);
            fm.beginTransaction().add(R.id.fragment_container, fragment, CAMERA_FRAGMENT).commit();
        }


    }
}
