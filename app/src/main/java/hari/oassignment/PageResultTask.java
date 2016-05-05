package hari.oassignment;

import android.os.AsyncTask;

import java.net.URL;

import hari.oassignment.utils.NetworkUtils;


/**
 * Created by hpochiraju on 02/05/16.
 */
public class PageResultTask extends AsyncTask<URL,Integer,String> {

    private SearchResultCallback searchResultCallback;

    public PageResultTask(SearchResultCallback callback) {
        this.searchResultCallback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(URL... params) {
        //firing service call request
        String response = NetworkUtils.makeServiceCall(params[0]);
        return response;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        // Callback
        searchResultCallback.onPostExecute(result);
    }
}
