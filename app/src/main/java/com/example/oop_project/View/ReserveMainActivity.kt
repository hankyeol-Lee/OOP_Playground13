package com.example.oop_project.View
import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.oop_project.R
import com.example.oop_project.Model.PlaceRepository
import com.example.oop_project.ViewModel.PlaceViewModel
import com.example.oop_project.ViewModel.PlaceViewModelFactory
import com.example.oop_project.databinding.ActivityReserveMainBinding

class ReserveMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReserveMainBinding
    private lateinit var mListView: ListView
    private val viewModel: PlaceViewModel by viewModels { PlaceViewModelFactory(PlaceRepository()) }
    private lateinit var searchViewAdaptor: SearchViewAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReserveMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mListView = findViewById(R.id.SearchListView)
        searchViewAdaptor = SearchViewAdaptor()
        mListView.adapter = searchViewAdaptor
        setupObservers()
        setupListeners()


    }

    private fun setupObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.places.collect { places ->
                searchViewAdaptor.clear()
                for (place in places) {
                    searchViewAdaptor.addItem(place.title, place.address, place.category)
                }
                searchViewAdaptor.notifyDataSetChanged()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.error.collect { errorMessage ->
                errorMessage?.let {
                    Toast.makeText(this@ReserveMainActivity, it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupListeners() {
        mListView.setOnItemClickListener { _, _, position, _ ->
            val place = searchViewAdaptor.getItem(position)
            val intent = Intent(this, ReserveDetailActivity::class.java).apply {
                putExtra("title", place.placeTitle)
                putExtra("address", place.placeAddress)
            }
            startActivity(intent)
        }
        binding.adresssearch.isSubmitButtonEnabled = true
        binding.adresssearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                viewModel.searchPlaces(query)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })


    }
}