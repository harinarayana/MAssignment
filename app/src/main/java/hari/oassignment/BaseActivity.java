package hari.oassignment;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;

import hari.oassignment.models.SearchRequest;
import hari.oassignment.models.SearchResult;
import hari.oassignment.utils.Constants;
import hari.oassignment.utils.NetworkUtils;

/**
 * Created by hpochiraju on 05/05/16.
 */
public class BaseActivity extends AppCompatActivity {
    protected ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initialising image loader
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        ImageLoader.getInstance().init(configuration);
    }

    protected void fireRequest(ContentValues values, SearchResultCallback callback) {
        if(NetworkUtils.isNetworkAvailable(this)) {
            values.put(Constants.ACTION,"query");
            values.put(Constants.PROP,"pageimages");
            values.put(Constants.FORMAT,"json");
            values.put(Constants.PIPROP,"thumbnail");
            values.put(Constants.PITHUMB_SIZE,"50");
            values.put(Constants.PILIMIT,"50");
            values.put(Constants.GENERATOR,"prefixsearch");
            mProgressBar.setVisibility(View.VISIBLE);
            SearchRequest searchRequest = new SearchRequest(this.getApplicationContext(), values);
            PageResultTask pageResultTask = new PageResultTask(callback);
            pageResultTask.execute(searchRequest.getRequest());
        } else {
            Toast.makeText(this, R.string.no_network, Toast.LENGTH_LONG).show();
        }
    }

    private void loadDataOnScreen(ArrayList<SearchResult> searchResults){

    }
}
