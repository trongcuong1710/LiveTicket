package com.example.liveticket;

import android.app.ActionBar;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import Interface.IAsyncCallBack;
import RequestApiLib.RequestAsyncResult;
import RequestApiLib.RequestAsyncTask;
import Enum.*;

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
                   ManualInputActivity.this.promptDialog(ManualInputActivity.this.getString(R.string.validation_failed_title), ManualInputActivity.this.getString(R.string.manual_input_code_validation_failed));
                   return;
                }

                ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair(ManualInputActivity.this.getString(R.string.code_request_parameter), code));
                params.add(new BasicNameValuePair(ManualInputActivity.this.getString(R.string.access_token_request_parameter), App.USER_INFO().getAccess_token()));

                RequestAsyncTask requestAsyncTask = new RequestAsyncTask(ManualInputActivity.this.getString(R.string.scan_url), params, null, ManualInputActivity.this);
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
            this.navigateBackToParent(AnimationDirection.RIGHT);

            /*NavUtils.navigateUpFromSameTask(this);
            this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);*/

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBeginTask() {
        this.showLoader(this.getString(R.string.scan_loader_title), this.getString(R.string.scan_loader_message));
    }

    @Override
    public void onTaskComplete(RequestAsyncResult result) {
        this.dismissLoader();

        if (result.getHasError() || result.StatusCode() != 200)
        {
            /**
             * redirect to invalid page
             */
            this.navigateToActivity(InvalidActivity.class, AnimationDirection.DOWN);

            /*Intent intent = new Intent(this.getBaseContext(), InvalidActivity.class);
            this.startActivity(intent);*/

            return;
        }

        /**
         * redirect to valid page
         */
        this.navigateToActivity(ValidActivity.class, AnimationDirection.DOWN);

        /*Intent intent = new Intent(this.getBaseContext(), ValidActivity.class);
        this.startActivity(intent);*/
    }
}
