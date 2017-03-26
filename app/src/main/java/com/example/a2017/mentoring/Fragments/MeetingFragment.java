package com.example.a2017.mentoring.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a2017.mentoring.Model.Request;
import com.example.a2017.mentoring.R;
import com.example.a2017.mentoring.RecyclerAdapters.RequestsAdapter;
import com.example.a2017.mentoring.RecyclerTools.RecyclerTouchListner;
import com.example.a2017.mentoring.RetrofitApi.ApiClientRetrofit;
import com.example.a2017.mentoring.RetrofitApi.ApiInterfaceRetrofit;
import com.example.a2017.mentoring.Utils.Preferences;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 2017 on 15/02/2017.
 */

public class MeetingFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout refreshLayout;
    RequestsAdapter mAdapter;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    List<Request> meetingList = new ArrayList<Request>();
    public RecyclerView recyclerView_meeting_list;
    int menteeId;
    boolean isMentee;
    FloatingActionButton fab;
    private Paint p = new Paint();

    private AlertDialog.Builder alertDialog;
    private View view;
    private boolean add = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isMentee = Preferences.isMentee(getContext());
        if (isMentee) {
            menteeId = Preferences.myId(getContext());
        } else {
            menteeId = getArguments().getInt("menteeId");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.meeting_main, container, false);
        recyclerView_meeting_list = (RecyclerView) view.findViewById(R.id.recycler_meeting_list);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.meetings_refresh);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        configureRecyclerView();
        configureSwipeRefreshLayout();
        fabOnClick();
        return view;
    }

    public void updateMeetings(ArrayList<Request> requests) {
        meetingList = requests;
    }

    @Override
    public void onStart() {
        super.onStart();
        getMeetingsDataFromServer();
        initSwipe();
    }

    private void configureSwipeRefreshLayout() {
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setRefreshing(true);
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                getMeetingsDataFromServer();
            }
        });
    }

    private void getMeetingsDataFromServer() {
        ApiInterfaceRetrofit retrofit = ApiClientRetrofit.getClient().create(ApiInterfaceRetrofit.class);
        Call<ArrayList<Request>> menteeMeetingsList = retrofit.getMenteeMeetingsList(menteeId);
        menteeMeetingsList.enqueue(new Callback<ArrayList<Request>>() {
            @Override
            public void onResponse(Call<ArrayList<Request>> call, Response<ArrayList<Request>> response) {
                if (response.code() == 200 || response.code() == 204) {
                    meetingList = response.body();
//                    Log.d("Size:", meetingList.get(0).getContactName());
                    Gson gson = new Gson();
                    Log.d("updateUi: ", gson.toJson(response.body().get(0)));
                    mAdapter.updateDataSet(meetingList);
                    refreshLayout.setRefreshing(false);

                } else {
                    refreshLayout.setRefreshing(false);
                    Log.d("Size:", "error");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Request>> call, Throwable t) {
                refreshLayout.setRefreshing(false);
                Log.d("Size:", "error1");
            }
        });
    }

    private void configureRecyclerView() {
        Log.d("Size:", "config...");
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView_meeting_list.setLayoutManager(mLayoutManager);
        recyclerView_meeting_list.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new RequestsAdapter(getContext());
        recyclerView_meeting_list.setAdapter(mAdapter);
        mAdapter.updateDataSet(meetingList);
        recyclerView_meeting_list.addOnItemTouchListener((new RecyclerTouchListner(getContext(), recyclerView_meeting_list, new RecyclerTouchListner.IclickListner() {
            @Override
            public void onClick(View view, int position) {
                Request request = meetingList.get(position);
                SendMeetingRequest(menteeId, request, 1);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        })));

    }

    private void fabOnClick() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMeetingRequest(menteeId, 0);
            }
        });
    }

    private void goToMeetingRequest(int menteeId, int flag) {
        fragmentManager = getFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        Bundle bundle = new Bundle();
        bundle.putInt("menteeId", menteeId);
        bundle.putInt("flag", flag);
        RequestFragment requestFragment = new RequestFragment();
        requestFragment.setArguments(bundle);
        transaction.replace(R.id.fragment_container, requestFragment, "REQUEST_MEETING");
        transaction.commit();
    }

    private void SendMeetingRequest(int menteeId, Request request, int flag) {
        fragmentManager = getFragmentManager();
        transaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("menteeId", menteeId);
        bundle.putInt("flag", flag);
        bundle.putSerializable("request", request);
        RequestFragment requestFragment = new RequestFragment();
        requestFragment.setArguments(bundle);
        transaction.replace(R.id.fragment_container, requestFragment, "REQUEST_MEETING");
        transaction.commit();
    }

    @Override
    public void onRefresh() {
        getMeetingsDataFromServer();
    }

    private void initSwipe(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT){
                    mAdapter.removeItem(position);
                }else{
                    Request request = meetingList.get(position);
                    SendMeetingRequest(menteeId, request, 1);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if(dX <= 0){
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_white_24dp);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    }else{
//                        p.setColor(Color.parseColor("#388E3C"));
//                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
//                        c.drawRect(background,p);
//                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_edit_white_24dp);
//                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
//                        c.drawBitmap(icon,null,icon_dest,p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView_meeting_list);
    }

}
