package hari.oassignment.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

import javax.net.ssl.X509TrustManager;

import hari.oassignment.models.SearchResult;
import hari.oassignment.models.ThumbNail;

/**
 * Created by hpochiraju on 02/05/16.
 */
public class NetworkUtils {
    /**
     * method to make a request to web-server
     * @param url - web-service url
     * */
    public static String makeServiceCall(URL url) {
        String result = null;
        HttpURLConnection http = null;
        InputStream in;
        try{
        if (url.getProtocol().toLowerCase().equals("https")) {
            trustAllHosts();
            try {
                HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
                https.setHostnameVerifier(DO_NOT_VERIFY);
                http = https;
                in = https.getInputStream();
                result = convertStreamToString(in);
            } catch (java.net.SocketTimeoutException e) {
                e.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                http = (HttpURLConnection) url.openConnection();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Log.i("Result","***************************************");
        //Log.i("Result",result);
        //Log.i("Result","***************************************");
        return result;
    }

    /**
     * method to make a Image download request to web-server
     * @param url - web-service url
     * */

    public static Bitmap makeBitmapServiceCall(URL url) {
        Bitmap result = null;
        HttpURLConnection http = null;
        InputStream in;

        if (url!=null && url.getProtocol().toLowerCase().equals("https")) {
            trustAllHosts();
            try {
                HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
                https.setHostnameVerifier(DO_NOT_VERIFY);
                http = https;
                in = https.getInputStream();
                //result = convertStreamToString(in);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(in);

                result = BitmapFactory.decodeStream(bufferedInputStream);

            } catch (java.net.SocketTimeoutException e) {
                e.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } else {
            try {
                http = (HttpURLConnection) url.openConnection();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return result;
    }
    /**
     * method to convert input stream into String response
     * @param is - Input stream data that we received from server
     * */
    public static String convertStreamToString(InputStream is){
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();

    }
    /**
     * method to parse json respone
     * @param response - un-parsed string response
     * */
    public static ArrayList<SearchResult> parse(String response){
        ArrayList<SearchResult> searchResults = new ArrayList<>();
        try {
            JSONObject result = new JSONObject(response);
            JSONObject queryObj = result.optJSONObject(Constants.QUERY);
            if(queryObj == null) {
                return searchResults;
            }
            JSONObject pages = queryObj.optJSONObject(Constants.PAGES);
            Iterator<String> keys= pages.keys();
            while (keys.hasNext()) {
                String keyValue = (String) keys.next();
                JSONObject page = pages.optJSONObject(keyValue);
                SearchResult searchResult = new SearchResult();
                searchResult.setPageId(page.optInt(Constants.PAGE_ID));
                searchResult.setmTitle(page.optString(Constants.TITLE));
                searchResult.setIndex(page.optInt(Constants.INDEX));
                JSONObject thumbnailObj = page.optJSONObject(Constants.THUMBNAIL);
                ThumbNail thumbNail = new ThumbNail();
                if(thumbnailObj!=null) {
                    thumbNail.setHeight(thumbnailObj.optInt(Constants.HEIGHT));
                    thumbNail.setWidth(thumbnailObj.optInt(Constants.WIDTH));
                    thumbNail.setSource(thumbnailObj.optString(Constants.SOURCE));
                    searchResult.setThumbNail(thumbNail);
                }

                searchResults.add(searchResult);
            }
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return searchResults;
    }

    /**
     * Trust every server - dont check for any certificate
     */
    private static void trustAllHosts() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[] {};
            }

            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }
        } };

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };


    /**
     * method to check availability of internet connection
     * @param context - activity context
     * */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
