package com.example.a2017.mentoring.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a2017.mentoring.Model.MenteeList;
import com.example.a2017.mentoring.R;
import com.example.a2017.mentoring.RecyclerAdapters.MenteeAdapter;
import com.example.a2017.mentoring.RecyclerTools.RecyclerTouchListner;
import com.example.a2017.mentoring.RetrofitApi.ApiClientRetrofit;
import com.example.a2017.mentoring.RetrofitApi.ApiInterfaceRetrofit;
import com.example.a2017.mentoring.Utils.Preferences;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by android on 10/03/2017.
 */

public class MenteeListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private ArrayList<MenteeList> menteelist;
    private MenteeAdapter menteeAdapter;
    private int userid;
    private int flag;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        userid= Preferences.myId(getContext());
        flag = getArguments().getInt("flag");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mentee_list_main, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_mentee);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.contact_refresh);
        configureRecyclerView();
        configureSwipeRefreshLayout();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void configureRecyclerView()
    {
        Log.d("Size:", "config...");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        menteeAdapter= new MenteeAdapter(getContext());
        menteelist= new ArrayList<MenteeList>();
        recyclerView.setAdapter(menteeAdapter);
        menteeAdapter.updateDataSet(menteelist);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListner(getContext(), recyclerView, new RecyclerTouchListner.IclickListner() {
            @Override
            public void onClick(View view, int position)
            {
                int userID= menteelist.get(position).getUserId();
                if(flag==0)
                {
                    goToMenteeProfile(userID);
                }
                else if(flag==2)
                {
                    goToMeetingRequest(userID);
                }
                else
                {
                    goToMeetingFragment(userID);
                }
            }
            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    private void configureSwipeRefreshLayout()
    {
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setRefreshing(true);
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                getDataFromServer();
            }
        });
    }

    @Override
    public void onRefresh()
    {
    getDataFromServer();
    }

    private void getDataFromServer()
    {
        ApiInterfaceRetrofit retrofit = ApiClientRetrofit.getClient().create(ApiInterfaceRetrofit.class);
        Call<ArrayList<MenteeList>> menteeListCall = retrofit.getMenteeList(userid);
        Log.d("Size:", String.valueOf(userid));
        menteeListCall.enqueue(new Callback<ArrayList<MenteeList>>() {
            @Override
            public void onResponse(Call<ArrayList<MenteeList>> call, Response<ArrayList<MenteeList>> response)
            {
                if (response.code()==200||response.code()==204)
                {
                 menteelist= response.body();
                    refreshLayout.setRefreshing(false);
                    menteeAdapter.updateDataSet(menteelist);
                    Log.d("Size:", "1");

                }
                else
                {
                    refreshLayout.setRefreshing(false);
                    Log.d("Size:", "2");

                }
            }

            @Override
            public void onFailure(Call<ArrayList<MenteeList>> call, Throwable t)
            {
                refreshLayout.setRefreshing(false);
                Log.d("Size:", "3");
                t.printStackTrace();

            }
        });
    }
    private void goToMenteeProfile(int userID)
    {
        fragmentManager = getFragmentManager();
        transaction = fragmentManager.beginTransaction();
        Bundle bundle= new Bundle();
        bundle.putInt("userId",userID);
        MenteeProfileFragment menteeProfile = new MenteeProfileFragment();
        menteeProfile.setArguments(bundle);
        transaction.replace(R.id.fragment_container, menteeProfile,"MENTEE_PROFILE");
        transaction.commit();
    }

    private void goToMeetingFragment(int menteeId)
    {
        fragmentManager = getFragmentManager();
        transaction = fragmentManager.beginTransaction();
        Bundle bundle= new Bundle();
        bundle.putInt("menteeId",menteeId);
        MeetingFragment meetingFragment = new MeetingFragment();
        meetingFragment.setArguments(bundle);
        transaction.replace(R.id.fragment_container, meetingFragment,"MEETING_FRAGMENT");
        transaction.commit();
    }

    private void goToMeetingRequest(int menteeId)
    {
        fragmentManager = getFragmentManager();
        transaction = fragmentManager.beginTransaction();
        Bundle bundle= new Bundle();
        bundle.putInt("menteeId",menteeId);
        RequestFragment requestFragment = new RequestFragment();
        requestFragment.setArguments(bundle);
        transaction.replace(R.id.fragment_container, requestFragment,"REQUEST_MEETING");
        transaction.commit();
    }
}
