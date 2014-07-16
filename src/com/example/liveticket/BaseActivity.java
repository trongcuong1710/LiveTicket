package com.example.liveticket;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.example.liveticket.R;

import Dialog.PromptDialog;

public abstract class BaseActivity extends Activity
{
    /**
     * process dialog
     */
    private ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.base, menu);
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
     * show loader when perform async process
     */
    protected void showLoader()
    {
        if (this.loader == null)
        {
            this.loader = new ProgressDialog(this);
            this.loader.setTitle("Login process");
            this.loader.setMessage("Validating user...");
        }

        this.loader.show();
    }

    /**
     * dismiss loader when finish
     */
    protected void dismissLoader()
    {
        if (this.loader == null || !this.loader.isShowing())
        {
            return;
        }

        this.loader.dismiss();
    }

    /**
     * prompt dialog
     * @param title : dialog title
     * @param message : dialog message
     */
    protected void promptDialog(String title, String message)
    {
        PromptDialog dialog = new PromptDialog();
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.show(this.getFragmentManager(), "tag");
    }
}
