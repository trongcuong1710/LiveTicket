package com.example.liveticket;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ApiModel.UserModel;
import Dialog.ConfirmDialog;
import Interface.IConfirmDialog;

public class LogoutActivity extends Activity implements IConfirmDialog
{
    /**
     * logout button
     */
    private Button btnLogout;

    /**
     * text view
     */
    private TextView textView;

    /**
     * user model
     */
    private UserModel user = UserModel.getInstance();

    /**
     * action bar
     */
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        this.textView = (TextView)this.findViewById(R.id.logoutTextView);
        this.textView.setText(Html.fromHtml("Login as: " + "<b>" + user.getUsername() + "</b>"));

        this.btnLogout = (Button)this.findViewById(R.id.btnLogout);
        this.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogoutActivity.this.confirmDialog();
            }
        });

        this.actionBar = this.getActionBar();
        this.actionBar.setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialogFragment)
    {
        /**
         * logout and redirect to login activity when user confirmed
         */
        user.Logout();
        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialogFragment)
    {
        /**
         * do nothing here
         */
    }

    /**
     * show confirm dialog when user press logout button
     */
    protected void confirmDialog()
    {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setTitle("Confirm!");
        dialog.setMessage("Do you really want to log out?");
        dialog.show(this.getFragmentManager(), "tag");
    }
}
