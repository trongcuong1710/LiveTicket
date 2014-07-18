package com.example.liveticket;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

import Dialog.PromptDialog;
import Enum.*;

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
     * @param title : loader title
     * @param message : loader message
     */
    protected void showLoader(String title, String message)
    {
        if (this.loader == null)
        {
            this.loader = new ProgressDialog(BaseActivity.this);
            this.loader.setTitle(title);
            this.loader.setMessage(message);
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

    /**
     * navigate to a specific activity
     * @param cls : activity to navigate to
     * @param animationDirection : animation direction
     */
    protected void navigateToActivity(Class<?> cls, AnimationDirection animationDirection)
    {
        Intent intent = new Intent(this.getBaseContext(), cls);
        startActivity(intent);

        this.overrideAnimation(animationDirection);
    }

    /**
     * navigate back to parent activity
     * @param animationDirection
     */
    protected void navigateBackToParent(AnimationDirection animationDirection)
    {
        this.finish();

        this.overrideAnimation(animationDirection);
    }

    /**
     * override pending transition
     * @param animationDirection
     */
    protected void overrideAnimation(AnimationDirection animationDirection)
    {
        switch (animationDirection)
        {
            case UP:
                this.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                break;
            case RIGHT:
                this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                break;
            case DOWN:
                this.overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
                break;
            case LEFT:
                this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
        }
    }
}
