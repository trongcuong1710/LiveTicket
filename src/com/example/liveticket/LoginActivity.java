package com.example.liveticket;

import android.app.*;
import android.content.Intent;
import android.os.*;
import android.text.*;
import android.view.*;
import android.widget.*;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.*;

import java.util.ArrayList;

import ApiModel.UserModel;
import Interface.*;
import  Enum.*;
import RequestApiLib.RequestAsyncResult;
import RequestApiLib.RequestAsyncTask;

public class LoginActivity extends BaseActivity implements IAsyncCallBack {
    /**
     * button login
     */
	private Button btnLogin;

    /**
     * user name textbox
     */
	private EditText txtUserName;

    /**
     * password textbox
     */
	private EditText txtPassword;

    @Override
    public void onBeginTask() {
        this.showLoader(this.getString(R.string.login_loader_title), this.getString(R.string.login_loader_message));
    }

    @Override
    public void onTaskComplete(RequestAsyncResult result)
    {
        this.dismissLoader();

        /**
         * prompt when login failed
         */
        if (result == null || result.getHasError() == true)
        {
            this.promptDialog(this.getString(R.string.login_failed_title), this.getString(R.string.login_failed_message));
            return;
        }

        /**
         * convert result to json object
         */
        JSONObject json;
        try {
            json = new JSONObject(result.Result());
        } catch (JSONException e) {
            e.printStackTrace();
            this.promptDialog(this.getString(R.string.common_error_title), this.getString(R.string.common_error_message));
            return;
        }

        /**
         * save user information to shared preference
         */
        try {
            App.USER_INFO().setId(json.getString(UserModel.ID_KEY));
            App.USER_INFO().setUsername(json.getString(UserModel.USER_NAME_KEY));
            App.USER_INFO().setEmail(json.getString(UserModel.EMAIL_KEY));
            App.USER_INFO().setAccess_token(json.getString(UserModel.ACCESS_TOKEN_KEY));
            App.USER_INFO().Update();
        } catch (JSONException e) {
            e.printStackTrace();
            this.promptDialog("Error!", "An error has occur, please restart application and try again");
            return;
        }

        this.navigateToActivity(ScannerActivity.class, AnimationDirection.LEFT);

        /*Intent intent = new Intent(getBaseContext(), ScannerActivity.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.btnLogin = (Button)findViewById(R.id.btnLogin);
        this.txtUserName = (EditText)findViewById(R.id.txtUserName);
        this.txtPassword = (EditText)findViewById(R.id.txtPassword);
        this.btnLogin.setOnClickListener(new View.OnClickListener()
        {
			@Override
			public void onClick(View v)
            {
				// TODO Auto-generated method stub
				String username = txtUserName.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();

                if (TextUtils.isEmpty(username))
                {
                    LoginActivity.this.promptDialog(LoginActivity.this.getString(R.string.validation_failed_title), LoginActivity.this.getString(R.string.user_name_validation_failed_message));
                    return;
                }

                if (TextUtils.isEmpty(password))
                {
                    LoginActivity.this.promptDialog(LoginActivity.this.getString(R.string.validation_failed_title), LoginActivity.this.getString(R.string.password_validation_failed_message));
                    return;
                }

                ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair(LoginActivity.this.getString(R.string.user_name_request_parameter), username));
                params.add(new BasicNameValuePair(LoginActivity.this.getString(R.string.password_request_parameter), password));

                RequestAsyncTask request = new RequestAsyncTask(LoginActivity.this.getString(R.string.login_url), params, null, LoginActivity.this);
                request.execute();
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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
