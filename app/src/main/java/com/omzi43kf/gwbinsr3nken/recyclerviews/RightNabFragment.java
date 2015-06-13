package com.omzi43kf.gwbinsr3nken.recyclerviews;


import android.os.Bundle;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RightNabFragment extends Fragment {
    private final static String LOG_TAG = RightNabFragment.class.getSimpleName();
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RightNavigationAdapter mRightNavigationAdapter;
    private List<String> mNavigationItemList;

    public RightNabFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_right_nab, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_right_nab__recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mNavigationItemList = new ArrayList<>();
        mNavigationItemList.add("hello");
        mNavigationItemList.add("hello");
        mNavigationItemList.add("hello");

        mRightNavigationAdapter = new RightNavigationAdapter(mNavigationItemList, new ArrayList<Integer>(), new RightNavigationAdapter.RightNavigationAdapterOnClickListener() {
            @Override
            public void onClick(int id) {
                Log.d(LOG_TAG, "clicked id;" + id);
            }
        });

        mRecyclerView.setAdapter(mRightNavigationAdapter);





        mToolbar = (Toolbar) view.findViewById(R.id.fragment_right_nav_tool_bar);
        mToolbar.inflateMenu(R.menu.right_nav_menu);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
//                return false;
                int id = item.getItemId();
                switch (id) {
                    case R.id.action_sort:
                        Log.d(LOG_TAG, "Sort clicked");
                        mDrawerLayout.openDrawer(Gravity.RIGHT);
                        return true;
                }
                return true;
            }

        });
        mDrawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout,
                R.string.open_drawer,
                R.string.clode_drawer) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

//        mNavigationView = (NavigationView) view.findViewById(R.id.navigation_view);

        return view;
    }


}
