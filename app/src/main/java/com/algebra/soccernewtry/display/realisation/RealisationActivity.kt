package com.algebra.soccernewtry.display.realisation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.algebra.soccernewtry.databinding.ActivityRealisationBinding
import com.algebra.soccernewtry.exitApp
import com.algebra.soccernewtry.navdrawer.NavDrawerList
import com.algebra.soccernewtry.navdrawer.SetupToolbarDrawer

class RealisationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRealisationBinding
    private val navDrawerListExpandable = NavDrawerList(this)
    private val adapter = RealisationAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRealisationBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbarAndNavigationDrawer()
        setUpRecyclerView()
    }

    private fun setUpRecyclerView(){
        binding.recyclerViewArrival.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewArrival.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return SetupToolbarDrawer(this, binding.root).onOptionsItemSelected(item)
    }

    private fun setupToolbarAndNavigationDrawer(){
        binding.toolbar.title = "Realisation"
        SetupToolbarDrawer(this, binding.root).setUpToolbar()
        navDrawerListExpandable.setUpValues()
        navDrawerListExpandable.prepareListData()
    }

    override fun onBackPressed() {
        exitApp(this)
    }
}