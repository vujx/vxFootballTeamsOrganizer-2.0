package com.algebra.soccernewtry.display.historyOfMatch.historyOfPlayer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.algebra.soccernewtry.R
import com.algebra.soccernewtry.databinding.FragmentBlueTeamHistoryOfPlayerBinding
import com.algebra.soccernewtry.databinding.FragmentMatchFlowBinding
import com.algebra.soccernewtry.display.historyOfMatch.historyOfPlayer.editHistory.EditHistoryMatchFlow
import com.algebra.soccernewtry.game.history.History
import com.algebra.soccernewtry.game.history.HistoryAdapter
import com.algebra.soccernewtry.game.history.edit.EditHistory
import com.algebra.soccernewtry.matchFlow.main.MatchFlowViewModel
import com.algebra.soccernewtry.player.main.PlayerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentMatchFlow : Fragment() {

    private var _binding: FragmentMatchFlowBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MatchFlowViewModel by viewModels()
    private val viewModelPlayer: PlayerViewModel by viewModels()
    private lateinit var editHistory: EditHistoryMatchFlow

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMatchFlowBinding.inflate(inflater, container, false)
        editHistory = EditHistoryMatchFlow(requireActivity())
        setUpRecyclerView()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if(adapterHistory.listOfResults.isEmpty()){
            requireActivity().lifecycleScope.launchWhenResumed {
                var scoreRed = 0
                var scoreBlue = 0
                val listOfMatchFlow = viewModel.getAllMatchFlowByMatchId(PlayerHistoryActivity.matchId)
                val allPlayers = viewModelPlayer.getAllPlayersForStat().filter {
                    it.isDeleted == 0
                }
                var teamId = 0
                listOfMatchFlow.forEach {
                    if(it.teamId == 1 && it.isAutoGoal == 0 || it.teamId == 2 && it.isAutoGoal == 1)
                    {
                        scoreRed += 1
                        teamId = 1
                    }
                    if(it.teamId == 2 && it.isAutoGoal == 0 || it.teamId == 1 && it.isAutoGoal == 1){
                        scoreBlue += 1
                        teamId = 2
                    }
                    var goalGetterName = ""
                    allPlayers.forEach {player ->
                        if(player.id == it.goalgetterId)
                            goalGetterName = player.name
                    }

                    var assisterName = ""
                    if(it.assisterId != -1)
                        allPlayers.forEach {player ->
                            if(player.id == it.assisterId)
                                assisterName = player.name
                        }
                    if(it.isAutoGoal == 0){
                        if(teamId == 1){
                            val history = History(scoreRed, scoreBlue, assisterName, goalGetterName, true, it.id, "", it.goalgetterId, it.assisterId)
                            adapterHistory.addResult(history)
                        } else {
                            val history = History(scoreRed, scoreBlue, assisterName, goalGetterName, false, it.id, "", it.goalgetterId, it.assisterId)
                            adapterHistory.addResult(history)
                        }
                    } else {
                        val history = History(scoreRed, scoreBlue, "", "", false, it.id, goalGetterName, it.goalgetterId, it.assisterId)
                        adapterHistory.addResult(history)
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView(){
        binding.recyclerViewHistory.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerViewHistory.adapter = adapterHistory

        adapterHistory.listener = object: HistoryAdapter.Listener{
            override fun editHistory(item: History) {
               editHistory.editHistory(item)
            }

            override fun deleteHistory(item: History, position: Int) {
                TODO("Not yet implemented")
            }

        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentMatchFlow()
        var adapterHistory = HistoryAdapter()

    }
}