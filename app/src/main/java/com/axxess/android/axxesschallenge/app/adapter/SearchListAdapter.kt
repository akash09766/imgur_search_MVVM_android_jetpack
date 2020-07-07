package com.axxess.android.axxesschallenge.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.axxess.android.axxesschallenge.R
import com.axxess.android.axxesschallenge.app.listeners.ItemClickListener
import com.axxess.android.axxesschallenge.app.model.search.ImgurDataItem
import com.axxess.android.axxesschallenge.app.utils.MConstants
import com.axxess.android.axxesschallenge.app.utils.loadImage

class SearchListAdapter :
    RecyclerView.Adapter<SearchListAdapter.SearchListAdapterViewHolder>() {

    private val TAG = "SearchListAdapter"

    private val searchList = ArrayList<ImgurDataItem>()
    private lateinit var listener: ItemClickListener

    fun setData(searchListData: List<ImgurDataItem>?) {
        this.searchList.clear()
        searchListData?.let { this.searchList.addAll(it) }
        notifyDataSetChanged()
    }

    fun setItemClickListener(listener: ItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListAdapterViewHolder {
        return SearchListAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.search_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchListAdapterViewHolder, position: Int) {
        holder.searchItemIv.loadImage(
            (MConstants.IMAGE_BASE_URL.plus(searchList[position].cover)).plus(
                MConstants.IMAGE_EXTENSION
            )
        )
        holder.view.setOnClickListener {
            listener.onItemClick(searchList[position])
        }
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    class SearchListAdapterViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        val searchItemIv = itemView.findViewById(R.id.searchItemIv) as ImageView
    }
}