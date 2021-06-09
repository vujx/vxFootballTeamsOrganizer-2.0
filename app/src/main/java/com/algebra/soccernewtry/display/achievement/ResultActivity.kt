package com.algebra.soccernewtry.display.achievement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.algebra.fuca.display.achievement.ResultAdaper
import com.algebra.soccernewtry.databinding.ActivityResultBinding
import com.algebra.soccernewtry.exitApp
import com.algebra.soccernewtry.historyOfGame.main.MatchViewModel
import com.algebra.soccernewtry.matchFlow.main.MatchFlowViewModel
import com.algebra.soccernewtry.matchFlow.model.MatchFlow
import com.algebra.soccernewtry.matchPlayers.main.MatchPlayerViewModel
import com.algebra.soccernewtry.navdrawer.NavDrawerList
import com.algebra.soccernewtry.navdrawer.SetupToolbarDrawer
import com.algebra.soccernewtry.player.main.PlayerViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.notifyAll

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
                listForStatRealisation.add(PlayerStat(player.id, player.name, wins, loses, draw, 0, 0, 0, playerSpec.attendance, 0))
            }

            allPlayers.filter{
                it.isDeleted == 0
            }.forEach {player ->
                var checkName = false
                listForStatRealisation.forEach {
                    if(player.name == it.name)
                        checkName = true
                }
                if(!checkName) listForStatRealisation.add(PlayerStat(player.id, player.name, 0, 0, 0, 0,0 ,0 , 0, 0))
            }
            if(listForStatRealisation.isEmpty()) binding.tvDisplay.text = "You don't have any players added!"
            adapterResult.setList(listForStatRealisation)
            binding.progressBar.visibility = View.GONE
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