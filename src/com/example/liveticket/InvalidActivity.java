package com.example.liveticket;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import Enum.*;

public class InvalidActivity extends BaseActivity {
    /**
     * main layout
     */
    private RelativeLayout mainLayout;

    /**
     * scan next button
     */
    private Button btnScanNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invalid);

        View.OnClickListener onClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //InvalidActivity.this.navigateToActivity(ScannerActivity.class, AnimationDirection.UP);
                InvalidActivity.this.navigateBackToParent(AnimationDirection.UP);
            }
        };

        this.getWindow().getDecorView().setBackgroundColor(Color.rgb(207, 42, 39));

        this.mainLayout = (RelativeLayout)this.findViewById(R.id.invalidLayout);
        this.mainLayout.setOnClickListener(onClickListener);

        this.btnScanNext = (Button)this.findViewById(R.id.btnScanNext);
        this.btnScanNext.setOnClickListener(onClickListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.invalid, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
