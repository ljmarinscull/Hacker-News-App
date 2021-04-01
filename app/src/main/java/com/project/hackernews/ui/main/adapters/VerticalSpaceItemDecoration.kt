package com.project.hackernews.ui.main.adapters

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class VerticalSpaceItemDecoration(context: Context, @DimenRes verticalMarginInPx: Int) :
    ItemDecoration() {
    private val verticalMarginInPx: Int = context.resources.getDimension(verticalMarginInPx).toInt()

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.bottom = verticalMarginInPx
    }

}