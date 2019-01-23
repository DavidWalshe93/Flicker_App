package com.example.david.flickr_viewer_app;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//David Walshe
//23/01/2019



class GetRawData extends AsyncTask<String, Void, String> {
    private static final String TAG = "GetRawData";
    private DownloadStatus downloadStatus;
    private final OnDownloadComplete callback;

    public GetRawData(OnDownloadComplete callback) {
        this.downloadStatus = DownloadStatus.IDLE;
        this.callback = callback;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, "GetRawData: paramater = " + s);
        if (callback != null) {
            callback.onDownloadComplete(s, downloadStatus);
        }
        Log.d(TAG, "onPostExecute: end");
    }

    interface OnDownloadComplete {
        void onDownloadComplete(String data, DownloadStatus status);
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection connection = null;
        BufferedReader br = null;

        if(strings == null) {
            downloadStatus = DownloadStatus.NOT_INITIALISED;
            return null;
        }

        try {
            downloadStatus = DownloadStatus.PROCESSING;
            URL url = new URL(strings[0]);

            // Connect to the URL to download data.
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int response = connection.getResponseCode();
            Log.d(TAG, "doInBackground: The response code was " + response);

            StringBuilder result = new StringBuilder();

            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            // Reading in data from the connection, one line at a time.
            for(String line = br.readLine(); line != null; line = br.readLine()) {
                result.append(line).append("\n");       // Append \n as readLine removes them.
            }

            downloadStatus = DownloadStatus.OK;
            return result.toString();

        } catch (MalformedURLException e){
            Log.e(TAG, "doInBackground: Invalid URL " + e.getMessage());
        } catch (IOException e){
            Log.e(TAG, "doInBackground: IO Exception " + e.getMessage());
        } catch (SecurityException e) {
            Log.e(TAG, "doInBackground: Security Exception. Needs permission " + e.getMessage());
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
            if( br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    Log.e(TAG, "doInBackground: IO Exception " + e.getMessage());
                }
            }
        }
        return null;
    }
}
