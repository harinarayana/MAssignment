package hari.oassignment;

import android.content.ContentValues;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;


import hari.oassignment.models.SearchResult;
import hari.oassignment.utils.Constants;
import hari.oassignment.utils.NetworkUtils;

public class OAssignmentActivity extends BaseActivity implements SearchResultCallback{
    private RecyclerView mRecyclerView;
    private SearchListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private EditText mSearchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oassignment);
        // Initializing all views
        initviews();
    }
    /**
     *
     * **/
    private void initviews(){
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mSearchText = (EditText) findViewById(R.id.search);
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
                    fireRequest(values, OAssignmentActivity.this);
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

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);//, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }
    /**
     * Method to update or set data to recyclerview adapter
     * @param searchResults list of parsed data that has to be displayed on screen
     * */
    private void loadDataOnScreen(ArrayList<SearchResult> searchResults){
        //Set Adapter if it is not set
        if (mRecyclerView.getAdapter() == null) {
            mAdapter = new SearchListAdapter(searchResults, this);
            mRecyclerView.setAdapter(mAdapter);
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
        // hiding the progress bar after completing the task
        mProgressBar.setVisibility(View.GONE);
    }
}
