package com.example.david.flickr_viewer_app;

//David Walshe
//28/01/2019

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class GetFlickrJsonData implements GetRawData.OnDownloadComplete {
    private static final String TAG = "GetFlickrJsonData";

    private List<Photo> photoList = null;
    private String baseURL;
    private final OnDataAvailable callBack;
    private String language;
    private boolean matchAll;

    interface OnDataAvailable {
        void onDataAvailable(List<Photo> data, DownloadStatus status);
    }

    public GetFlickrJsonData(String baseURL, String language, boolean matchAll, OnDataAvailable callBack) {
        this.baseURL = baseURL;
        this.language = language;
        this.matchAll = matchAll;
        this.callBack = callBack;
    }

    void executeOnSameThread(String searchCriteria) {
        Log.d(TAG, "executeOnSameThread: starts");
        String destinationUrl = createUrl(searchCriteria, language, matchAll);

        GetRawData getRawData = new GetRawData(this);
        getRawData.execute(destinationUrl);
        Log.d(TAG, "executeOnSameThread: ends");
    }

    private String createUrl(String searchCritiria, String lang, boolean matchAll) {
        Log.d(TAG, "createUrl: starts");

        return Uri.parse(baseURL).buildUpon()
                .appendQueryParameter("tags", searchCritiria)
                .appendQueryParameter("tagMode", matchAll ? "ALL" : "ANY")
                .appendQueryParameter("lang", lang)
                .appendQueryParameter("format", "json")
                .appendQueryParameter("nojsoncallback", "1")
                .build().toString();
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        Log.d(TAG, "onDownloadComplete: starts. Status = " + status);

        if (status == DownloadStatus.OK) {
            photoList = new ArrayList<>();

            try {
                JSONObject jsonData = new JSONObject(data);
                JSONArray itemsArray = jsonData.getJSONArray("items");

                for (int i = 0; i < itemsArray.length(); i++) {
                    JSONObject jsonPhoto = itemsArray.getJSONObject(i);
                    String title = jsonPhoto.getString("title");
                    String author = jsonPhoto.getString("author");
                    String authorId = jsonPhoto.getString("author_id");
                    String tags = jsonPhoto.getString("tags");

                    JSONObject jsonMedia = jsonPhoto.getJSONObject("media");
                    String photoUrl = jsonMedia.getString("m");

                    String link = photoUrl.replaceFirst("_m.", "_b.");
                }
            } catch (JSONException e) {
                Log.e(TAG, "onDownloadComplete: JSON error: " + e.getMessage());
            }
        }
    }

}
