package com.project.hackernews.ui.main.adapters

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.project.hackernews.NewsApplication

class SwipeToDeleteCallback(listener: SwipeToDelete) :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val mListener: SwipeToDelete = listener
    private val background: ColorDrawable = ColorDrawable(Color.RED)

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        // used for up and down movements
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition

        mListener.onSwiped(viewHolder,position)
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        val itemView: View = viewHolder.itemView
        val backgroundCornerOffset = 20 //so background is behind the rounded corners of itemVi
        if (dX > 0) { // Swiping to the right
            background.setBounds(itemView.left, itemView.top,
                    itemView.left + dX.toInt() + backgroundCornerOffset, itemView.bottom)
        } else if (dX < 0) { // Swiping to the left
            background.setBounds(itemView.right + dX.toInt() - backgroundCornerOffset,
                    itemView.top, itemView.right, itemView.bottom)
        } else { // view is unSwiped
            background.setBounds(0, 0, 0, 0)
        }
        background.draw(c)
        val paint = Paint()

        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL
        paint.textSize = 72F

        c.drawText("Delete", (itemView.right - 256).toFloat(), (itemView.bottom - 72).toFloat(), paint);
    }

    interface SwipeToDelete{
        fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int)
    }
}