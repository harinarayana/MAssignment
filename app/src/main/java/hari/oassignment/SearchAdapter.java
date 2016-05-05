package hari.oassignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import hari.oassignment.models.SearchResult;

/**
 * Created by hpochiraju on 03/05/16.
 */
public class SearchAdapter extends BaseAdapter {
    private ArrayList<SearchResult> mSearchResults;
    private static LayoutInflater inflater;
    private ImageLoader mImageLoader;
    private DisplayImageOptions options;

    public SearchAdapter(ArrayList<SearchResult> searchResults, Context context) {
        this.mSearchResults = searchResults;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mImageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher)
                .showImageOnLoading(R.drawable.ic_launcher).build();

    }

    /**
     * method to update data in adapter
     * @param searchResults - parsed data list
     * */
    public void updateData(ArrayList<SearchResult> searchResults){
        this.mSearchResults = searchResults;
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

    @Override
    public int getCount() {
        return (mSearchResults != null) ? mSearchResults.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.row,null);
            holder = new ViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String url = mSearchResults.get(position).getThumbNail()!=null? mSearchResults.get(position).getThumbNail().getSource():"";
        mImageLoader.displayImage(url,holder.icon, options);
        holder.title.setText(mSearchResults.get(position).getmTitle());
        return convertView;
    }

    public static class ViewHolder{
        public TextView title;
        public ImageView icon;
    }
}
