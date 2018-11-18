package com.ibar.protectme;

import android.os.AsyncTask;
import android.os.Build;
import android.text.Html;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpPostAsyncTask extends AsyncTask<String, Void, Void> {


        // This is the JSON body of the post
        JSONObject postData;
        LoginAct loginact;
        // This is a constructor that allows you to pass in the JSON body
        public HttpPostAsyncTask(Map<String, String> postData, LoginAct loginact) {
            if (postData != null) {
                this.postData = new JSONObject(postData);
            }
            this.loginact = loginact;
        }

        // This is a function that we are overriding from AsyncTask. It takes Strings as parameters because that is what we defined for the parameters of our async task
        @Override
        protected Void doInBackground(String... params) {

            try {
                // This is getting the url from the string we passed in
                URL url = new URL("http://" + Config.SERVER_IP + "/" + params[0]);

                // Create the urlConnection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();


                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestMethod("POST");

                // Send the post body
                if (this.postData != null) {
                    OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                    writer.write(postData.toString());
                    writer.flush();
                }

                int statusCode = urlConnection.getResponseCode();
                Log.d("BRUH Status: ", String.valueOf(statusCode));
                if (statusCode ==  200) {

                    InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

                    String response = convertInputStreamToString(inputStream);

                    Log.d("BRUH Response: ", response);

                    JSONObject json;

                    if (Build.VERSION.SDK_INT >= 24)
                    {
                        String text = Html.fromHtml(response, Html.FROM_HTML_MODE_LEGACY).toString();
                        json = new JSONObject(text);
                    }
                    else
                    {
                        json = new JSONObject(Html.fromHtml(response).toString());
                    }

                    if(json.has("errors")) {
                        loginact.onLoginFailed();
                        return null;

                    }

                    String sessionId = json.get("sessionid").toString();

                    loginact.onLoginSuccess(sessionId);

                } else {
                    loginact.onLoginFailed();
                    // Do something to handle the error
                }

            } catch (Exception e) {
                Log.d("BRUH", e.getLocalizedMessage());
            }
            return null;
        }


    private String convertInputStreamToString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}