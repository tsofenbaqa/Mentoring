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

import com.example.a2017.mentoring.Model.MenteeList;
import com.example.a2017.mentoring.R;
import com.example.a2017.mentoring.RetrofitApi.BaseUrl;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Pcp on 17/01/2017.
 */

public class MenteeAdapter extends RecyclerView.Adapter<MenteeAdapter.MenteeHolder> {

    List<MenteeList> mentees = new ArrayList<>();
    Context context;

    public MenteeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MenteeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.mentee_list, parent, false);
        return new MenteeHolder(v);
    }
    @Override
    public void onBindViewHolder(MenteeHolder holder, int position) {
        holder.contactName.setText(String.valueOf(mentees.get(position).getContactName()));
        holder.image.setImageURI(BaseUrl.MENTORING_JPG+mentees.get(position).getUserId());


    }
    @Override
    public int getItemCount() {
        return mentees.size();
    }

    public void updateDataSet(List<MenteeList> mentees) {
        this.mentees.clear();
        this.mentees.addAll(mentees);
        this.notifyDataSetChanged();
    }

    class MenteeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView contactName;
        SimpleDraweeView image ;

        public MenteeHolder(View itemView) {
            super(itemView);
            contactName = (TextView) itemView.findViewById(R.id.mentee_name);
            image = (SimpleDraweeView) itemView.findViewById(R.id.contactImage);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

}