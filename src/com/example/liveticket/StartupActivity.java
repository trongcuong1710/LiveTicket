package com.example.liveticket;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import Enum.*;

import ApiModel.UserModel;

public class StartupActivity extends BaseActivity {
    /**
     * delayed time before start next activity
     */
    private long delayed = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        UserModel user = UserModel.getInstance();

        if (user.isLogin())
        {
            this.handle(ScannerActivity.class);
            /*Intent intent = new Intent(getBaseContext(), ScannerActivity.class);
            startActivity(intent);*/
        }
        else
        {
            this.handle(LoginActivity.class);
            /*Intent intent = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(intent);*/
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.startup, menu);
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

    /**
     * create a new handler
     * @param cls
     */
    private void handle(Class<?> cls)
    {
        final Class<?> clss = cls;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateToActivity(clss, AnimationDirection.LEFT);
                finish();
            }
        }, this.delayed);
    }
}
