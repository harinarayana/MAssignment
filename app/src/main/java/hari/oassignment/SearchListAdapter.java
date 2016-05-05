package hari.oassignment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import hari.oassignment.models.SearchResult;

/**
 * Created by hpochiraju on 03/05/16.
 */
public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder> {
    private ArrayList<SearchResult> mSearchResults;
    private ImageLoader mImageLoader;
    private DisplayImageOptions options;
    private int lastPosition = -1;
    private Context mContext;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View mPageItem;
        public ImageView mImageView;
        public TextView mPageTitle;
        public ViewHolder(View v) {
            super(v);
            mPageItem = v;
            mImageView = (ImageView) v.findViewById(R.id.page_item);
            mPageTitle = (TextView) v.findViewById(R.id.page_title);
        }
    }

    public SearchListAdapter(ArrayList<SearchResult> searchResults, Context context){
        this.mContext = context;
        this.mSearchResults = searchResults;
        this.mImageLoader = ImageLoader.getInstance();
        this.options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher)
                .showImageOnLoading(R.drawable.ic_launcher).build();
    }

    @Override
    public SearchListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_result_page_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        String uri = mSearchResults.get(position).getThumbNail()!=null ? mSearchResults.get(position).getThumbNail().getSource():"";
        mImageLoader.displayImage(uri,holder.mImageView, options);
        //animateRow(holder.mPageItem, position);
        holder.mPageTitle.setText(mSearchResults.get(position).getmTitle());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return (mSearchResults != null) ? mSearchResults.size() : 0;
    }

    /**
     * method to update data in adapter
     * @param searchResults - parsed data list
     * */
    public void updateData(ArrayList<SearchResult> searchResults) {
        this.mSearchResults = searchResults;
        //notifying the change in data-set
        notifyDataSetChanged();
    }

    /**
     * method to clear data in adapter
     * */
    public void clearData() {
        this.mSearchResults.clear();
        //notifying the change in data-set
        notifyDataSetChanged();
    }

   private void animateRow(View viewToBeAnimated, int position) {
        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        viewToBeAnimated.startAnimation(animation);
        lastPosition = position;

    }


}
