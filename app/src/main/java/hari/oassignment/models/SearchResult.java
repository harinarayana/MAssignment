package hari.oassignment.models;

/**
 * Created by hpochiraju on 03/05/16.
 */
public class SearchResult {
    private ThumbNail thumbNail;
    private String mTitle;
    private int pageId;
    private int index;

    public int getIndex() {
        return index;
    }

    public int getPageId() {
        return pageId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public ThumbNail getThumbNail() {
        return thumbNail;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public void setThumbNail(ThumbNail thumbNail) {
        this.thumbNail = thumbNail;
    }
}
