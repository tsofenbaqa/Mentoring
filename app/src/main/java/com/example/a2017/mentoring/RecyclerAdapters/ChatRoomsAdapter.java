package com.example.a2017.mentoring.RecyclerAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 2017 on 04/02/2017.
 */

public class ChatRoomsAdapter extends RecyclerView.Adapter<ChatRoomsAdapter.ViewHolder>
{

    class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(View itemView)
        {
            super(itemView);
        }
    }

    public ChatRoomsAdapter()
     {

     }
    @Override
    public ChatRoomsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
       // View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_room_list,parent,false);
       // return  new ViewHolder(itemview);
        return null;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {

    }

    @Override
    public int getItemCount()
    {
        return 0;
    }


}
