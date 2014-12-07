package com.example.simon.if26.chat.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class Contact {

    public static final int MODEL_ID = 0;
    public static final String KEY_ID = "id";
    public static final String KEY_CONTACT = "contact";
    public static final String KEY_CONTACTS = "contacts";
    public static final String KEY_LAST_NAME = "last_name";
    public static final String KEY_FIRST_NAME = "first_name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_MESSAGE = "message";

    private int _id;
    private String _lastname;
    private String _firstname;
    private String _email;
    private ArrayList<Message> _messages;

    public Contact() {
        this._messages = new ArrayList<Message>();
    }

    public Contact(JSONObject json) {
        try {
            this.setId(json.getInt(KEY_ID));
            this.setLastname(json.getJSONObject(KEY_CONTACT).getString(KEY_LAST_NAME));
            this.setFirstname(json.getJSONObject(KEY_CONTACT).getString(KEY_FIRST_NAME));
            this.setEmail(json.getJSONObject(KEY_CONTACT).getString(KEY_EMAIL));
            this.setLastMessage(json.getJSONObject(KEY_MESSAGE));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Contact(int id, String lastname, String firstname, String email,
                   ArrayList<Message> messages) {
        super();
        this._id = id;
        this._lastname = lastname;
        this._firstname = firstname;
        this._email = email;
        this._messages = messages;
    }

    public static ArrayList<Contact> fromJSON(JSONArray arr) {
        ArrayList<Contact> contacts = new ArrayList<Contact>();

        for (int i = 0; i < arr.length(); i++) {
            try {
                contacts.add(new Contact(arr.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return contacts;
    }

    public int getId() {
        return _id;
    }

    public Contact setId(int id) {
        this._id = id;
        return this;
    }

    public String getLastname() {
        return _lastname;
    }

    public Contact setLastname(String lastname) {
        this._lastname = lastname;
        return this;
    }

    public String getFirstname() {
        return _firstname;
    }

    public Contact setFirstname(String firstname) {
        this._firstname = firstname;
        return this;
    }

    public String getEmail() {
        return _email;
    }

    public Contact setEmail(String email) {
        this._email = email;
        return this;
    }

    public ArrayList<Message> getMessages() {
        if (this._messages == null) {
            this._messages = new ArrayList<Message>();
        }
        return _messages;
    }

    public Contact setMessages(ArrayList<Message> messages) {
        this._messages = messages;
        return this;
    }

    public Contact setLastMessage(JSONObject json) {
        Message lastMessage = null;

        try {
            lastMessage = new Message(
                    json.getString("message"),
                    json.getString("date"),
                    this,
                    json.getBoolean("sent"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.getMessages().add(this.getMessages().size() == 0 ? 0 : this.getMessages().size() - 1, lastMessage);

        return this;
    }

    @Override
    public String toString() {
        return this._id + " " +
                this._firstname + " " +
                this._lastname + " " +
                this._email + " " +
                this._messages.toString();
    }

    public CharSequence getFullname() {
        return this.getFirstname() + " " + this.getLastname().toUpperCase(Locale.FRANCE);
    }

    public Message getLastmessage() {
        return this.getMessages().get(this.getMessages().size() - 1);
    }

}