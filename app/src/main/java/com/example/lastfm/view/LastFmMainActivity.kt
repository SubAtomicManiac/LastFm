package com.example.lastfm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lastfm.R
import com.example.lastfm.controller.LastFmController
import com.example.lastfm.databinding.TrackListBinding
import com.example.lastfm.domain.entities.tracks.TrackOrder.DEFAULT
import com.example.lastfm.extensions.GenericAdapter
import com.example.lastfm.extensions.hideKeyboard
import com.example.lastfm.viewmodel.TrackListViewModel
import com.example.lastfm.viewmodel.TrackViewModel
import com.example.lib.controllers
import kotlinx.android.synthetic.main.activity_lastfm_main.*
import kotlinx.android.synthetic.main.track_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class LastFmMainActivity : AppCompatActivity() {
    private val trackViewModel : TrackListViewModel by viewModel()
    private val controller : LastFmController by controllers()
    private lateinit var recyclerViewAdapter: GenericAdapter<TrackViewModel>
    private lateinit var spinnerListener: AdapterView.OnItemSelectedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lastfm_main)
        setupView()
        setupObservers()
        setupListeners()
    }

    private fun setupView(){
        val binding = TrackListBinding.inflate(layoutInflater)
        binding.viewModel = trackViewModel
        track_list_Container.addView(binding.root)
        controller.searchTracks(null, DEFAULT.name)
        recyclerViewAdapter = GenericAdapter(R.layout.track_row)
        track_list.adapter = recyclerViewAdapter
        track_list.layoutManager = LinearLayoutManager(this)
        ArrayAdapter.createFromResource(this, R.array.orderBy_array, android.R.layout.simple_dropdown_item_1line). also {
            spinnerAdapter -> spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
            appearance_spinner.adapter = spinnerAdapter
        }
    }

    private fun setupObservers(){
        trackViewModel.tracksLiveData.observe(this, Observer { recyclerViewAdapter.addItems(it) })
    }

    private fun setupListeners(){
        details_backdrop.setOnClickListener { controller.trackMinimised() }

        recyclerViewAdapter.setOnListItemViewClickListener( object : GenericAdapter.OnListItemViewClickListener {
            override fun onClick(view: View, position: Int) {
                view.hideKeyboard()
                val item = recyclerViewAdapter.getItem(position)
                controller.fetchTrack(item.track,item.artist)
            }
        })

        track_search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let{controller.searchTracks(it,appearance_spinner.selectedItem.toString())}
                return false
            }

        })

        spinnerListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                controller.searchTracks(null, appearance_spinner.selectedItem.toString())
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val search = track_search.query.toString()
                if (search != "") controller.searchTracks(search, appearance_spinner.selectedItem.toString())
            }

        }
        appearance_spinner.onItemSelectedListener = spinnerListener
    }
}
