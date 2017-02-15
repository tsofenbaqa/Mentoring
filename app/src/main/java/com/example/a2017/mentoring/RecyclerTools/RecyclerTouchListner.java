package com.example.a2017.mentoring.RecyclerTools;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 2017 on 04/02/2017.
 */
public class RecyclerTouchListner implements RecyclerView.OnItemTouchListener
{
    private GestureDetector gestureDetector ;
    private IclickListner clickListner ;

    public interface IclickListner
    {
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }

    public RecyclerTouchListner(Context context, final RecyclerView recyclerView, final IclickListner clickListner)
    {
        this.clickListner = clickListner ;
        gestureDetector = new GestureDetector(context , new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onSingleTapUp(MotionEvent e)
            {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e)
            {
                final View child = recyclerView.findChildViewUnder(e.getX(),e.getY());
                if (child!=null && clickListner !=null )
                {
                    clickListner.onLongClick(child,recyclerView.getChildAdapterPosition(child));
                }
            }
        });
    }

    @Override
    public synchronized boolean onInterceptTouchEvent(final RecyclerView recyclerView, MotionEvent e)
    {
        final View child = recyclerView.findChildViewUnder(e.getX(),e.getY());
        if (child!=null && clickListner !=null  && gestureDetector.onTouchEvent(e))
        {
            clickListner.onClick(child, recyclerView.getChildAdapterPosition(child));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView recyclerView, MotionEvent e)
    {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept)
    {

    }
}
