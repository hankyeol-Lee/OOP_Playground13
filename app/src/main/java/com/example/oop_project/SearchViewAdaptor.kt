package com.example.oop_project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class SearchViewAdaptor : BaseAdapter() {
    private val mItems = ArrayList<SearchViewItem>()

    override fun getCount(): Int = mItems.count()



    override fun getItem(position: Int): SearchViewItem = mItems[position]


    override fun getItemId(position: Int): Long = 0L

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        TODO("Not yet implemented")
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val context = parent.context
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.listview_custom, parent, false)

        // 커스텀 리스트뷰의 XML 속성 값 정의
        val placetitle: TextView = view.findViewById(R.id.Place_Title)
        val placeaddress: TextView = view.findViewById(R.id.Place_Adress)
        val placecategory: TextView = view.findViewById(R.id.place_category)


        // 데이터 가져오기
        val searchViewItem : SearchViewItem = getItem(position)

        // XML의 각 TextView에 데이터 설정
        placetitle.text = searchViewItem.placeTitle
        placeaddress.text = searchViewItem.placeAddress
        placecategory.text = searchViewItem.placeCategory


        return view
    }

    // 네이버 블로그 검색 중 데이터를 추가하는 메서드
    fun addItem(_placetitle: String, _placeAddress: String, _placeCategory: String) {
        val mItem = SearchViewItem().apply {
            this.placeTitle = _placetitle
            this.placeAddress = _placeAddress
            this.placeCategory = _placeCategory
        }
        mItems.add(mItem)
    }
}