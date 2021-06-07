package com.algebra.soccernewtry.display.achievement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
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

@AndroidEntryPoint
class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private val navDrawerListExpandable = NavDrawerList(this)
    private val adapterResult = ResultAdaper()
    private val viewModelPlayer: PlayerViewModel by viewModels()
    private val viewModelMatches: MatchViewModel by viewModels()
    private val viewModelMatchFlow: MatchFlowViewModel by viewModels()
    private val viewModelMatchPlayers: MatchPlayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityResultBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbarAndNavigationDrawer()
        setUpRecyclerView()
        lifecycleScope.launchWhenResumed {
            val listOfMatchFlow = viewModelMatchFlow.getAll()
            Log.d("ListaMatchFlow", listOfMatchFlow.toString())
            val listOfMatchPLayers = viewModelMatchPlayers.getAllMatchPlayer().observe(
                this@ResultActivity, Observer {
                    Log.d("ListaMatchPlayer", it.toString())
                }
            )
            val allMatch = viewModelMatches.getAllMatchesCourtine()
            Log.d("ispisiAllMatch", allMatch.toString())
        }


        bind()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun bind(){
        val listForStatRealisation = mutableListOf<PlayerStat>()
        this.lifecycleScope.launchWhenResumed {
            val getAllMatches = viewModelMatches.getAllMatchesCourtine()
            val allPlayers = viewModelPlayer.getAllPlayersForStat()
            allPlayers.filter {
                it.isDeleted == 0
            }.forEach {
                val playerSpec = viewModelPlayer.getAllPlayersSpecification(it.id)
                var wins = 0
                var loses = 0
                var draw = 0
                viewModelPlayer.getPlayersMatches(it.id).forEach {
                    val getMatchResultRed = viewModelPlayer.getMatchResult(it.id)
                    Log.d("ispisMatchRes", getMatchResultRed.toString())
                    val getMatchResultBlue = viewModelPlayer.getResultBlueTeam(it.id)
                    Log.d("ispisMatchBlue", getMatchResultBlue.toString())
                }

               // val getTeamId = viewModelPlayer.getTeamIdPLayer(getMatchResultRed.matchId, it.id)
               // Log.d("ispisiTeamId", getTeamId.toString())
            }

         /*   val numOfAssist = viewModelPlayer.getNumberOfAssist(1)
            val numOfGoals = viewModelPlayer.getNumberOfGoals(1)
            Log.d("IspisnumGoals", numOfGoals.toString())
            Log.d("ispisAssist", numOfAssist.toString())
            val numOfAutogoal = viewModelPlayer.getNumberOfAutogoals(1)

            Log.d("ispisAutogoal", numOfAutogoal.toString())
            Log.d("ispisMatchResult", getMatchResult.toString())*/
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