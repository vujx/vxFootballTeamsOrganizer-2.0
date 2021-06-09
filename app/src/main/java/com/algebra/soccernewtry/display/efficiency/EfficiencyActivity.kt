package com.algebra.soccernewtry.display.efficiency

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.algebra.fuca.display.achievement.ResultAdaper
import com.algebra.fuca.display.efficiency.EfficiencyAdapter
import com.algebra.soccernewtry.databinding.ActivityEfficiencyBinding
import com.algebra.soccernewtry.display.achievement.PlayerStat
import com.algebra.soccernewtry.exitApp
import com.algebra.soccernewtry.historyOfGame.main.MatchViewModel
import com.algebra.soccernewtry.matchFlow.main.MatchFlowViewModel
import com.algebra.soccernewtry.matchPlayers.main.MatchPlayerViewModel
import com.algebra.soccernewtry.navdrawer.NavDrawerList
import com.algebra.soccernewtry.navdrawer.SetupToolbarDrawer
import com.algebra.soccernewtry.player.main.PlayerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EfficiencyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEfficiencyBinding
    private val navDrawerListExpandable = NavDrawerList(this)
    private lateinit var adapter: EfficiencyAdapter
    private val viewModelPlayer: PlayerViewModel by viewModels()
    private val viewModelMatches: MatchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEfficiencyBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        this.lifecycleScope.launchWhenResumed {
            adapter = EfficiencyAdapter(viewModelMatches.getAllMatchesCourtine().size)
            setUpRecyclerView()
        }
        setupToolbarAndNavigationDrawer()
    }

    override fun onResume() {
        super.onResume()
        val listForStatRealisation = mutableListOf<PlayerStat>()
        this.lifecycleScope.launchWhenResumed {
            val allPlayers = viewModelPlayer.getAllPlayersForStat()
            allPlayers.filter {
                it.isDeleted == 0
            }.forEach {player ->
                val playerSpec = viewModelPlayer.getAllPlayersSpecification(player.id)
                var wins = 0
                var loses = 0
                var draw = 0
                viewModelPlayer.getPlayersMatches(player.id).forEach {
                    val getMatchResultRed = viewModelPlayer.getMatchResult(it.matchId)
                    val getMatchResultBlue = viewModelPlayer.getResultBlueTeam(it.matchId)

                    if(getMatchResultRed.teamGoals > getMatchResultBlue.teamGoals && it.teamId == 1) wins++
                    if(getMatchResultRed.teamGoals == getMatchResultBlue.teamGoals) draw++
                    if(getMatchResultRed.teamGoals < getMatchResultBlue.teamGoals && it.teamId == 1) loses++

                    if(getMatchResultRed.teamGoals < getMatchResultBlue.teamGoals && it.teamId == 2) wins++
                    if(getMatchResultRed.teamGoals > getMatchResultBlue.teamGoals && it.teamId == 2) loses++
                }

                val numOfGoals = viewModelPlayer.getNumberOfGoals(player.id)
                val numOfAss = viewModelPlayer.getNumberOfAssist(player.id)
                val numOfAutogoals = viewModelPlayer.getNumberOfAutogoals(player.id)
                listForStatRealisation.add(PlayerStat(player.id, playerSpec.name, wins, loses, draw, numOfGoals, numOfAss, numOfAutogoals, playerSpec.attendance, 0))

            }
            if(listForStatRealisation.isEmpty()) binding.tvDisplay.text = "You don't have any players added!"
            adapter.setEfficiencyList(listForStatRealisation)
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
        binding.toolbar.title = "Efficiency"
        SetupToolbarDrawer(this, binding.root).setUpToolbar()
        navDrawerListExpandable.setUpValues()
        navDrawerListExpandable.prepareListData()
    }

    override fun onBackPressed() {
        exitApp(this)
    }
}