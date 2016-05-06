package hari.oassignment;

import android.content.ContentValues;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import hari.oassignment.models.SearchResult;
import hari.oassignment.utils.Constants;
import hari.oassignment.utils.NetworkUtils;

public class SearchActivity extends BaseActivity implements SearchResultCallback{
    private ListView mListView;
    private EditText mSearchText;
    private SearchAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //initialise views
        initViews();
    }

    private void initViews() {
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.GONE);
        mSearchText = (EditText) findViewById(R.id.search);
        mListView = (ListView) findViewById(R.id.list_view);
        // To notify text change in edit text
        mSearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()!=0){
                    ContentValues values = new ContentValues();
                    values.put(Constants.GPS_SEARCH,s.toString());

                    //Firing Search Request
                    fireRequest(values, SearchActivity.this);
                } else {
                    // remove data from list
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //Clear data after 3000ms
                            mAdapter.clearData();
                        }
                    }, 3000);
                }
            }
        });
    }
    /**
     * Method to update or set data to listview adapter
     * @param searchResults list of parsed data that has to be displayed on screen
     * */
    private void loadDataOnScreen(ArrayList<SearchResult> searchResults){
        //Set Adapter if it is not set
        if(mListView.getAdapter() == null) {
            mAdapter = new SearchAdapter(searchResults, this);
            mListView.setAdapter(mAdapter);
        } else {
            //Update data in adapter if a adapter is existing
            mAdapter.updateData(searchResults);
        }

    }
    /***
     * Call back method to get response back to Activity
     * @param response unparsed string response from server
     * */
    @Override
    public void onPostExecute(String response) {
        //Parsing data
        ArrayList<SearchResult> searchResults = NetworkUtils.parse(response);
        loadDataOnScreen(searchResults);

        mProgressBar.setVisibility(View.GONE);
    }
}
