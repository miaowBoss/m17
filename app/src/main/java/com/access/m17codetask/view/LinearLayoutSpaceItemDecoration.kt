package com.access.m17codetask.view

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class LinearLayoutSpaceItemDecoration(var spacing: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        var position = parent.getChildAdapterPosition(view)
        outRect.left = spacing
        outRect.right = spacing
        outRect.bottom = spacing
        if (position == 1) outRect.top = spacing
    }
}
