package com.example.liveticket;

import android.app.ActionBar;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import Dialog.ConfirmDialog;
import Interface.IConfirmDialog;
import Enum.*;

public class LogoutActivity extends BaseActivity implements IConfirmDialog
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
     * action bar
     */
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        this.textView = (TextView)this.findViewById(R.id.logoutTextView);
        this.textView.setText(Html.fromHtml(this.getString(R.string.logout_textview_content) + "<b>" + App.USER_INFO().getUsername() + "</b>"));

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
            this.navigateBackToParent(AnimationDirection.RIGHT);

            /*NavUtils.navigateUpFromSameTask(this);
            this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);*/

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
        App.USER_INFO().Logout();
        this.navigateToActivity(LoginActivity.class, AnimationDirection.RIGHT);

        /*Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(intent);*/
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
        dialog.setTitle(this.getString(R.string.logout_confirm_title));
        dialog.setMessage(this.getString(R.string.logout_confirm_message));
        dialog.show(this.getFragmentManager(), "tag");
    }
}
