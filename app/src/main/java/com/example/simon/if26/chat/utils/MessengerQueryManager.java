package com.example.simon.if26.chat.utils;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MessengerQueryManager {

    private static final String BASE_URL = "http://train.sandbox.eutech-ssii.com/messenger/";
    private static final String LOGIN_URL = "login.php?email={{login}}&password={{password}}";
    private static final String CONTACTS_URL = "contacts.php?token={{token}}";
    private static final String MESSAGES_URL = "messages.php?token={{token}}&contact={{contact_id}}";
    private static final String NEW_MESSAGE_URL = "message.php?token={{token}}&contact={{contact_id}}&message={{message}}";

    private static JSONObject getJSONFromURL(String url) {
        HttpClient client = new DefaultHttpClient();
        HttpGet req = new HttpGet(url);
        req.setHeader("Content-type", "application/json");

        String result = null;
        JSONObject json = null;
        InputStream inputStream = null;
        BufferedReader reader = null;
        StringBuilder sb = null;
        String line = null;

        try {
            inputStream = client.execute(req).getEntity().getContent();
            reader = new BufferedReader(new InputStreamReader(inputStream,
                    "UTF-8"), 8);

            sb = new StringBuilder();
            line = "";

            while ((line = reader.readLine()) != null) {
                sb.append(line + '\n');
            }

            inputStream.close();
            result = sb.toString();

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            json = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    public static JSONObject queryLogin(String login, String password) {
        String url = BASE_URL + LOGIN_URL
                .replace("{{login}}", login)
                .replace("{{password}}", password);

        return getJSONFromURL(url);
    }

    public static JSONObject queryContacts(String token) {
        String url = BASE_URL + CONTACTS_URL
                .replace("{{token}}", token);
        return getJSONFromURL(url);
    }

    public static JSONObject queryMessages(String token, String id) {
        String url = BASE_URL + MESSAGES_URL
                .replace("{{token}}", token)
                .replace("{{contact_id}}", id);

        return getJSONFromURL(url);
    }

    public static JSONObject queryNewMessage(String token, String id, String message) {
        String url = BASE_URL + NEW_MESSAGE_URL
                .replace("{{token}}", token)
                .replace("{{contact_id}}", id)
                .replace("{{message}}", message);

        return getJSONFromURL(url);
    }
}
