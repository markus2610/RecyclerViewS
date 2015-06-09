package com.omzi43kf.gwbinsr3nken.recyclerviews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lgaji on 2015/06/09.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private static final String LOG_TAG = ItemAdapter.class.getSimpleName();
    private List<Item> mItemList;
    private Context mContext;
    private View mEmptyView;
    private ItemAdapterOnClickListener mItemAdapterOnClickListener;

    static final int NORMAL_LIST_ITEM = 1;
    static final int HEADER_LIST_ITEM = 2;

    public static final String SWIPING_RIGHT ="swiping_right";
    public static final String SWIPING_LEFT ="swiping_left";
    public static final String SWIPE_END = "swiping_end";

    private boolean isLayoutVisible = false;

    public ItemAdapter(Context context, List<Item> itemList, View emptyView, ItemAdapterOnClickListener itemAdapterOnClickListener) {
        mContext = context;
        mItemList = itemList;
        mEmptyView = emptyView;
        mItemAdapterOnClickListener = itemAdapterOnClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mMainText;
        public TextView mSubText;
        public ViewGroup mFacadeLayout;
        public ViewGroup mRearRightLayout;
        public ViewGroup mRearLeftLayout;


        public ViewHolder(View itemView) {
            super(itemView);

            mMainText = (TextView) itemView.findViewById(R.id.list_item_main_textview);
            mSubText = (TextView) itemView.findViewById(R.id.list_item_sub_textview);
            mFacadeLayout = (ViewGroup) itemView.findViewById(R.id.list_item_facade_layout);
            mRearRightLayout = (ViewGroup) itemView.findViewById(R.id.list_item_rear_right_layout);
            mRearLeftLayout = (ViewGroup) itemView.findViewById(R.id.list_item_rear_left_layout);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            int id = mItemList.get(adapterPosition).getUniqueId();
            mItemAdapterOnClickListener.onClicked(id);
        }


        public void setLayoutVisibility(String swipeDirection) {
//            if (!isLayoutVisible) {
//                mRearRightLayout.setVisibility(View.VISIBLE);
//                isLayoutVisible = true;
//            } else if (isLayoutVisible) {
//                mRearRightLayout.setVisibility(View.INVISIBLE);
//                isLayoutVisible = false;
//            }

            switch (swipeDirection) {
                case SWIPING_RIGHT:
                    Log.d(LOG_TAG, "Swipie right");
                    mRearRightLayout.setVisibility(View.VISIBLE);
                    mRearLeftLayout.setVisibility(View.INVISIBLE);
                    break;
                case SWIPING_LEFT:
                    Log.d(LOG_TAG, "Swipe left");

                    mRearRightLayout.setVisibility(View.INVISIBLE);
                    mRearLeftLayout.setVisibility(View.VISIBLE);
                    break;
                case SWIPE_END:
                    Log.d(LOG_TAG, "Swipe end");
                    mRearRightLayout.setVisibility(View.INVISIBLE);
                    mRearLeftLayout.setVisibility(View.INVISIBLE);
                    break;
            }

//            if (visible) {
//
//                Log.d(LOG_TAG, "VISIBLE TRUE" );
//                    mRearRightLayout.setVisibility(View.VISIBLE);
//
//
//
//            } else {
//                Log.d(LOG_TAG, "VISIBLE FALSE" );
//
//                mFacadeLayout.setVisibility(View.VISIBLE);
//                mRearRightLayout.setVisibility(View.INVISIBLE);
//
//            }

        }
    }


    public static interface ItemAdapterOnClickListener {
        public void onClicked(int id);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        if (viewGroup instanceof RecyclerView) {
            int layoutId = -1;
            switch (i) {
                case NORMAL_LIST_ITEM:
                    layoutId = R.layout.list_item_normal;
                    break;
//                case HEADER_LIST_ITEM:
//                    layoutId = R.layout.list_item_header;
//                    break;
            }
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false);
            view.setFocusable(true);
            return new ViewHolder(view);

        } else {
            throw new RuntimeException("Not bound to RecycelrView");
        }


//        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {


        switch (getItemViewType(i)) {
            case NORMAL_LIST_ITEM:

                break;
            case HEADER_LIST_ITEM:
                break;
        }


        viewHolder.mMainText.setText(mItemList.get(i).getMainText());
        viewHolder.mSubText.setText(mItemList.get(i).getSubText());
//        viewHolder.mFacadeLayout.setBackgroundResource(R.drawable.ripple_bg);

    }

    @Override
    public int getItemCount() {
        if (mItemList == null) return 0;
        return mItemList.size();
    }


    @Override
    public int getItemViewType(int position) {
//        return super.getItemViewType(position);

        return mItemList.get(position).getStyle();


    }

    public void setData(List<Item> itemList) {
        mItemList = itemList;
        notifyDataSetChanged();
        mEmptyView.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    public void add(Item item) {
        mItemList.add(0, item);
        notifyItemInserted(0);

        // Cotent resolver

    }

    public void move(int from, int to) {
        Item prevItem = mItemList.remove(from);
        mItemList.add(to > from ? to - 1 : to, prevItem);
        notifyItemMoved(from, to);

        // Cotent resolver

    }

    public int remove(int position) {
        Item item = mItemList.remove(position);

        notifyItemRemoved(position);
        return item.getUniqueId();

        // Cotent resolver
    }


}
