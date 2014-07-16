package com.example.liveticket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.liveticket.R;

import Camera.CameraManager;
import Interface.IPreviewCallback;
import util.ConvertUtil;

public class ScannerActivity extends BaseActivity implements IPreviewCallback
{
    /**
     * frame layout
     */
    private FrameLayout frame;

    /**
     * camera manager
     */
    private CameraManager cameraManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        try
        {
            this.frame = (FrameLayout) this.findViewById(R.id.previewFrame);
            cameraManager = CameraManager.getInstance(this.getBaseContext(), this);
            this.frame.addView(cameraManager.getCameraPreview());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.promptDialog("Error!", "Cannot access camera!");
        }
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
        return true;
    }

    @Override
    public void onPreviewCallback(byte[] data, int width, int height)
    {
        String decodedString = ConvertUtil.barcodeToString(data, width, height);

        if (!TextUtils.isEmpty(decodedString))
        {
            this.cameraManager.stopPreview();
            this.promptDialog("Decoded!", decodedString);
        }

        this.cameraManager.startPreview();
    }
}
