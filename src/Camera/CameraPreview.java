package Camera;

import android.content.*;
import android.hardware.*;
import android.view.*;

import Interface.IPreviewCallback;

/**
 * Created by DFS on 7/16/2014.
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback
{
    /**
     * surface holder
     */
    private SurfaceHolder surfaceHolder;

    /**
     * camera
     */
    private Camera camera;

    /**
     * preview callback listener
     */
    private IPreviewCallback listener;

    /**
     * determine whether preview is running
     */
    private boolean isPreviewRunning;

    /**
     * context which camera's running on
     */
    private Context context;

    /**
     * constructor
     * @param context : activity context
     * @param camera : camera
     */
    public CameraPreview(Context context, Camera camera, IPreviewCallback listener)
    {
        super(context);

        this.camera = camera;
        this.listener = listener;

        this.surfaceHolder = getHolder();
        this.surfaceHolder.addCallback(this);
        this.surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        try
        {
            //this.camera.setPreviewDisplay(holder);
            this.camera.setDisplayOrientation(90);
            this.camera.startPreview();
            this.setPreviewCallbackListener();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        if (this.surfaceHolder.getSurface() == null)
        {
            return;
        }

        try
        {
            this.camera.stopPreview();
        }
        catch (Exception e)
        {

        }

        try
        {
            this.camera.setPreviewDisplay(this.surfaceHolder);
            this.camera.setDisplayOrientation(90);
            this.camera.startPreview();
            this.setPreviewCallbackListener();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    /**
     * set preview callback for camera
     */
    private void setPreviewCallbackListener()
    {
        Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera)
            {
                CameraPreview.this.listener.onPreviewCallback(data, camera.getParameters().getPreviewSize().width, camera.getParameters().getPreviewSize().height);
            }
        };

        this.camera.setPreviewCallback(previewCallback);
    }

    /**
     * set camera rotation
     */
    private void setPreviewRotation(int width, int height)
    {
        if (this.isPreviewRunning)
        {
            try
            {
                this.camera.stopPreview();
            }
            catch (Exception e)
            {
                /**
                 * attemp to stop camera preview
                 */
            }
        }

        Camera.Parameters parameters = this.camera.getParameters();
        Display display = ((WindowManager)this.context.getSystemService(this.context.WINDOW_SERVICE)).getDefaultDisplay();

        switch (display.getRotation())
        {
            case Surface.ROTATION_0:
                parameters.setPreviewSize();
        }
    }
}
