package com.example.liveticket;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.liveticket.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import ApiModel.UserModel;
import Interface.IAsyncCallBack;
import RequestApiLib.RequestAsyncResult;
import RequestApiLib.RequestAsyncTask;

public class ManualInputActivity extends BaseActivity implements IAsyncCallBack
{
    /**
     * action bar
     */
    private ActionBar actionBar;

    /**
     * send button
     */
    private Button btnSend;

    /**
     * code text view
     */
    private EditText txtCode;

    /**
     * user model
     */
    private UserModel user = UserModel.getInstance();

    /**
     * request URL
     */
    private final String REQUEST_URL = "https://sss-mobile-test.herokuapp.com/scan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_input);

        this.txtCode = (EditText)this.findViewById(R.id.txtCode);

        this.btnSend = (Button)this.findViewById(R.id.btnSend);
        this.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = ManualInputActivity.this.txtCode.getText().toString().trim();

                if (TextUtils.isEmpty(code))
                {
                   ManualInputActivity.this.promptDialog("Validation Failed!", "Please input code!");
                   return;
                }

                ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("code", code));
                params.add(new BasicNameValuePair("access_token", ManualInputActivity.this.user.getAccess_token()));

                RequestAsyncTask requestAsyncTask = new RequestAsyncTask(ManualInputActivity.this.REQUEST_URL, params, null, ManualInputActivity.this);
                requestAsyncTask.execute();
            }
        });

        this.actionBar = getActionBar();
        this.actionBar.setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.manual_input, menu);
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
    public void onBeginTask() {
        this.showLoader("Please wait...", "Request ticket information from server...!");
    }

    @Override
    public void onTaskComplete(RequestAsyncResult result) {
        this.dismissLoader();

        if (result.getHasError() || result.StatusCode() != 200)
        {
            /**
             * redirect to invalid page
             */
            Intent intent = new Intent(this.getBaseContext(), InvalidActivity.class);
            this.startActivity(intent);
            return;
        }

        /**
         * redirect to valid page
         */
        Intent intent = new Intent(this.getBaseContext(), ValidActivity.class);
        this.startActivity(intent);
    }
}
