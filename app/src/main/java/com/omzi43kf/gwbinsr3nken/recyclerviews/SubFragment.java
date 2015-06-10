package com.omzi43kf.gwbinsr3nken.recyclerviews;


import android.content.ContentValues;
import android.graphics.Canvas;
import android.os.Bundle;

import android.provider.CallLog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SubFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Item>> {

    private static final String LOG_TAG = SubFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ItemAdapter mItemAdapter;
    private List<Item> mItemList;
    private ItemTouchHelper mItemTouchHelper;
    public ItemTouchHelper.Callback mCallback;
    private View mEmptyView;
    private int LOADER_ID = 10;
    private FloatingActionButton mFloatingActionButton;
    private ImageButton mLoaderStartButton;

    public SubFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sub, container, false);


        mLoaderStartButton = (ImageButton) view.findViewById(R.id.loader_start_button);
        mLoaderStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaderStart();
            }
        });

        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_sub_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mEmptyView = view.findViewById(R.id.fragment_sub_empty_view);

        mItemAdapter = createItemAdapter();

        mRecyclerView.setAdapter(mItemAdapter);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

//        mItemTouchHelper = createItemTouchHelper();
        mItemTouchHelper = createItemTouchHelper();
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

        mFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.fragment_sub_fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterSize = mItemAdapter.getItemCount();
                final ExtendAsyncQueryHandler extendAsyncQueryHandler = new ExtendAsyncQueryHandler(getActivity().getContentResolver());

                ContentValues contentValues = new ContentValues();
                contentValues.put(DataContract.ItemEntry.COLUMN_ITEM_MAIN_TEXT, "newly added item");
                contentValues.put(DataContract.ItemEntry.COLUMN_ITEM_SUB_TEXT, "newly added sub item");
                contentValues.put(DataContract.ItemEntry.COLUMN_ITEM_POSITION, adapterSize);
                contentValues.put(DataContract.ItemEntry.COLUMN_ITEM_STYLE, 100);
                extendAsyncQueryHandler.startInsert(0, null, DataContract.ItemEntry.CONTENT_URI, contentValues);

                loaderStart();
            }
        });


        return view;
    }

    private void loaderStart() {
        getLoaderManager().initLoader(LOADER_ID++, null, this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private ItemTouchHelper createItemTouchHelper() {
        mCallback = crateCallback();
        return new ItemTouchHelper(mCallback);
    }

    private ItemTouchHelper.Callback crateCallback() {
        return new ExtendItemTouchHelperCallback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if (viewHolder instanceof ItemAdapter.ViewHolder) {
                    int position = viewHolder.getAdapterPosition();


//                    Log.d(LOG_TAG, "movement frag: position: " + position);

                }

                return makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) | makeFlag(ItemTouchHelper.ACTION_STATE_DRAG, ItemTouchHelper.UP | ItemTouchHelper.DOWN);

            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                final int fromPos = viewHolder.getAdapterPosition();
                final int toPos = target.getAdapterPosition();

                Log.d(LOG_TAG, " postion " + "From: " + fromPos + " TO: " + toPos);
                mItemAdapter.move(fromPos, toPos);


                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
                Item item = mItemAdapter.remove(viewHolder.getAdapterPosition());
                int removedId = item.getUniqueId();

//                Snackbar.make(mRecyclerView, "Item removed, position: " + viewHolder.getAdapterPosition(), Snackbar.LENGTH_SHORT).show();
                final ExtendAsyncQueryHandler extendAsyncQueryHandler = new ExtendAsyncQueryHandler(getActivity().getContentResolver());
                extendAsyncQueryHandler.startDelete(removedId, null, DataContract.ItemEntry.buildItemUri(removedId), null, null);

                Snackbar.make(mRecyclerView, "Item removed, position: " + removedId, Snackbar.LENGTH_SHORT).setAction(getResources().getString(R.string.snack_bar_action), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();


                Log.d(LOG_TAG, "removed row; " + removedId);
//                ((ItemAdapter.ViewHolder) viewHolder).setLayoutVisibility(false);
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                getDefaultUIUtil().clearView(((ItemAdapter.ViewHolder) viewHolder).mFacadeLayout);
//                getDefaultUIUtil().clearView(((ItemAdapter.ViewHolder) viewHolder).itemView);
//                ((ItemAdapter.ViewHolder) viewHolder).setLayoutVisibility(false);
                ((ItemAdapter.ViewHolder) viewHolder).setLayoutVisibility(ItemAdapter.SWIPE_END);


            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if (actionState == ItemTouchHelper.ACTION_STATE_IDLE) {
                    Log.d(LOG_TAG, "IDLE");
                }


                if (viewHolder != null) {
                    if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                        getDefaultUIUtil().onSelected(((ItemAdapter.ViewHolder) viewHolder).mFacadeLayout);
//                        ((ItemAdapter.ViewHolder) viewHolder).setLayoutVisibility(true);

                    } else if (actionState == ItemTouchHelper.ACTION_STATE_IDLE) {

                        getDefaultUIUtil().onSelected(((ItemAdapter.ViewHolder) viewHolder).itemView);
                    } else {
                        getDefaultUIUtil().onSelected(((ItemAdapter.ViewHolder) viewHolder).itemView);
                    }


                }
            }


            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

//                Log.d(LOG_TAG, "dx: " + dX + " dy: " + dY);


                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    float swipingDx = dX;
                    String swipingDirection;
                    if (swipingDx > 0) {
                        swipingDirection = ItemAdapter.SWIPING_RIGHT;
                    } else if (swipingDx < 0) {
                        swipingDirection = ItemAdapter.SWIPING_LEFT;
                    } else {
                        swipingDirection = ItemAdapter.SWIPE_END;
                    }
//                    String swipingDirection = swipingDx > 0 ? ItemAdapter.SWIPING_RIGHT :ItemAdapter.SWIPING_LEFT;


                    getDefaultUIUtil().onDraw(c, recyclerView,
                            ((ItemAdapter.ViewHolder) viewHolder).mFacadeLayout, dX, dY,
                            actionState, isCurrentlyActive);
                    ((ItemAdapter.ViewHolder) viewHolder).setLayoutVisibility(swipingDirection);

                } else {
                    getDefaultUIUtil().onDraw(c, recyclerView,
                            ((ItemAdapter.ViewHolder) viewHolder).itemView, dX, dY,
                            actionState, isCurrentlyActive);

                }


            }

            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    getDefaultUIUtil().onDrawOver(c, recyclerView,
                            ((ItemAdapter.ViewHolder) viewHolder).mFacadeLayout, dX, dY,
                            actionState, isCurrentlyActive);
                } else {
                    getDefaultUIUtil().onDraw(c, recyclerView,
                            ((ItemAdapter.ViewHolder) viewHolder).itemView, dX, dY,
                            actionState, isCurrentlyActive);
//                    ((ItemAdapter.ViewHolder) viewHolder).setLayoutVisibility(false);
                }
            }


        };


    }

    private ItemAdapter createItemAdapter() {
//        List<Item> itemList = createItemList();
        List<Item> itemList = null;
        ItemAdapter itemAdapter = new ItemAdapter(getActivity(), itemList, mEmptyView, new ItemAdapter.ItemAdapterOnClickListener() {
            @Override
            public void onClicked(int id) {
                Log.d(LOG_TAG, "item id: " + id);
            }
        });

        return itemAdapter;
    }

    private List<Item> createItemList() {
        List<Item> mItemList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Item item = new Item("Item:" + i, "Sub:" + i, i);
            mItemList.add(item);
        }
        return mItemList;

    }


    @Override
    public Loader<List<Item>> onCreateLoader(int id, Bundle args) {
//        return null;
//        return new ItemListLoader(getActivity(), getActivity().getContentResolver());
        return new ItemSearchListLoader(getActivity(),getActivity().getContentResolver(),"item1");
    }

    @Override
    public void onLoadFinished(Loader<List<Item>> loader, List<Item> itemList) {
        Log.d(LOG_TAG, "load finished");
        mItemAdapter.setData(itemList);
        Log.d(LOG_TAG, "Item count: " + mItemAdapter.getItemCount());
    }

    @Override
    public void onLoaderReset(Loader<List<Item>> loader) {
        mItemAdapter.setData(null);
    }
}
