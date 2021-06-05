package com.algebra.soccernewtry.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.algebra.soccernewtry.R
import com.algebra.soccernewtry.constants.Constants
import com.algebra.soccernewtry.databinding.ActivitySubmitTeamsBinding
import com.algebra.soccernewtry.displayMessage
import com.algebra.soccernewtry.game.history.HistoryAdapter
import com.algebra.soccernewtry.game.history.HistoryFragment
import com.algebra.soccernewtry.game.teams.blue.BlueTeamFragment
import com.algebra.soccernewtry.game.teams.red.RedTeamFragment
import com.algebra.soccernewtry.historyOfGame.main.MatchViewModel
import com.algebra.soccernewtry.historyOfGame.model.Match
import com.algebra.soccernewtry.matchFlow.main.MatchFlowViewModel
import com.algebra.soccernewtry.matchFlow.model.MatchFlow
import com.algebra.soccernewtry.matchPlayers.main.MatchPlayerViewModel
import com.algebra.soccernewtry.matchPlayers.model.MatchPlayer
import com.algebra.soccernewtry.player.main.PlayerViewModel
import com.algebra.soccernewtry.player.model.Player
import com.algebra.soccernewtry.runningGame.main.RunningGameViewModel
import com.algebra.soccernewtry.runningGame.model.History
import com.algebra.soccernewtry.stateactivity.main.StateActivityViewModel
import com.algebra.soccernewtry.stateactivity.model.StateOfActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class SubmitTeamsActivity : AppCompatActivity() {

    lateinit var binding: ActivitySubmitTeamsBinding
    private lateinit var pagerAdapter: SlidePagerAdapter
    private lateinit var frgBlut: Fragment
    private lateinit var frgRed: Fragment
    private val viewModel: StateActivityViewModel by viewModels()
    private val viewModelRunningGame: RunningGameViewModel by viewModels()
    private val viewModelPlayers: PlayerViewModel by viewModels()
    private val viewModelMatch: MatchViewModel by viewModels()
    private val viewModelMatchPlayer: MatchPlayerViewModel by viewModels()
    private val viewModelMatchFlow: MatchFlowViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySubmitTeamsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        clear()
        setUpToolbar()
        bind()
        setUpViewPager()

      //  endOper = EndOperation(viewModelPlayers, viewModelMatch, viewModelMatchFlow, viewModelMatchPlayer, this)
    }

    override fun onResume() {
        super.onResume()
        this.lifecycleScope.launchWhenResumed{
            val allPlayers = viewModelPlayers.getAllPlayersForStat()
            allPlayers.forEach {
                getRedTeam().forEach {player ->
                    if(it.id == player.id) {
                        viewModelPlayers.addPlayer(Player(it.id, it.name, it.defense, it.agility, it.technique, 1, 1, it.bonusPoints, 0))
                    }
                }
                getBlueTeam().forEach {player ->
                    if(it.id == player.id) viewModelPlayers.addPlayer(Player(it.id, it.name, it.defense, it.agility, it.technique, 1, 2, it.bonusPoints, 0))
                }
            }
        }
    }

    private fun clear(){
        RedTeamFragment.goalBlue = 0
        RedTeamFragment.goalRed = 0
        RedTeamFragment.counter = 0
    }

    private fun bind(){
        viewModel.getStateOfActivity().observe(this, Observer {
            if(it.isNotEmpty() && it[0].isEndedGame == 0) viewModel.addStateActiity(StateOfActivity(it[0].id, 1))
        })

        val listOfHistory = mutableListOf<com.algebra.soccernewtry.game.history.History>()
        this.lifecycleScope.launchWhenResumed {
            viewModelRunningGame.getAllHistoryCourutine().forEach {
                Log.d("ispisHistoryNsme", it.goalgetter.toString())
                var isRed = false
                var autogoalName = ""
                if(it.teamId == 1) isRed = true
                if(it.isAutoGoal == 1) {
                    autogoalName = it.goalgetter
                    listOfHistory.add(com.algebra.soccernewtry.game.history.History(it.goalRed, it.goalBlue, "", "",
                        isRed, it.id, autogoalName, it.goalgetterId, it.assisterId))
                } else{
                    listOfHistory.add(com.algebra.soccernewtry.game.history.History(it.goalRed, it.goalBlue, it.assister, it.goalgetter,
                        isRed, it.id, "", it.goalgetterId, it.assisterId))
                }
                adapterHistory.setList(listOfHistory.reversed())
                if(listOfHistory.isNotEmpty()){
                    RedTeamFragment.goalRed = adapterHistory.listOfResults[adapterHistory.listOfResults.size - 1].goalRed
                    RedTeamFragment.goalBlue = adapterHistory.listOfResults[adapterHistory.listOfResults.size - 1].goalBlue
                }
            }
        }
    }

    private fun setUpViewPager(){
        pagerAdapter = SlidePagerAdapter(supportFragmentManager)
        frgBlut = BlueTeamFragment.newInstance(getBlueTeam())
        frgRed = RedTeamFragment.newInstance(getRedTeam())
        val fragments = listOf<Fragment>(frgRed, frgBlut, HistoryFragment.newInstance())
        pagerAdapter.setPages(fragments)
        binding.viewPager.adapter = pagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    private fun getRedTeam(): List<Player>{
        return intent.getSerializableExtra(Constants.RED_TEAM) as List<Player>
    }

    private fun getBlueTeam(): List<Player>{
        return intent.getSerializableExtra(Constants.BLUE_TEAM) as List<Player>
    }

    fun setScore(){
        if(frgBlut is BlueTeamFragment){
            (frgBlut as BlueTeamFragment).setScore()
        }
    }

    fun setScoreRed(){
        if(frgRed is RedTeamFragment){
            (frgRed as RedTeamFragment).setScore()
        }
    }

    private fun setUpToolbar(){
        window.statusBarColor = ContextCompat.getColor(this, R.color.greenToolbar)
    }

    private inner class SlidePagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm){

        private val fragments = mutableListOf<Fragment>()

        fun setPages(listOfFragments: List<Fragment>){
            fragments.clear()
            fragments.addAll(listOfFragments)
            notifyDataSetChanged()
        }

        override fun getItem(position: Int): Fragment = fragments[position]
        override fun getCount(): Int = fragments.size

        override fun getPageTitle(position: Int): CharSequence? {
            return when(position){
                0 -> "RED TEAM"
                1 -> "BLUE TEAM"
                2 -> "HISTORY"
                else -> ""
            }
        }
    }

    override fun onBackPressed() {
       displayMessage(this, "If you want to end game press END MATCH!")
    }

    fun endMatchOperation(){
        viewModelMatch.insertMatch(Match(0, getTodayDate()))
        lifecycleScope.launchWhenResumed {
            viewModelPlayers.getAllPlayersForStat().forEach {
                if(it.isPlaying == 1 && it.isDeleted != 1) viewModelPlayers.addPlayer(Player(it.id, it.name, it.defense, it.agility, it.technique,
                    0,0, it.bonusPoints, it.isDeleted))
            }
        }
        var matchId: Int = -1
        lifecycleScope.launchWhenResumed {
            val list = viewModelMatch.getAllMatchesCourtine()
            matchId = list[list.size - 1].id
        }


        SubmitTeamsActivity.adapterHistory.listOfResults.forEach {
            val isAutoGoal = if(it.autoGoal.isNullOrEmpty()) 0 else 1
            val teamId = if(it.isRed) 1
            else 2
            val matchFlow = MatchFlow(0, matchId, it.goalGeterId, it.assisterId, teamId, isAutoGoal)
            viewModelMatchFlow.addMatchFlow(matchFlow)
        }

        RedTeamFragment.checkListOfPlayers.forEach {
            val matchPlayer = MatchPlayer(0 , matchId, it.id)
            viewModelMatchPlayer.addMatchPlayer(matchPlayer)
        }

        BlueTeamFragment.checkListOfPlayers.forEach {
            viewModelMatchPlayer.addMatchPlayer(MatchPlayer(0 ,matchId, it.id))
        }

    }
    private fun getTodayDate(): String{
        val today = Calendar.getInstance()

        val day = today.get(Calendar.DAY_OF_MONTH)
        val month = today.get(Calendar.MONTH) + 1
        val year = today.get(Calendar.YEAR)

        return "$day.$month.$year"
    }

    override fun onPause() {
        if(checkIfUserLeftApp){
            var teamId = 0
            var isAutogoal = 0
            viewModelRunningGame.deleteAll()
            adapterHistory.listOfResults.forEach {
                teamId = if(it.isRed) 1 else 2
                isAutogoal = if(it.autoGoal?.isEmpty()!!) 0 else 1
                viewModelRunningGame.addHistory(History(0, it.goalGeterId, it.assisterId, teamId, isAutogoal, it.goalRed, it.goalBlue))
            }
            Log.d("ispisovo", "jesituuuu")
        }else{
            viewModelRunningGame.deleteAll()
            adapterHistory.listOfResults.clear()
            RedTeamFragment.checkListOfPlayers.clear()
            BlueTeamFragment.checkListOfPlayers.clear()
        }
        super.onPause()
    }

    companion object{
        val adapterHistory = HistoryAdapter()
        var checkIfUserLeftApp = true
    }
}