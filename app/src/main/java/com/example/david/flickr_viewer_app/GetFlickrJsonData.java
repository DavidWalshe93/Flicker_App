package com.example.david.flickr_viewer_app;

//David Walshe
//28/01/2019

import android.net.Uri;
import android.util.Log;

import java.util.List;

class GetFlickrJsonData implements GetRawData.OnDownloadComplete {
    private static final String TAG = "GetFlickrJsonData";

    private List<Photo> photoList = null;
    private String baseURL;
    private final OnDataAvailable callBack;
    private String language;
    private boolean matchAll;

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {

    }

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
}
