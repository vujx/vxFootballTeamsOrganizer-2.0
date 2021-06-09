package com.algebra.soccernewtry.display.historyOfMatch.historyOfPlayer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.algebra.fuca.display.history.historyOfPlayers.HistoryPlayerAdapter
import com.algebra.soccernewtry.R
import com.algebra.soccernewtry.databinding.FragmentRedTeamHistoryOfPlayersBinding
import com.algebra.soccernewtry.display.historyOfMatch.historyOfPlayer.model.PlayerMatchScore
import com.algebra.soccernewtry.game.PlayerCheck
import com.algebra.soccernewtry.matchFlow.main.MatchFlowViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentRedTeamHistoryOfPlayers : Fragment() {

    private var _binding: FragmentRedTeamHistoryOfPlayersBinding? = null
    private val binding get() = _binding!!
    private val adapterTeam = HistoryPlayerAdapter()
    private val viewModel: MatchFlowViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRedTeamHistoryOfPlayersBinding.inflate(inflater, container, false)
        setUpRecyclerView()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if(listOfRedTeamMatchScore.isEmpty()){
            requireActivity().lifecycleScope.launchWhenResumed {
                val redTeam = viewModel.getRedTeam(PlayerHistoryActivity.matchId)
                redTeam.forEach {
                    listOfRedTeamMatchScore.add(viewModel.getMatchScore(it, PlayerHistoryActivity.matchId))
                }
                adapterTeam.setList(listOfRedTeamMatchScore)
                binding.progressBar.visibility = View.GONE
                listOfRedTeamMatchScore.forEach {
                    listOfPlayersRed.add(PlayerCheck(0, it.name, false, false, false, 1))
                }
            }
        } else {
            adapterTeam.setList(listOfRedTeamMatchScore)
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setUpRecyclerView(){
        binding.recyclerViewRedTeamHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewRedTeamHistory.adapter = adapterTeam
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentRedTeamHistoryOfPlayers()
        val listOfRedTeamMatchScore = mutableListOf<PlayerMatchScore>()
        val listOfPlayersRed = mutableListOf<PlayerCheck>()
    }
}