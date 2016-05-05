package hari.oassignment.models;

import android.graphics.Bitmap;

/**
 * Created by hpochiraju on 03/05/16.
 */
public class ThumbNail {
    private String source;
    private int width;
    private int height;

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getSource() {
        return source;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
