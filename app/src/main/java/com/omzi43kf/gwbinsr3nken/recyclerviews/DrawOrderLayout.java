package com.omzi43kf.gwbinsr3nken.recyclerviews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

/**
 * Created by lgaji on 2015/06/09.
 */
public class DrawOrderLayout extends RelativeLayout{
    public DrawOrderLayout(Context context) {
        super(context);
    }

    public DrawOrderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawOrderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
//        setChildrenDrawingOrderEnabled(true);

        return super.getChildDrawingOrder(childCount, i);
//        if (i == 0) {
//            return 1;
//        } else if (i == 1) {
//            return 0;
//        }
//        return super.getChildDrawingOrder(childCount,i);



    }


}
