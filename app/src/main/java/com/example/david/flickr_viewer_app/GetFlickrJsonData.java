package com.example.david.flickr_viewer_app;

//David Walshe
//28/01/2019

import java.util.List;

class GetFlickrJsonData implements GetRawData.OnDownloadComplete {
    private static final String TAG = "GetFlickrJsonData";
    private final OnDataAvailable callBack;
    private List<Photo> photoList = null;
    private String baseURL;
    private String langauge;
    private boolean matahAll;

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {

    }

    interface OnDataAvailable {
        void onDataAvailable(List<Photo> data, DownloadStatus status);
    }
}
