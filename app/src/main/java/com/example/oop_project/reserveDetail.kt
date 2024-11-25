package com.example.oop_project

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.oop_project.databinding.ActivityReserveDetailBinding
import com.example.oop_project.databinding.ActivityReserveMainBinding

class reserveDetail : AppCompatActivity() {
    private var calendar = Calendar.getInstance()
    private var year = calendar.get(Calendar.YEAR)
    private var month = calendar.get(Calendar.MONTH)
    private var day = calendar.get(Calendar.DAY_OF_MONTH)
    private val hour = calendar.get(Calendar.HOUR_OF_DAY)
    private val minute = calendar.get(Calendar.MINUTE)
    private lateinit var binding : ActivityReserveDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reserve_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding = ActivityReserveDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.Title.text = intent.getStringExtra("title")
        binding.Address.text = intent.getStringExtra("address")

        binding.datepicker.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, month, day ->
                    binding.reserveDatetext.setText(
                        "$year/${month + 1}/$day"
                    )
                },
                year, month, day
            )
            datePickerDialog.show()
        }
        binding.timePicker1.setOnClickListener {
            showTimePickerDialog(binding.timefrom)
        }

        binding.timePicker2.setOnClickListener {
            showTimePickerDialog(binding.timeto)
        }

    }
    private fun showTimePickerDialog(targetEditText: EditText){
        val timePickerDialog = TimePickerDialog(
            this,
            {_, hour, minute ->
                val formattedTime = String.format("%02d:%02d",hour, minute)
                targetEditText.setText(formattedTime)
            },
            hour,minute,true
        )
        timePickerDialog.show()
    }
}