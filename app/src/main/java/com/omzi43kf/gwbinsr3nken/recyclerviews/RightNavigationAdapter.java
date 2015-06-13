package com.omzi43kf.gwbinsr3nken.recyclerviews;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lgaji on 2015/06/13.
 */
public class RightNavigationAdapter extends RecyclerView.Adapter<RightNavigationAdapter.ViewHolder> {

    private static final String LOG_TAG = RightNavigationAdapter.class.getSimpleName();
    private List<String> mNavigationItemList;
    private List<Integer> mNavigationIconList;
    private RightNavigationAdapterOnClickListener mOnClickListener;

    static final int NORMAL_LIST_ITEM = 1;
    static final int HEADER_LIST_ITEM = 2;


    public RightNavigationAdapter(List<String> navigationItemList, List<Integer> navigationIconList, RightNavigationAdapterOnClickListener onClickListener) {
        mNavigationItemList = navigationItemList;
        mNavigationIconList = navigationIconList;
        mOnClickListener = onClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mNavigationTextView;
        public CheckBox mNavigationCheckBox;
        public TextView mNavigationHeaderTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mNavigationTextView = (TextView) itemView.findViewById(R.id.navigation_texi_view);
            mNavigationCheckBox = (CheckBox) itemView.findViewById(R.id.navigation_check_box);
            mNavigationHeaderTextView = (TextView) itemView.findViewById(R.id.navigation_adapter_header_text);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {


            mNavigationCheckBox.toggle();
            if (mNavigationCheckBox.isChecked()) {
                int adapterPosition = getAdapterPosition();
                Log.d(LOG_TAG, "Is checked true");
                mOnClickListener.onClick(adapterPosition);
            } else {
                Log.d(LOG_TAG, "Is checked false");
            }






        }


    }

    public static interface RightNavigationAdapterOnClickListener {
        void onClick(int id);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if (parent instanceof RecyclerView) {
            int layoutId = -1;
            switch (viewType) {
                case NORMAL_LIST_ITEM:
                    layoutId = R.layout.navigation_adapter_list_item;
                    break;
                case HEADER_LIST_ITEM:
                    layoutId = R.layout.navigation_adapter_header_layout;
                    break;
            }

            View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
            view.setFocusable(true);

            return new ViewHolder(view);

        } else {
            throw new RuntimeException("Not bound to RecycelrView");
        }


    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case NORMAL_LIST_ITEM:
                holder.mNavigationTextView.setText(mNavigationItemList.get(position-1));
                break;
            case HEADER_LIST_ITEM:
                holder.mNavigationHeaderTextView.setText("filter");
                break;
        }



    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_LIST_ITEM;
        }  else {
            return NORMAL_LIST_ITEM;
        }

    }

    @Override
    public int getItemCount() {
        return mNavigationItemList.size() + 1;
    }
}
