package com.example.liveticket;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import Camera.CameraManager;
import Interface.IAsyncCallBack;
import Interface.IPreviewCallback;
import RequestApiLib.RequestAsyncResult;
import RequestApiLib.RequestAsyncTask;
import util.ConvertUtil;
import Enum.*;

public class ScannerActivity extends BaseActivity implements IPreviewCallback, IAsyncCallBack
{
    /**
     * frame layout
     */
    private FrameLayout frame;

    /**
     * camera manager
     */
    private CameraManager cameraManager;

    /**
     * manual input button
     */
    private Button btnManuallInput;

    /**
     * async task to request API
     */
    private RequestAsyncTask requestAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        this.getWindow().getDecorView().setBackgroundColor(Color.rgb(119, 148, 186));

        try
        {
            this.frame = (FrameLayout) this.findViewById(R.id.previewFrame);
            this.cameraManager = CameraManager.getInstance(this.getBaseContext(), this);

            this.connectCamera();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.promptDialog(this.getString(R.string.common_error_title), this.getString(R.string.camera_access_error));
        }

        this.btnManuallInput = (Button)this.findViewById(R.id.btnInput);
        this.btnManuallInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScannerActivity.this.navigateToActivity(ManualInputActivity.class, AnimationDirection.LEFT);

                /*Intent intent = new Intent(ScannerActivity.this.getBaseContext(), ManualInputActivity.class);
                ScannerActivity.this.startActivity(intent);
                ScannerActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);*/
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.scanner, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent intent = new Intent(getBaseContext(), LogoutActivity.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        return true;
    }

    @Override
    public void onPreviewCallback(byte[] data, int width, int height)
    {
        String decodedString = ConvertUtil.barcodeToString(data, width, height);

        if (!TextUtils.isEmpty(decodedString))
        {
            if (this.requestAsyncTask != null && this.requestAsyncTask.IsBusy())
                return;

            this.requestServer(decodedString);
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        //this.frame.removeView(this.cameraManager.getCameraPreview());
        this.cameraManager.releaseCamera();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        try
        {
           this.connectCamera();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.promptDialog(this.getString(R.string.common_error_title), this.getString(R.string.camera_access_error));
        }
    }

    /**
     * reconnect camera
     */
    private void connectCamera() throws Exception
    {
        try
        {
            if (!this.cameraManager.isCameraAvailable())
            {
                this.cameraManager.reconnectCamera();
            }

            try
            {
                this.frame.removeView(this.cameraManager.getCameraPreview());
            }
            catch(Exception e)
            {
                /**
                 * attemp to remove view
                 */
            }

            this.frame.addView(cameraManager.getCameraPreview());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void onBeginTask()
    {
        this.showLoader(this.getString(R.string.scan_loader_title), this.getString(R.string.scan_loader_message));
    }

    @Override
    public void onTaskComplete(RequestAsyncResult result)
    {
        this.dismissLoader();

        if (result.getHasError() || result.StatusCode() != 200)
        {
            /**
             * navigate to invalid activity
             */
            this.navigateToActivity(InvalidActivity.class, AnimationDirection.DOWN);

            /*Intent intent = new Intent(this.getBaseContext(), InvalidActivity.class);
            this.startActivity(intent);*/

            return;
        }

        /**
         * navigate to valid activity
         */
        this.navigateToActivity(ValidActivity.class, AnimationDirection.DOWN);

        /*Intent intent = new Intent(this.getBaseContext(), ValidActivity.class);
        this.startActivity(intent);*/
    }

    /**
     * request ticket information from server
     * @param decodedString
     */
    private void requestServer(String decodedString)
    {
        this.cameraManager.stopPreview();

        ArrayList<NameValuePair> requestParams = new ArrayList<NameValuePair>();
        requestParams.add(new BasicNameValuePair(this.getString(R.string.code_request_parameter), decodedString));
        requestParams.add(new BasicNameValuePair(this.getString(R.string.access_token_request_parameter), App.USER_INFO().getAccess_token()));

        this.requestAsyncTask = new RequestAsyncTask(this.getString(R.string.scan_url), requestParams, null, this);
        requestAsyncTask.execute();
    }
}
