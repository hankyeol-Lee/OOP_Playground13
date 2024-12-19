package com.example.oop_project.View

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Html
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.oop_project.CustomSpinnerAdapter
import com.example.oop_project.Model.Reservation
import com.example.oop_project.Model.ReservationRepository
import com.example.oop_project.R
import com.example.oop_project.ViewModel.ReserveViewModel
import com.example.oop_project.databinding.ActivityReserveDetailBinding
import java.util.Calendar

class ReserveDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReserveDetailBinding
    private val viewModel: ReserveViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReserveDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val category:String = intent.getStringExtra("category") ?: ""
        val title : String = intent.getStringExtra("title") ?:""
        //HTML 태그 뜨는 오류 수정
        binding.Title.text =
            Html.fromHtml(title, Html.FROM_HTML_MODE_LEGACY).toString()
        binding.Address.text =
            Html.fromHtml(intent.getStringExtra("address"), Html.FROM_HTML_MODE_LEGACY).toString()
        binding.placeImage.setImageResource(
            when {
                category.contains("탁구") || title.contains("탁구")-> R.drawable.tabletennis
                category.contains("볼링") || title.contains("볼링") -> R.drawable.bowling
                category.contains("테니스") || title.contains("테니스") -> R.drawable.tennis
                else -> R.drawable.ic_launcher_background
            }
        )
        setListeners()

    }


    private fun setListeners() {
        val calendar = Calendar.getInstance()

        binding.reserveBtn.setOnClickListener()
        {
            val place = binding.Title.text.toString() // 장소
            val date = binding.reserveDatetext.text.toString() // 날짜
            val startTime = binding.startTime.selectedItem.toString() // 시작 시간
            val endTime = binding.endTime.selectedItem.toString() // 종료 시간

            // Reservation 객체 생성
            val reservation = Reservation(
                place = place,
                date = date,
                startTime = startTime,
                endTime = endTime
            )

            // 예약 저장 함수 호출
            viewModel.saveReserve(this@ReserveDetailActivity, reservation)
            val intent = Intent(this, ReserveCompleteActivity::class.java).apply {
                putExtra("facilityName", place)
                putExtra("date", date)
                putExtra("timeRange", "$startTime ~ $endTime")
            }
            startActivity(intent)
        }
            binding.datepicker.setOnClickListener {
                // 현재 연도, 월, 일을 가져옴
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                // DatePickerDialog 생성
                val datePickerDialog = DatePickerDialog(
                    this,
                    { _, selectedYear, selectedMonth, selectedDay ->
                        // 선택된 날짜를 YYYY/MM/DD 형식으로 변환
                        val formattedDate = String.format(
                            "%04d-%02d-%02d",
                            selectedYear,
                            selectedMonth + 1, // 월은 0부터 시작하므로 +1
                            selectedDay
                        )
                        // TextView에 설정
                        binding.reserveDatetext.setText(formattedDate)
                        val place = binding.Title.text.toString()
                        setupSpinner(place, formattedDate)
                    },
                    year, month, day
                )
                // DatePickerDialog 보여주기
                datePickerDialog.show()
            }



        }
        fun setupSpinner(place: String, date: String) {
            viewModel.reservedTimes.observe(this) { reservedTimes ->
                val timeSlots = resources.getStringArray(R.array.시간).toList() // Spinner 시간 배열
                val adapter = CustomSpinnerAdapter(this, timeSlots, reservedTimes) // 비활성화 항목 포함
                binding.startTime.adapter = adapter
                binding.endTime.adapter = adapter
            }

            // ViewModel을 통해 예약된 시간대 데이터 요청
            viewModel.fetchReservedTimeSlots(place, date)
        }
    }
