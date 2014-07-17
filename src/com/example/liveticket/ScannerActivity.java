package com.example.liveticket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.liveticket.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;

import ApiModel.UserModel;
import Camera.CameraManager;
import Interface.IAsyncCallBack;
import Interface.IPreviewCallback;
import RequestApiLib.RequestAsyncResult;
import RequestApiLib.RequestAsyncTask;
import util.ConvertUtil;

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
     * request url
     */
    private final String REQUEST_URL = "https://sss-mobile-test.herokuapp.com/scan";

    /**
     * user info
     */
    private UserModel user = UserModel.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        try
        {
            this.frame = (FrameLayout) this.findViewById(R.id.previewFrame);
            this.cameraManager = CameraManager.getInstance(this.getBaseContext(), this);

            this.connectCamera();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.promptDialog("Error!", "Cannot access camera!");
        }

        this.btnManuallInput = (Button)this.findViewById(R.id.btnInput);
        this.btnManuallInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScannerActivity.this.getBaseContext(), ManualInputActivity.class);
                ScannerActivity.this.startActivity(intent);
                ScannerActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
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
            this.cameraManager.stopPreview();

            ArrayList<NameValuePair> requestParams = new ArrayList<NameValuePair>();
            requestParams.add(new BasicNameValuePair("code", decodedString));
            requestParams.add(new BasicNameValuePair("access_token", this.user.getAccess_token()));

            RequestAsyncTask requestAsyncTask = new RequestAsyncTask(this.REQUEST_URL, requestParams, null, this);
            requestAsyncTask.execute();
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        this.frame.removeView(this.cameraManager.getCameraPreview());
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
            this.promptDialog("Error!", "Can not reconnect camera!");
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
        this.showLoader("Please wait...!", "Requesting ticket information from server...!");
    }

    @Override
    public void onTaskComplete(RequestAsyncResult result)
    {
        this.dismissLoader();

        if (result.getHasError() || result.StatusCode() != 200)
        {
            Intent intent = new Intent(this.getBaseContext(), InvalidActivity.class);
            this.startActivity(intent);
            return;
        }

        Intent intent = new Intent(this.getBaseContext(), ValidActivity.class);
        this.startActivity(intent);
        return;
    }
}
