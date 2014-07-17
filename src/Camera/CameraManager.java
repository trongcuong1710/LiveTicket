package Camera;

import android.content.Context;
import android.graphics.*;
import android.hardware.*;
import android.hardware.Camera;
import android.hardware.Camera.*;

import java.io.IOException;

import Interface.IPreviewCallback;

/**
 * Created by DFS on 7/16/2014.
 */
public class CameraManager
{
    /**
     * singleton instance of camera manager
     */
    private static CameraManager instance;

    /**
     * activity context
     */
    private Context context;

    /**
     * camera preview
     */
    private CameraPreview cameraPreview;

    /**
     * get camera preview
     * @return
     */
    public CameraPreview getCameraPreview()
    {
        return this.cameraPreview;
    }

    /**
     * get instance of camera manager
     * @param context : activity context
     * @return
     * @throws Throwable
     */
    public synchronized static CameraManager getInstance(Context context, IPreviewCallback listener) throws Exception
    {
        try
        {
            if (instance == null)
                instance = new CameraManager(context, listener);

            return instance;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * camera
     */
    private Camera camera;

    /**
     * listener for camera preview callback
     */
    private IPreviewCallback listener;

    /**
     * private constructor to implement singleton
     * @param context : : activity context
     */
    private CameraManager(Context context, IPreviewCallback listener) throws Exception
    {
        try
        {
            this.context = context;
            this.listener = listener;
            this.getCameraInstance();
            this.cameraPreview = new CameraPreview(this.context, this.camera, this.listener);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * get instance of camera
     * @return
     */
    private void getCameraInstance() throws Exception
    {
        try
        {
            CameraInfo cameraInfo = new CameraInfo();

            int frontId = -1;
            int backId = -1;
            int numberOfCameras = Camera.getNumberOfCameras();

            for (int i = 0; i < numberOfCameras; i++)
            {
                Camera.getCameraInfo(i, cameraInfo);

                if (cameraInfo.facing == CameraInfo.CAMERA_FACING_FRONT)
                {
                    frontId = i;
                }
                else if (cameraInfo.facing == CameraInfo.CAMERA_FACING_BACK)
                {
                    backId = i;
                }
            }

            if (backId == -1)
            {
                if (frontId == -1)
                    throw new Exception();

                this.camera = Camera.open(frontId);
                return;
            }

            this.camera = Camera.open(backId);
            Camera.Parameters parameters = this.camera.getParameters();
            parameters.set("orientation", "portrait");

            if (this.context.getPackageManager().hasSystemFeature("android.hardware.camera.autofocus"))
            {
                parameters.setFocusMode(Parameters.FOCUS_MODE_AUTO);
            }

            this.camera.setParameters(parameters);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    /**
     * stop camera preview
     */
    public void stopPreview()
    {
        this.camera.stopPreview();
    }

    /**
     * start camera preview
     */
    public void startPreview()
    {
        this.camera.startPreview();
    }

    /**
     * release camera
     */
    public void releaseCamera()
    {
        this.camera.stopPreview();
        this.camera.setPreviewCallback(null);
        this.cameraPreview.getHolder().removeCallback(this.cameraPreview);
        this.camera.release();
        this.camera = null;
    }

    /**
     * reconnect camera
     */
    public void reconnectCamera() throws Exception
    {
        try {
            this.getCameraInstance();
            this.cameraPreview = new CameraPreview(this.context, this.camera, this.listener);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * determine whether camera available
     * @return
     */
    public boolean isCameraAvailable()
    {
        return (!(this.camera == null));
    }
}
