package com.example.a2017.mentoring.RecyclerTools;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
/**
 * Created by 2017 on 04/02/2017.
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration
{
    private static final int [] attrs = new int []{android.R.attr.listDivider};
    private Context context;
    private Drawable drawable;

    public DividerItemDecoration(Context context)
    {
        this.context=context;
        final TypedArray array = context.obtainStyledAttributes(attrs);
        drawable =array.getDrawable(0);
        array.recycle();
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state)
    {
        final  int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        for (int i = 0 ; i < parent.getChildCount() ; i++)
        {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + drawable.getIntrinsicHeight();
            drawable.setBounds(left,top,right,bottom);
            drawable.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {
        outRect.set(0,0,0,drawable.getIntrinsicHeight());
    }
}
