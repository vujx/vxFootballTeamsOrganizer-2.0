package com.algebra.soccernewtry.display.efficiency

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.algebra.fuca.display.achievement.ResultAdaper
import com.algebra.fuca.display.efficiency.EfficiencyAdapter
import com.algebra.soccernewtry.databinding.ActivityEfficiencyBinding
import com.algebra.soccernewtry.exitApp
import com.algebra.soccernewtry.historyOfGame.main.MatchViewModel
import com.algebra.soccernewtry.navdrawer.NavDrawerList
import com.algebra.soccernewtry.navdrawer.SetupToolbarDrawer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EfficiencyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEfficiencyBinding
    private val navDrawerListExpandable = NavDrawerList(this)
    private lateinit var adapter: EfficiencyAdapter
    private val viewModelMatch: MatchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEfficiencyBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        this.lifecycleScope.launchWhenResumed {
            adapter = EfficiencyAdapter(viewModelMatch.getAllMatchesCourtine().size)
            setUpRecyclerView()
        }
        setupToolbarAndNavigationDrawer()
    }


    private fun setUpRecyclerView(){
        binding.recyclerViewArrival.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewArrival.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return SetupToolbarDrawer(this, binding.root).onOptionsItemSelected(item)
    }

    private fun setupToolbarAndNavigationDrawer(){
        binding.toolbar.title = "Efficiency"
        SetupToolbarDrawer(this, binding.root).setUpToolbar()
        navDrawerListExpandable.setUpValues()
        navDrawerListExpandable.prepareListData()
    }

    override fun onBackPressed() {
        exitApp(this)
    }
}