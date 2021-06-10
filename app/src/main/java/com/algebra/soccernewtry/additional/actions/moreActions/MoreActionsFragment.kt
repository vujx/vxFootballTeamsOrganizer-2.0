package com.algebra.soccernewtry.additional.actions.moreActions

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.algebra.soccernewtry.R
import com.algebra.soccernewtry.additional.actions.*
import com.algebra.soccernewtry.databinding.FragmentMoreActionsBinding
import com.algebra.soccernewtry.dialog.DialogCheck
import com.algebra.soccernewtry.displayMessage
import com.algebra.soccernewtry.historyOfGame.main.MatchViewModel
import com.algebra.soccernewtry.matchFlow.main.MatchFlowViewModel
import com.algebra.soccernewtry.matchPlayers.main.MatchPlayerViewModel
import com.algebra.soccernewtry.networking.main.ServiceViewModel
import com.algebra.soccernewtry.player.main.PlayerViewModel
import com.algebra.soccernewtry.player.model.Player
import com.algebra.soccernewtry.player.repository.PlayerRepository
import com.algebra.soccernewtry.shareCode.main.ShareCodeViewModel
import com.algebra.soccernewtry.shareCode.model.ShareCode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreActionsFragment : Fragment() {

    private var _binding: FragmentMoreActionsBinding? = null
    private val binding get() = _binding!!
    private lateinit var csvExport: CSVExport

    private val viewModelPlayer: PlayerViewModel by viewModels()
    private val viewModelMatchPlayer: MatchPlayerViewModel by viewModels()
    private val viewModelMatchFlow: MatchFlowViewModel by viewModels()
    private val viewModelMatches: MatchViewModel by viewModels()
    private val viewModelService: ServiceViewModel by viewModels()
    private val viewModelCode: ShareCodeViewModel by viewModels()

    private lateinit var insertValues: InsertValues
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoreActionsBinding.inflate(inflater, container, false)
        clickListener()
        return binding.root
    }

    private fun onFailureShareCode(){
        if(isInternetAvailable() || verifyAvailableNetwork(requireActivity() as MoreActionsActivity))
            displayMessage(requireActivity() as MoreActionsActivity, "Something is wrong, try again!")
        else
            displayMessage(requireActivity() as MoreActionsActivity, "Check your internet connection!")
    }

    private fun clickListener(){
        binding.btnShareDatabase.setOnClickListener {
            val dialog = createDialog("Are you sure you want to share database?", "Refresh")
            dialog.listener = object: DialogCheck.Listener{
                override fun getPress(isPress: Boolean) {
                    if(isPress){
                        insertValues = InsertValues(viewModelPlayer, viewModelMatchFlow, viewModelMatches, viewModelMatchPlayer)
                        lifecycleScope.launchWhenResumed {
                            viewModelService.getCode(insertValues.getAllValues())
                            viewModelService.getCode.observe(requireActivity(), Observer {
                                displayMessage(requireActivity() as AppCompatActivity, "You got your code!")
                                viewModelCode.addCode(ShareCode(0, it))
                            })
                            viewModelService.errorObserver.observe(requireActivity(), Observer {
                                onFailureShareCode()
                            })
                        }
                    }
                }
            }
        }

        binding.btnRefreshAll.setOnClickListener {
            val dialog = createDialog("Are you sure you want to refresh all?", "Refresh")
            dialog.listener = object: DialogCheck.Listener{
                override fun getPress(isPress: Boolean) {
                    if(isPress){
                        lifecycleScope.launchWhenResumed {
                            val listOfAllPlayers = viewModelPlayer.getAllPlayersForStat()
                            listOfAllPlayers.filter {
                                it.isDeleted == 1
                            }.forEach {
                                viewModelPlayer.deletePlayer(it.id)
                            }

                            listOfAllPlayers.filter {
                                it.isDeleted == 0
                            }.forEach {
                                viewModelPlayer.addPlayer(Player(it.id, it.name, it.defense, it.agility, it.technique, 0, 0, 0, 0))
                            }
                    }
                        deleteAll()
                }
            }
            }
        }

        binding.btnResetAll.setOnClickListener {
            val dialog = createDialog("Are you sure you want to reset all?", "Reset")
            dialog.listener = object: DialogCheck.Listener{
                override fun getPress(isPress: Boolean) {
                    if(isPress){
                        viewModelPlayer.deleteAll()
                        deleteAll()
                    }
                }
            }
        }

        binding.btnCSV.setOnClickListener {
            csvExport = CSVExport(viewModelPlayer, viewModelMatches,requireActivity() as MoreActionsActivity)
            csvExport.createCSVValues()
        }
    }

    private fun deleteAll(){
        viewModelMatches.deleteAllMatches()
        viewModelMatchPlayer.deleteAllMatchPlayers()
        viewModelMatchFlow.deleteAll()
    }

    private fun createDialog(title: String, tag: String): DialogCheck{
        val dialog = DialogCheck(title)
        dialog.show(requireActivity().supportFragmentManager, tag)
        return dialog
    }

    companion object {
        @JvmStatic
        fun newInstance() = MoreActionsFragment()
    }
}