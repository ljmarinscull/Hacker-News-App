package com.project.hackernews.ui.main.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.hackernews.R
import com.project.hackernews.data.model.NewObject
import com.project.hackernews.ui.main.adapters.NewsAdapter.NewsHolder
import java.util.*

class NewsAdapter(
        var mContext: Context,
        var mListener: OnItemClickListener,
        private var mItems: ArrayList<NewObject>,
) : RecyclerView.Adapter<NewsHolder>() {

    private var inflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        inflater = LayoutInflater.from(parent.context)

        return NewsHolder(
                inflater!!.inflate(R.layout.item_layout, parent, false),
                parent.context
        )
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    fun removeItem(pos: Int): Any? {
        if (mItems.size > pos && pos >= 0) {
            val item: Any = mItems.removeAt(pos)
            notifyItemRemoved(pos)
            return item
        }
        return null
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        holder.bind(mItems[position])
    }

    fun setItems(items: ArrayList<NewObject>) {
        mItems = items
        notifyDataSetChanged()
    }

    inner class NewsHolder(itemView: View, val context: Context)
        : RecyclerView.ViewHolder(itemView) {
        lateinit var mNews: NewObject

        fun bind(news: NewObject) {
            mNews = news
            itemView.setOnClickListener {
                mListener.onItemClick(mNews)
            }
            val title = itemView.findViewById<TextView>(R.id.title)
            title.text = mNews.titleToShow()
            val authorAndTime = itemView.findViewById<TextView>(R.id.authorAndTime)
            authorAndTime.text = mNews.authorAndTimeShow()
        }
    }

    interface OnItemClickListener {
        fun onItemClick(obj: NewObject)
    }
}