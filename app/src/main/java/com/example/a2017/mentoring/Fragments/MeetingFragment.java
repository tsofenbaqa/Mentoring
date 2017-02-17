package com.example.a2017.mentoring.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.a2017.mentoring.R;

/**
 * Created by 2017 on 15/02/2017.
 */

public class MeetingFragment extends Fragment
{
    public  RecyclerView recyclerView_meeting_list ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.meeting_main,container,false);
        recyclerView_meeting_list= (RecyclerView) view.findViewById(R.id.recycler_meeting_list);
        configureRecyclerView();
        return view;
    }

    private void configureRecyclerView()
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView_meeting_list.setLayoutManager(layoutManager);
        recyclerView_meeting_list.setItemAnimator(new DefaultItemAnimator());
        recyclerView_meeting_list.setAdapter(null);
    }
}
