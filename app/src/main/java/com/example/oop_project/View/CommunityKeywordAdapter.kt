package com.example.oop_project.View


import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.oop_project.Model.KeywordItem
import com.example.oop_project.R

class CommunityKeywordAdapter(private val keywords: List<KeywordItem>) :
    RecyclerView.Adapter<CommunityKeywordAdapter.KeywordViewHolder>() {

    class KeywordViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val keywordTextView: TextView = view.findViewById(R.id.keywordTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_keywords, parent, false)
        return KeywordViewHolder(view)
    }

    // 데이터와 아이템 뷰를 바인딩 > 순위표에 보여지는 텍스트를 변경함.
    override fun onBindViewHolder(holder: KeywordViewHolder, position: Int) {
        val keywordItem = keywords[position]
        val keywordString:String = "${position+1} .  " + keywordItem.keyword + "                " +
                "⬆ " + keywordItem.number
        if (keywordItem.number >= 30 ) {
        holder.keywordTextView.setTextColor(Color.BLUE)
        }
        holder.keywordTextView.text = keywordString
    }

    override fun getItemCount(): Int = keywords.size
}



