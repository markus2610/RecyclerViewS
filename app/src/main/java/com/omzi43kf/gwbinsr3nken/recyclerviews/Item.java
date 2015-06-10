package com.omzi43kf.gwbinsr3nken.recyclerviews;

/**
 * Created by lgaji on 2015/06/08.
 */
public class Item {

    private String mMainText;
    private String mSubText;
    private int mStyle;
    private int mUniqueId;
    private int mPosition;

    {
        mStyle = ItemAdapter.NORMAL_LIST_ITEM;
    }

    public Item(String mainText, String subText, int uniqueId) {
        mMainText = mainText;
        mSubText = subText;

        mUniqueId = uniqueId;
    }


    public String getMainText() {
        return mMainText;
    }

    public void setMainText(String mainText) {
        mMainText = mainText;
    }

    public String getSubText() {
        return mSubText;
    }

    public void setSubText(String subText) {
        mSubText = subText;
    }

    public int getStyle() {
        return mStyle;
    }

    public void setStyle(int style) {
        mStyle = style;
    }

    public int getUniqueId() {
        return mUniqueId;
    }

    public void setUniqueId(int uniqueId) {
        mUniqueId = uniqueId;
    }


    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        mPosition = position;
    }
}
