package com.example.simon.if26.chat.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.simon.chat.R;
import com.example.simon.if26.chat.adapters.ContactAdapter;
import com.example.simon.if26.chat.models.Contact;
import com.example.simon.if26.chat.utils.MessengerQueryManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by simon on 29/11/14.
 */
public class ContactListActivity extends Activity implements AdapterView.OnItemClickListener {

    public static final String CONTACT_ID = "com.example.simon.if26.chat.CONTACT_ID";

    private String _token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        _token = getIntent().getExtras().getString(LoginActivity.TOKEN);

        queryContactList();
    }

    private void queryContactList() {
        new GetContactListTask(this).execute(_token);
    }

    public void populateContacts(ArrayList<Contact> contacts) {
        ContactAdapter adapter = new ContactAdapter(this, contacts);
        ListView lv = (ListView) findViewById(R.id.contact_list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(this, MessageListActivity.class);
        i.putExtra(LoginActivity.TOKEN, _token);
        i.putExtra(ContactListActivity.CONTACT_ID, view.getTag().toString());
        this.startActivity(i);
    }

    private class GetContactListTask extends AsyncTask<String, Integer, JSONObject> {

        private ContactListActivity _activity;

        public GetContactListTask(ContactListActivity activity) {
            _activity = activity;
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            return MessengerQueryManager.queryContacts(params[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            JSONArray contacts = null;
            try {
                contacts = result.getJSONArray(Contact.KEY_CONTACTS);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (contacts != null) {
                _activity.populateContacts(Contact.fromJSON(contacts));
            }
        }
    }
}
