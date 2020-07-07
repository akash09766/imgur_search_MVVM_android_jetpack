package com.axxess.android.axxesschallenge.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.axxess.android.axxesschallenge.R
import com.axxess.android.axxesschallenge.app.room.entities.CommentsDetails

class CommentListAdapter : RecyclerView.Adapter<CommentListAdapter.CommentListAdapterViewHolder>() {

    private val TAG = "CommentListAdapter"

    private val commentList = ArrayList<CommentsDetails>()

    fun setData(commentList: List<CommentsDetails>?) {
        this.commentList.clear()
        commentList?.let { this.commentList.addAll(it) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommentListAdapterViewHolder {
        return CommentListAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.comment_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CommentListAdapterViewHolder, position: Int) {
        holder.userCommentTv.text = commentList[position].comment
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    class CommentListAdapterViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        val userCommentTv = itemView.findViewById(R.id.user_comment_tv) as TextView
    }
}