package com.example.simon.if26.chat.activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.simon.chat.R;
import com.example.simon.if26.chat.adapters.MessageAdapter;
import com.example.simon.if26.chat.models.Message;
import com.example.simon.if26.chat.utils.MessengerQueryManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by simon on 29/11/14.
 */
public class MessageListActivity extends Activity {

    private String _token;
    private String _id;
    private ArrayList<Message> _messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        _token = getIntent().getExtras().getString(LoginActivity.TOKEN).toString();
        _id = getIntent().getExtras().getString(ContactListActivity.CONTACT_ID);
        _messages = new ArrayList<Message>();

        queryMessageList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh_messages:
                populateMessages();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_message_list, menu);
        return true;
    }

    private void queryMessageList() {
        new GetMessageListTask(this).execute(_token, _id);
    }

    private void populateMessages() {
        MessageAdapter adapter = new MessageAdapter(this, _messages);
        ListView lv = (ListView) findViewById(R.id.message_list);
        lv.setAdapter(adapter);
        Toast.makeText(this, "Message list updated!", Toast.LENGTH_SHORT).show();
    }

    public void sendNewMessage(View view) {
        EditText newMessageField = (EditText) findViewById(R.id.new_message);
        String newMessage = (newMessageField).getText().toString();

        if (newMessage.isEmpty()) {
            Toast.makeText(this, "Cannot send an empty message :(", Toast.LENGTH_SHORT).show();
            return;
        }

        new SendNewMessageTask(this).execute(_token, _id, newMessage);
        newMessageField.setText("");
    }

    public ArrayList<Message> getMessages() {
        return _messages;
    }

    protected void setMessages(ArrayList<Message> _messages) {
        this._messages = _messages;
    }

    private class GetMessageListTask extends AsyncTask<String, Integer, JSONObject> {

        private MessageListActivity _activity;

        public GetMessageListTask(MessageListActivity activity) {
            _activity = activity;
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            return MessengerQueryManager.queryMessages(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            JSONArray messages = null;
            try {
                messages = result.getJSONArray(Message.KEY_MESSAGES);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (messages != null) {
                _activity.setMessages(Message.fromJSON(messages));
                _activity.populateMessages();
            }
        }
    }

    private class SendNewMessageTask extends AsyncTask<String, Integer, JSONObject> {

        private MessageListActivity _activity;

        public SendNewMessageTask(MessageListActivity activity) {
            _activity = activity;
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            return MessengerQueryManager.queryNewMessage(params[0], params[1], params[2]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            JSONObject message = null;
            try {
                message = result.getJSONObject(Message.KEY_MESSAGE);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (message != null) {
                _activity.getMessages().add(_activity.getMessages().size() - 1, new Message(message));
                _activity.populateMessages();
            }

        }
    }
}
