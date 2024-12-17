package com.example.oop_project.View

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.oop_project.Model.SearchViewItem
import com.example.oop_project.R

class SearchViewAdaptor(
    private val onItemClick: (SearchViewItem) -> Unit // 클릭 리스너 전달
) : RecyclerView.Adapter<SearchViewAdaptor.ViewHolder>() {
    val mItems = ArrayList<SearchViewItem>()



    override fun getItemId(position: Int): Long = 0L

    override fun getItemCount(): Int = mItems.size

    class ViewHolder(itemView: View, private val onItemClick: (SearchViewItem) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val placeTitle: TextView = itemView.findViewById(R.id.Place_Title)
        val placeAddress: TextView = itemView.findViewById(R.id.Place_Adress)
        val placeCategory: TextView = itemView.findViewById(R.id.place_category)

        fun bind(item: SearchViewItem) {
            placeTitle.text = item.placeTitle
            placeAddress.text = item.placeAddress
            placeCategory.text = item.placeCategory

            // 클릭 리스너 연결
            itemView.setOnClickListener {
                Log.d("RecyclerView", "Item clicked: ${item.placeTitle}")
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listview_custom, parent, false)
        return ViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mItems[position]
        holder.bind(item)
        holder.placeTitle.text = removeHtmlTags(item.placeTitle)
        holder.placeAddress.text = removeHtmlTags(item.placeAddress)
        holder.placeCategory.text = removeHtmlTags(item.placeCategory)


    }

    fun addItem(_placeTitle: String, _placeAddress: String, _placeCategory: String) {
        val item = SearchViewItem().apply {
            this.placeTitle = _placeTitle
            this.placeAddress = _placeAddress
            this.placeCategory = _placeCategory
        }
        mItems.add(item)
    }
    fun clear() {
        mItems.clear()
        notifyDataSetChanged()
    }
    fun removeHtmlTags(input: String): String {
        return input.replace(Regex("<[^>]*>"), "") // HTML 태그 제거
    }



}