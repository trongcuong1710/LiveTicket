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
import Dialog.*;
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

    /**
     * login url
     */
    private final String loginURL = "https://sss-mobile-test.herokuapp.com/login";

    @Override
    public void onBeginTask() {
        this.showLoader();
    }

    @Override
    public void onTaskComplete(RequestAsyncResult result)
    {
        this.dismissLoader();

        /**
         * prompt when login faile
         */
        if (result == null || result.getHasError() == true)
        {
            this.promptDialog("Login Failed!", "Please check your user name, password and make sure you're connecting to network.");
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
            this.promptDialog("Error!", "An error has occur, please restart application and try again");
            return;
        }

        /**
         * save user information to shared preference
         */
        UserModel user = UserModel.getInstance();
        try {
            user.setId(json.getString(UserModel.ID_KEY));
            user.setUsername(json.getString(UserModel.USER_NAME_KEY));
            user.setEmail(json.getString(UserModel.EMAIL_KEY));
            user.setAccess_token(json.getString(UserModel.ACCESS_TOKEN_KEY));
            user.Update();
        } catch (JSONException e) {
            e.printStackTrace();
            this.promptDialog("Error!", "An error has occur, please restart application and try again");
            return;
        }

        Intent intent = new Intent(getBaseContext(), ScannerActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.btnLogin = (Button)findViewById(R.id.btnLogin);
        this.txtUserName = (EditText)findViewById(R.id.txtUserName);
        this.txtPassword = (EditText)findViewById(R.id.txtPassword);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
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
                    promptDialog("Validation Failed!", "Please input user name.");
                    return;
                }

                if (TextUtils.isEmpty(password))
                {
                    promptDialog("Validation Failed!", "Please input password.");
                    return;
                }

                ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("password", password));

                RequestAsyncTask request = new RequestAsyncTask(loginURL, params, null, (IAsyncCallBack)LoginActivity.this);
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
