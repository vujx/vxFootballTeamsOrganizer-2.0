package com.algebra.soccernewtry.display.achievement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.algebra.fuca.display.achievement.ResultAdaper
import com.algebra.soccernewtry.databinding.ActivityResultBinding
import com.algebra.soccernewtry.exitApp
import com.algebra.soccernewtry.navdrawer.NavDrawerList
import com.algebra.soccernewtry.navdrawer.SetupToolbarDrawer
import com.algebra.soccernewtry.player.main.PlayerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private val navDrawerListExpandable = NavDrawerList(this)
    private val adapterResult = ResultAdaper()
    private val viewModelPlayer: PlayerViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityResultBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbarAndNavigationDrawer()
        setUpRecyclerView()

        bind()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun bind(){
        this.lifecycleScope.launchWhenResumed {
            val list = viewModelPlayer.getAllPlayersSpecification(1)
            Log.d("Davidimo", list.toString())
            val numOfAssist = viewModelPlayer.getNumberOfAssist(1)
            val numOfGoals = viewModelPlayer.getNumberOfGoals(1)
            Log.d("IspisnumGoals", numOfGoals.toString())
            Log.d("ispisAssist", numOfAssist.toString())
        }
    }

    private fun setUpRecyclerView(){
        binding.recyclerViewArrival.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewArrival.adapter = adapterResult
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return SetupToolbarDrawer(this, binding.root).onOptionsItemSelected(item)
    }

    private fun setupToolbarAndNavigationDrawer(){
        binding.toolbar.title = "Achievement"
        SetupToolbarDrawer(this, binding.root).setUpToolbar()
        navDrawerListExpandable.setUpValues()
        navDrawerListExpandable.prepareListData()
    }

    override fun onBackPressed() {
        exitApp(this)
    }
}