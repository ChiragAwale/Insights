package com.chiragawale.folinsight.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.chiragawale.folinsight.adapter.UserAdapterRV;
import com.chiragawale.folinsight.keys.GlobalVar;
import com.chiragawale.folinsight.R;
import com.chiragawale.folinsight.entity.Users;
import com.chiragawale.folinsight.loader.UserLoaderLocal;

import java.util.List;

/**
 * Created by chira on 7/13/2017.
 */

public class MutualFragment extends Fragment implements LoaderManager.LoaderCallbacks <List<Users>>{
    //Adapter for providing data to the list view
    private UserAdapterRV mUserAdapter;

    //Loader Id for follower loader
    private final static int FOLLOWER_LOADER_ID = 1;
    private ProgressBar progressBar;
    private RecyclerView mUserList;
    private RelativeLayout emptyView;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_list, container, false);
        emptyView = (RelativeLayout) rootView.findViewById(R.id.empty_view);
        //Progress bar
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        //gets reference for the recycler view
        mUserList = (RecyclerView) rootView.findViewById(R.id.rv_user_list);

        //Defines the layout for the recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mUserList.setLayoutManager(layoutManager);

        //Improves performance
        mUserList.setHasFixedSize(true);
        //initializes the adapter with empty list
        mUserAdapter = new UserAdapterRV(0, null);
        //Sets the adapter for the Recycler view
        mUserList.setAdapter(mUserAdapter);
        //Kicks off the loader
        getLoaderManager().initLoader(FOLLOWER_LOADER_ID, null, this);
        mUserList.setVisibility(View.GONE);

        return rootView;
    }


    //When the loader is created
    @Override
    public Loader<List<Users>> onCreateLoader(int id, Bundle args) {
        progressBar.setVisibility(View.VISIBLE);
        return new UserLoaderLocal(getActivity(), GlobalVar.MUTUAL_FRAGMENT);
    }

    @Override
    public void onResume() {
        getLoaderManager().restartLoader(FOLLOWER_LOADER_ID,null,this);
        super.onResume();
    }

    //When the loader is done loading the data
    @Override
    public void onLoadFinished(Loader<List<Users>> loader, List<Users> data) {
        mUserAdapter = new UserAdapterRV(data.size(),data);
        mUserList.setAdapter(mUserAdapter);
        progressBar.setVisibility(View.INVISIBLE);
        if(data.size()!=0){
            mUserList.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
        Log.e("DATA SIZE",data.size() + "===================================================");
    }

    //When the loader is reset
    @Override
    public void onLoaderReset(Loader<List<Users>> loader) {
        Log.e("loader Reset","===================================================");
        mUserAdapter = new UserAdapterRV(0, null);
        mUserList.setAdapter(mUserAdapter);
    }

}