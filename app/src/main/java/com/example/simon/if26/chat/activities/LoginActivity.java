package com.example.simon.if26.chat.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.simon.chat.R;
import com.example.simon.if26.chat.utils.MessengerQueryManager;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends Activity {

    public static final String TOKEN = "com.example.simon.if26.chat.TOKEN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View v) {
        String login = ((EditText) findViewById(R.id.login)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();

        if (login.isEmpty()) {
            Toast.makeText(this, "Login is missing", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "Password is missing", Toast.LENGTH_SHORT).show();
            return;
        }

        new LoginTask(this).execute(login, password);
    }

    public void onLogin(JSONObject result) {
        boolean authenticationFailed;
        try {
            authenticationFailed = result.getBoolean("error");
        } catch (JSONException e) {
            authenticationFailed = true;
        }

        if (authenticationFailed) {
            Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show();
            return;
        }

        String token;
        try {
            token = result.getString("token");
        } catch (JSONException e) {
            token = null;
        }

        if (token.isEmpty() || token == null) {
            Toast.makeText(this, "Token cannot be found", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent i = new Intent(this, ContactListActivity.class);
        i.putExtra(TOKEN, token);
        startActivity(i);
    }

    private class LoginTask extends AsyncTask<String, Integer, JSONObject> {

        private LoginActivity _activity;

        public LoginTask(LoginActivity activity) {
            this._activity = activity;
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            return new MessengerQueryManager().queryLogin(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            this._activity.onLogin(jsonObject);
        }
    }
}
