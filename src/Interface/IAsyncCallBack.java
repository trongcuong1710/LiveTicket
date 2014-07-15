package Interface;

import RequestApiLib.*;

/**
 * Created by DFS on 7/15/2014.
 */
public interface IAsyncCallBack
{
    /**
     * onPreExecute
     */
    void onBeginTask();

    void onTaskComplete(RequestAsyncResult result);
}
