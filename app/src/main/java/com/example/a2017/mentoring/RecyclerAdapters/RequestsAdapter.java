package com.example.a2017.mentoring.RecyclerAdapters;

/**
 * Created by Pcp on 01/03/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a2017.mentoring.Model.Request;
import com.example.a2017.mentoring.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Pcp on 17/01/2017.
 */

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.RequestsHolder> {

    List<Request> requests = new ArrayList<>();
    Context context;

    public RequestsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RequestsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.fragment_meeting_row, parent, false);
        return new RequestsHolder(v);
    }
    @Override
    public void onBindViewHolder(RequestsHolder holder, int position) {
//        holder.title.setText(String.valueOf(requests.get(position).get));
//        holder.topic.setText(String.valueOf(requests.get(position).getMeetingTopic()));
//        holder.date.setText(String.valueOf(requests.get(position).getDate()));

    }
    @Override
    public int getItemCount() {
        return requests.size();
    }

    public void updateDataSet(List<Request> requests) {
        this.requests.clear();
        this.requests.addAll(requests);
        this.notifyDataSetChanged();
    }

    class RequestsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView topic;
        TextView date;

        public RequestsHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.meeting_title);
            topic = (TextView) itemView.findViewById(R.id.meeting_topic);
            date = (TextView) itemView.findViewById(R.id.meeting_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

}