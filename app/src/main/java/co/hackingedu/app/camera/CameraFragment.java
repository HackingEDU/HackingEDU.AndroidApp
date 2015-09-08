package co.hackingedu.app.camera;

/**
 * Created by Spicycurryman on 9/5/15.
 */
import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import java.util.List;


@SuppressWarnings("deprecation")
public class CameraFragment extends Fragment {
    private static final String TAG = CameraFragment.class.getCanonicalName();

    private SurfaceView surfaceView;
    private FrameLayout surfaceContainer;
    private Camera camera;
    private int camOrientation;
    private SurfaceCamCallback callback;
    private OrientationEventListener orientationListener = null;

    public CameraFragment() {
        super();
        camOrientation = 0;
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String packageName = inflater.getContext().getApplicationInfo().packageName;
        Resources r = inflater.getContext().getApplicationContext().getResources();

        int resourceId = r.getIdentifier("fragment_camera", "layout", packageName);
        View v = inflater.inflate(resourceId, container, false);
        surfaceView = new SurfaceView(inflater.getContext());

        resourceId = r.getIdentifier("surface_container", "id", packageName);
        surfaceContainer = (FrameLayout) v.findViewById(resourceId);

        return v;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        surfaceView = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (surfaceView != null) {
            this.camera = getCameraInstance();
            if (camera != null) {
                callback = new SurfaceCamCallback(camera);
                surfaceView.getHolder().addCallback(callback);
                surfaceContainer.addView(surfaceView,
                        new ViewGroup.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT)));
            }
        }

        if ( (getActivity() != null) && (orientationListener == null) ) {
            orientationListener = new OrientationEventListener(getActivity()) {
                private int orientationPrev = 0;

                @Override
                public void onOrientationChanged(int orientation) {
                    if (orientationPrev != orientation) {
                        orientationPrev = orientation;
                        setCameraDisplayOrientation();
                    }
                }
            };
            orientationListener.enable();
        }
    }

    private void setCameraDisplayOrientation() {
        if (camera == null) {
            return;
        }
        Display display = ((WindowManager)getActivity().getSystemService(Activity.WINDOW_SERVICE)).getDefaultDisplay();
        switch (display.getRotation() ) {
            case Surface.ROTATION_0:
                camOrientation = 90;
                break;
            case Surface.ROTATION_90:
                camOrientation = 0;
                break;
            case Surface.ROTATION_180:
                camOrientation = 270;
                break;
            case Surface.ROTATION_270:
                camOrientation = 180;
                break;
        }
        camera.setDisplayOrientation(camOrientation);
    }

    private static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
            Log.e(TAG, "Error opening camera: " + e.getMessage());
        }
        return c; // returns null if camera is unavailable
    }



    @Override
    public void onPause() {
        super.onPause();

        if (orientationListener != null) {
            orientationListener.disable();
            orientationListener = null;
        }
        if (surfaceView != null) {
            surfaceView.getHolder().removeCallback(callback);
            callback = null;
        }
        if (camera != null) {
            camera.setPreviewCallback(null);
            surfaceContainer.removeView(surfaceView);
        }
        releaseCamera();
    }

    private void releaseCamera() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }




    private class SurfaceCamCallback implements SurfaceHolder.Callback, Camera.PreviewCallback {
        private Camera camera;
        Camera.Size previewSize;

        public SurfaceCamCallback(Camera camera) {
            this.camera = camera;
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            //do nothing
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if (camera != null && holder != null) {
                camera.setPreviewCallback(null);
                camera.stopPreview();    // stop camera preview
                try {
                    // restart camera preview
                    configureCamera(width, height);
                    setCameraDisplayOrientation();
                    camera.setPreviewDisplay(holder);
                    camera.setPreviewCallback(this);
                    camera.startPreview();
                } catch (Exception e) {
                    Log.e(TAG, "Exception raised configuring camera: " + e.getMessage());
                }
            }
        }

        private void configureCamera(int width, int height) {
            if (camera == null) {
                return;
            }

            Camera.Parameters cameraParams = camera.getParameters();
            cameraParams.set("orientation", "portrait");
            List<Camera.Size> sizes = cameraParams.getSupportedPreviewSizes();
            previewSize = getOptimalPreviewSize(sizes, Math.max(width, height), Math.min(width, height));
            cameraParams.setPreviewSize(previewSize.width, previewSize.height);
            // set YUV data format.
            cameraParams.setPreviewFormat(ImageFormat.NV21);
            // set frequency of capture
            setAcceptableFrameRate(cameraParams);

            if (getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_AUTOFOCUS)) {
                if (cameraParams.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                    cameraParams.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                } else if (cameraParams.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
                    cameraParams.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                }
            }

            camera.setParameters(cameraParams);
        }

        private void setAcceptableFrameRate(Camera.Parameters params) {
            List<int[]> ranges = params.getSupportedPreviewFpsRange();
            int[] frameRate = {0, 0};
            for (int[] range : ranges) {
                if (range[0] > frameRate[0]) {
                    frameRate[0] = range[0];
                    frameRate[1] = range[1];
                }
            }
            params.setPreviewFpsRange(frameRate[0], frameRate[1]);
        }

        private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int targetWidth, int targetHeight) {
            final double ASPECT_TOLERANCE = 0.05;
            double targetRatio = (double) targetWidth / targetHeight;
            if (sizes == null)
                return null;

            Camera.Size optimalSize = null;
            double minDiff = Double.MAX_VALUE;

            for (Camera.Size size : sizes) {
                double ratio = (double) size.width / size.height;
                if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
                    continue;
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
            if (optimalSize == null) {
                minDiff = Double.MAX_VALUE;
                for (Camera.Size size : sizes) {
                    if (Math.abs(size.height - targetHeight) < minDiff) {
                        optimalSize = size;
                        minDiff = Math.abs(size.height - targetHeight);
                    }
                }
            }

            return optimalSize;
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (camera != null) {
                camera.setPreviewCallback(null);
                camera.stopPreview();
            }
        }

        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
        }
    }
}