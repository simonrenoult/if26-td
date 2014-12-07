package com.example.simon.if26.chat.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Message {

    public static final String KEY_MESSAGE = "message";
    public static final String KEY_DATE = "date";
    public static final String KEY_SENT = "sent";
    public static final String KEY_MESSAGES = "messages";

    private String _content;
    private String _date;
    private boolean _isSent;
    private Contact _sender;

    public Message(JSONObject json) {
        try {
            this.setContent(json.getString(KEY_MESSAGE));
            this.setDate(json.getString(KEY_DATE));
            this.setIsSent(json.getBoolean(KEY_SENT));
            this.setSender(null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Message(String content, String date, Contact sender, boolean isSent) {
        super();
        this._content = content;
        this._date = date;
        this._sender = sender;
        this._isSent = isSent;
    }

    public static ArrayList<Message> fromJSON(JSONArray arr) {
        ArrayList<Message> messages = new ArrayList<Message>();
        for (int i = 0; i < arr.length(); i++) {
            try {
                messages.add(new Message(arr.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return messages;
    }

    public String getContent() {
        return _content;
    }

    public void setContent(String content) {
        this._content = content;
    }

    public String getDate() {
        return _date;
    }

    public void setDate(String date) {
        this._date = date;
    }

    public Contact getSender() {
        return _sender;
    }

    public void setSender(Contact sender) {
        this._sender = sender;
    }

    public boolean isSent() {
        return this._isSent;
    }

    public void setIsSent(boolean isSent) {
        this._isSent = isSent;
    }

    @Override
    public String toString() {
        return this._content + " - Sent ? " + this._isSent;
    }

}