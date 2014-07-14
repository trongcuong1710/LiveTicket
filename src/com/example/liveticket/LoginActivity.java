package com.example.liveticket;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;


public class LoginActivity extends Activity {
	
	private Button btnLogin;
	private EditText txtUserName;
	private EditText txtpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.btnLogin = (Button)findViewById(R.id.btnLogin);
        this.txtUserName = (EditText)findViewById(R.id.txtUserName);
        this.txtpassword = (EditText)findViewById(R.id.txtPassword);
        
        this.btnLogin.setEnabled(false);
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
