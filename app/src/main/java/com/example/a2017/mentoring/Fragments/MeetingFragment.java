package com.example.a2017.mentoring.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 2017 on 15/02/2017.
 */

public class MeetingFragment extends Fragment
{
    RecyclerView requestsRecyclerView;
    LinearLayoutManager mLayoutManager;
    RequestsAdapter mAdapter;

    List<Request> meetingList = new ArrayList<Request>();

    public  RecyclerView recyclerView_meeting_list ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.meeting_main,container,false);
        recyclerView_meeting_list= (RecyclerView) view.findViewById(R.id.recycler_meeting_list);
        configureRecyclerView();
        Toast.makeText(getActivity(),"oncreate",Toast.LENGTH_SHORT).show();
        return view;
    }


    private void configureRecyclerView()
    {
        Log.d("Size:", "config...");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView_meeting_list.setLayoutManager(layoutManager);
        recyclerView_meeting_list.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new RequestsAdapter(getContext());
        recyclerView_meeting_list.setAdapter(mAdapter);
        meetingList.add(new Request(1, 2,"call","פגישה מס 1","10/3/2017", "13:00","HR",
            "", ""));
        meetingList.add(new Request(1, 2,"call","פגישה מס 1","10/3/2017", "13:00","HR",
                "", ""));
        meetingList.add(new Request(1, 2,"call","פגישה מס 2","10/3/2017", "13:00","HR",
                "", ""));
        meetingList.add(new Request(1, 2,"call","פגישה מס 3","10/3/2017", "13:00","HR",
                "", ""));
        meetingList.add(new Request(1, 2,"call","פגישה מס 4","10/3/2017", "13:00","HR",
                "", ""));
        meetingList.add(new Request(1, 2,"call","פגישה מס 5","10/3/2017", "13:00","HR",
                "", ""));
        meetingList.add(new Request(1, 2,"call","פגישה מס 6","10/3/2017", "13:00","HR",
                "", ""));
        meetingList.add(new Request(1, 2,"call","פגישה מס 7","10/3/2017", "13:00","HR",
                "", ""));
        meetingList.add(new Request(1, 2,"call","פגישה מס 8","10/3/2017", "13:00","HR",
                "", ""));
        meetingList.add(new Request(1, 2,"call","פגישה מס 9","10/3/2017", "13:00","HR",
                "", ""));
        meetingList.add(new Request(1, 2,"call","פגישה מס 10","10/3/2017", "13:00","HR",
                "", ""));
        meetingList.add(new Request(1, 2,"call","פגישה מס 11","10/3/2017", "13:00","HR",
                "", ""));
        mAdapter.updateDataSet(meetingList);

    }
}
