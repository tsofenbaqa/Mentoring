package com.example.a2017.mentoring.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.a2017.mentoring.Model.Request;
import com.example.a2017.mentoring.R;
import com.example.a2017.mentoring.RecyclerAdapters.RequestsAdapter;
import com.example.a2017.mentoring.RetrofitApi.ApiClientRetrofit;
import com.example.a2017.mentoring.RetrofitApi.ApiInterfaceRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 2017 on 15/02/2017.
 */

public class MeetingFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout refreshLayout;
    RequestsAdapter mAdapter;
    List<Request> meetingList = new ArrayList<Request>();
    public  RecyclerView recyclerView_meeting_list ;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.meeting_main,container,false);
        recyclerView_meeting_list= (RecyclerView) view.findViewById(R.id.recycler_meeting_list);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.meetings_refresh);
        configureRecyclerView();
        configureSwipeRefreshLayout();
        Toast.makeText(getActivity(),"oncreate",Toast.LENGTH_SHORT).show();
        return view;
    }

    public void updateMeetings(ArrayList<Request> requests){
        meetingList = requests;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        getMeetingsDataFromServer();
//    }

    private void configureSwipeRefreshLayout(){
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setRefreshing(true);
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                getMeetingsDataFromServer();
            }
        });
    }

    private void getMeetingsDataFromServer(){
        ApiInterfaceRetrofit retrofit = ApiClientRetrofit.getClient().create(ApiInterfaceRetrofit.class);
        Call<ArrayList<Request>> menteeMeetingsList = retrofit.getMenteeMeetingsList(35);
        menteeMeetingsList.enqueue(new Callback<ArrayList<Request>>() {
            @Override
            public void onResponse(Call<ArrayList<Request>> call, Response<ArrayList<Request>> response)
            {
                if (response.code()==200||response.code()==204)
                {
                    meetingList= response.body();
//                    Log.d("Size:", meetingList.get(0).getContactName());
                    mAdapter.updateDataSet(meetingList);
                    refreshLayout.setRefreshing(false);

                }
                else
                {
                    refreshLayout.setRefreshing(false);
                    Log.d("Size:", "error");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Request>> call, Throwable t)
            {
                refreshLayout.setRefreshing(false);
                Log.d("Size:", "error1");
            }
        });
    }
    private void configureRecyclerView()
    {
        Log.d("Size:", "config...");
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView_meeting_list.setLayoutManager(mLayoutManager);
        recyclerView_meeting_list.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new RequestsAdapter(getContext());
        recyclerView_meeting_list.setAdapter(mAdapter);

//        meetingList.add(new Request(1, 2,"call","פגישה מס 1","10/3/2017", "13:00","HR",
//            "", ""));
//        meetingList.add(new Request(1, 2,"call","פגישה מס 1","10/3/2017", "13:00","HR",
//                "", ""));
//        meetingList.add(new Request(1, 2,"call","פגישה מס 2","10/3/2017", "13:00","HR",
//                "", ""));
//        meetingList.add(new Request(1, 2,"call","פגישה מס 3","10/3/2017", "13:00","HR",
//                "", ""));
//        meetingList.add(new Request(1, 2,"call","פגישה מס 4","10/3/2017", "13:00","HR",
//                "", ""));
//        meetingList.add(new Request(1, 2,"call","פגישה מס 5","10/3/2017", "13:00","HR",
//                "", ""));
//        meetingList.add(new Request(1, 2,"call","פגישה מס 6","10/3/2017", "13:00","HR",
//                "", ""));
//        meetingList.add(new Request(1, 2,"call","פגישה מס 7","10/3/2017", "13:00","HR",
//                "", ""));
//        meetingList.add(new Request(1, 2,"call","פגישה מס 8","10/3/2017", "13:00","HR",
//                "", ""));
//        meetingList.add(new Request(1, 2,"call","פגישה מס 9","10/3/2017", "13:00","HR",
//                "", ""));
//        meetingList.add(new Request(1, 2,"call","פגישה מס 10","10/3/2017", "13:00","HR",
//                "", ""));
//        meetingList.add(new Request(1, 2,"call","פגישה מס 11","10/3/2017", "13:00","HR",
//                "", ""));
        mAdapter.updateDataSet(meetingList);

    }

    @Override
    public void onRefresh() {
        getMeetingsDataFromServer();
    }
}
