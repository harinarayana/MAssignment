package hari.oassignment.models;

import android.content.ContentValues;
import android.content.Context;
import android.util.Pair;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import hari.oassignment.R;

/**
 * Created by hpochiraju on 03/05/16.
 */
public class SearchRequest {
    Context mContext;
    ContentValues mParams;

    public SearchRequest(Context context, ContentValues params){
        this.mContext = context;
        this.mParams = params;
    }

    public URL getRequest(){
        URL url = null;
        StringBuilder uri = new StringBuilder( mContext.getString(R.string.api_base_url));

        for(Map.Entry<String,Object> pair: mParams.valueSet()) {
            uri.append( pair.getKey() + "=" + pair.getValue().toString()+"&");
        }

        try{
            url = new URL(uri.toString());
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        }
        return url;
    }

}
