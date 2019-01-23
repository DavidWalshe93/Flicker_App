package com.example.david.flickr_viewer_app;

import android.os.AsyncTask;

//David Walshe
//23/01/2019



class GetRawData extends AsyncTask<String, Void, String> {

    private DownloadStatus downloadStatus;

    public GetRawData(DownloadStatus downloadStatus) {
        this.downloadStatus = DownloadStatus.IDLE;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... strings) {
        return null;
    }
}
