package Interface;

import android.hardware.Camera;

/**
 * Created by DFS on 7/16/2014.
 */
public interface IPreviewCallback
{
    void onPreviewCallback(byte[] data, int width, int height);
}
