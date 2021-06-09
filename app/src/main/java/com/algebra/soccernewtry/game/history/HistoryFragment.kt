package com.algebra.soccernewtry.game.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.algebra.soccernewtry.databinding.FragmentHistoryBinding
import com.algebra.soccernewtry.dialog.DialogCheck
import com.algebra.soccernewtry.game.SubmitTeamsActivity
import com.algebra.soccernewtry.game.history.edit.EditHistory
import com.algebra.soccernewtry.game.teams.red.RedTeamFragment
import com.algebra.soccernewtry.matchFlow.main.MatchFlowViewModel
import dagger.hilt.android.AndroidEntryPoint

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var editHistory: EditHistory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        setUpRecyclerView()
        editHistory = EditHistory(activity as SubmitTeamsActivity, requireActivity())

        return binding.root
    }

    private fun setUpRecyclerView() {
        binding.recyclerViewHistory.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerViewHistory.adapter = SubmitTeamsActivity.adapterHistory

        SubmitTeamsActivity.adapterHistory.listener = object : HistoryAdapter.Listener {
            override fun editHistory(item: History) {
                editHistory.editHistory(item)
            }

            override fun deleteHistory(item: History, position: Int) {
                val dialog = DialogCheck("Are you sure you want to delete goal?")
                dialog.show(requireActivity().supportFragmentManager, "Exit")
                dialog.listener = object : DialogCheck.Listener {
                    override fun getPress(isPress: Boolean) {
                        if (isPress) {
                            SubmitTeamsActivity.adapterHistory.removeResult(item, position)
                            if(item.isRed){
                                if(RedTeamFragment.goalRed != 0){
                                    RedTeamFragment.goalRed--
                                    for(counter: Int in position until SubmitTeamsActivity.adapterHistory.listOfResults.size){
                                        SubmitTeamsActivity.adapterHistory.listOfResults[counter].goalRed--
                                    }
                                }
                            }
                            else {
                                if(RedTeamFragment.goalBlue != 0){
                                    RedTeamFragment.goalBlue--
                                    for(counter: Int in position until SubmitTeamsActivity.adapterHistory.listOfResults.size){
                                        SubmitTeamsActivity.adapterHistory.listOfResults[counter].goalBlue--
                                    }
                                }
                            }
                            (activity as SubmitTeamsActivity).setScore()
                        }
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HistoryFragment()
    }
}