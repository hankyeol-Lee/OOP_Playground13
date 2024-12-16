package com.example.oop_project

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CustomSpinnerAdapter(
    context: Context,
    private val items: List<String>,         // 전체 아이템 목록
    private val disabledItems: List<String> // 비활성화 아이템 목록
) : ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, items) {

    override fun isEnabled(position: Int): Boolean {
        // disabledItems에 포함된 항목은 비활성화
        return !disabledItems.contains(items[position])
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent) as TextView

        if (!isEnabled(position)) {
            // 비활성화된 항목은 회색으로 표시
            view.setTextColor(Color.GRAY)
        } else {
            // 활성화된 항목은 기본 색상으로 표시
            view.setTextColor(Color.BLACK)
        }

        return view
    }
}