package com.algebra.soccernewtry.display.realisation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.algebra.soccernewtry.databinding.ActivityRealisationBinding
import com.algebra.soccernewtry.display.achievement.PlayerStat
import com.algebra.soccernewtry.exitApp
import com.algebra.soccernewtry.navdrawer.NavDrawerList
import com.algebra.soccernewtry.navdrawer.SetupToolbarDrawer
import com.algebra.soccernewtry.player.main.PlayerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RealisationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRealisationBinding
    private val navDrawerListExpandable = NavDrawerList(this)
    private val adapter = RealisationAdapter()
    private val viewModelPlayer: PlayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRealisationBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbarAndNavigationDrawer()
        setUpRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        val listForStatRealisation = mutableListOf<PlayerStat>()
        lifecycleScope.launchWhenResumed {
            viewModelPlayer.getAllPlayersForStat().filter {
                it.isDeleted == 0
            }.forEach {
                val numOfGoals = viewModelPlayer.getNumberOfGoals(it.id)
                val numOfAss = viewModelPlayer.getNumberOfAssist(it.id)
                val numOfAutogoals = viewModelPlayer.getNumberOfAutogoals(it.id)
                val playerSpec = viewModelPlayer.getAllPlayersSpecification(it.id)
                listForStatRealisation.add(PlayerStat(it.id, playerSpec.name, 0, 0, 0, numOfGoals, numOfAss, numOfAutogoals, playerSpec.attendance, 0))
            }
            adapter.setListRealisation(listForStatRealisation)
            if(listForStatRealisation.isEmpty()) binding.tvDisplay.text = "You don't have any players added!"
            else binding.tvDisplay.text = ""
            binding.progressBar.visibility = View.GONE
        }
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