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
import com.algebra.soccernewtry.databinding.FragmentBlueTeamHistoryOfPlayerBinding
import com.algebra.soccernewtry.display.historyOfMatch.historyOfPlayer.model.PlayerMatchScore
import com.algebra.soccernewtry.game.PlayerCheck
import com.algebra.soccernewtry.matchFlow.main.MatchFlowViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentBlueTeamHistoryOfPlayer : Fragment() {

    private var _binding: FragmentBlueTeamHistoryOfPlayerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MatchFlowViewModel by viewModels()
    private val adapterTeam = HistoryPlayerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBlueTeamHistoryOfPlayerBinding.inflate(inflater, container, false)
        setUpRecyclerView()
        return binding.root
    }

    private fun setUpRecyclerView(){
        binding.recyclerViewRedTeamHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewRedTeamHistory.adapter = adapterTeam
    }

    override fun onResume() {
        super.onResume()
        requireActivity().lifecycleScope.launchWhenResumed {
            val blueTeam = viewModel.getBlueTeam(PlayerHistoryActivity.matchId)
            val listOfBlueTeamMatchScore = mutableListOf<PlayerMatchScore>()
            blueTeam.forEach {
                listOfBlueTeamMatchScore.add(viewModel.getMatchScore(it, PlayerHistoryActivity.matchId))
            }
            adapterTeam.setList(listOfBlueTeamMatchScore)
            binding.progressBar.visibility = View.GONE
            listOfBlueTeamMatchScore.forEach {
                blueTeamPlayers.add(PlayerCheck(0, it.name, false, false, false, 2))
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentBlueTeamHistoryOfPlayer()
        val blueTeamPlayers = mutableListOf<PlayerCheck>()
    }
}