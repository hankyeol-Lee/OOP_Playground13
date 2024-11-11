package com.example.oop_project

// 필요한 import 문
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class reservePingPongFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reserve_ping_pong, container, false)

        // listbutton6 버튼을 찾고 클릭 리스너 설정
        val listButton6 = view.findViewById<Button>(R.id.listbutton6)
        listButton6.setOnClickListener {
            // reserveDetail 액티비티로 이동하는 인텐트 생성
            val intent = Intent(requireContext(), reserveDetail::class.java)
            startActivity(intent)
        }

        return view
    }
}
