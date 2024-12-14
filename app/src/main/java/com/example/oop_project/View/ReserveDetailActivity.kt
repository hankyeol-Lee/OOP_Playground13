package com.example.oop_project.View

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Html
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.oop_project.Model.ReservationRepository
import com.example.oop_project.R
import com.example.oop_project.ViewModel.ReserveViewModel
import com.example.oop_project.ViewModel.ReserveViewModelFactory
import com.example.oop_project.databinding.ActivityReserveDetailBinding
import java.util.Calendar

class ReserveDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReserveDetailBinding
    private val viewModel: ReserveViewModel by viewModels {
        ReserveViewModelFactory(ReservationRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReserveDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 검색결과에 HTML 태그 처리
        binding.Title.text = Html.fromHtml(intent.getStringExtra("title"), Html.FROM_HTML_MODE_LEGACY).toString()
        binding.Address.text = Html.fromHtml(intent.getStringExtra("address"), Html.FROM_HTML_MODE_LEGACY).toString()

        setupObservers()
        setupListeners()
        setupSpinner()
    }

    private fun setupObservers() {
        viewModel.selectedDate.observe(this) { date ->
            binding.reserveDatetext.setText(date)
        }

        viewModel.isOverlapping.observe(this) { isOverlapping ->
            if (isOverlapping) {
                Toast.makeText(this, "이미 예약된 시간입니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "예약이 가능합니다.", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.reservationStatus.observe(this) { isSuccess ->
            if (isSuccess) {
                Toast.makeText(this, "예약이 완료되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupListeners() {
        binding.datepicker.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, month, day ->
                    val date = "$year/${month + 1}/$day"
                    viewModel.setSelectedDate(date)
                },
                viewModel.selectedDate.value?.split("/")?.get(0)?.toInt() ?: Calendar.getInstance().get(Calendar.YEAR),
                viewModel.selectedDate.value?.split("/")?.get(1)?.toInt()?.minus(1) ?: Calendar.getInstance().get(Calendar.MONTH),
                viewModel.selectedDate.value?.split("/")?.get(2)?.toInt() ?: Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        binding.reserveBtn.setOnClickListener {
            val place = binding.Title.text.toString()
            val date = binding.reserveDatetext.text.toString()
            val startTime = binding.startTime.selectedItem.toString()
            val endTime = binding.endTime.selectedItem.toString()

            // 중복 확인
            viewModel.checkDuplication(place, date, startTime, endTime)

            // 중복되지 않으면 예약
            viewModel.reservePlace(place, date, startTime, endTime)
        }
    }

    private fun setupSpinner() {
        // Spinner 초기화 (res/values/time.xml 사용)
        val timeArray = resources.getStringArray(R.array.시간)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, timeArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.startTime.adapter = adapter
        binding.endTime.adapter = adapter
    }

}