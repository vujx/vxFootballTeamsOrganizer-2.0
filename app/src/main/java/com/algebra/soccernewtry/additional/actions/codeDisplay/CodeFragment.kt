package com.algebra.soccernewtry.additional.actions.codeDisplay

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.algebra.soccernewtry.additional.actions.MoreActionsActivity
import com.algebra.soccernewtry.additional.actions.isInternetAvailable
import com.algebra.soccernewtry.additional.actions.verifyAvailableNetwork
import com.algebra.soccernewtry.databinding.FragmentCodeBinding
import com.algebra.soccernewtry.dialog.DialogCheck
import com.algebra.soccernewtry.displayMessage
import com.algebra.soccernewtry.historyOfGame.main.MatchViewModel
import com.algebra.soccernewtry.matchFlow.main.MatchFlowViewModel
import com.algebra.soccernewtry.matchPlayers.main.MatchPlayerViewModel
import com.algebra.soccernewtry.networking.main.ServiceViewModel
import com.algebra.soccernewtry.player.main.PlayerViewModel
import com.algebra.soccernewtry.shareCode.main.ShareCodeViewModel
import com.algebra.soccernewtry.shareCode.model.ShareCode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CodeFragment : Fragment() {

    private var _binding: FragmentCodeBinding? = null
    private val binding get() = _binding!!
    private val adapter = CodeAdapter()
    private val viewModelCode: ShareCodeViewModel by viewModels()
    private val viewModelService: ServiceViewModel by viewModels()
    private val viewModelPlayer: PlayerViewModel by viewModels()
    private val viewModelMatchPlayer: MatchPlayerViewModel by viewModels()
    private val viewModelMatchFlow: MatchFlowViewModel by viewModels()
    private val viewModelMatches: MatchViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCodeBinding.inflate(inflater, container, false)
        clickListener()
        setUpRecyclerView()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModelCode.getAllShareCode().observe(requireActivity(), Observer {
            if(it.isEmpty()) binding.tvDisplay.text = "You don't have any code generated!"
            else binding.tvDisplay.text = ""

            adapter.setList(it)
            binding.progressBar.visibility = View.GONE
        })
    }

    private fun setUpRecyclerView(){
        binding.recyclerViewScore.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerViewScore.adapter = adapter

        adapter.listener = object: CodeAdapter.Listener{
            override fun copyCode(code: String) {
                copyCodeToClipBoard(code)
            }

            override fun deleteCode(shareCode: ShareCode) {
                val dialog = DialogCheck("Are you sure you want to delete ${shareCode.code} code from database?")
                dialog.show(requireActivity().supportFragmentManager, "Deletecode")
                Log.d("ispis", shareCode.toString())
                dialog.listener = object: DialogCheck.Listener{
                    override fun getPress(isPress: Boolean) {
                        if(isPress){
                            viewModelService.deleteCode(shareCode.code)
                            viewModelService.deleteCode.observe(requireActivity(), Observer {
                                Log.d("isp", it.toString())
                                Log.d("ispisshareCode", shareCode.toString())
                                viewModelCode.deleteCode(shareCode.id)
                            })
                            viewModelService.errorObserver.observe(requireActivity(), Observer {
                                onFailureShareCode()
                            })
                        }
                    }
                }
            }
        }
    }

    private fun onFailureShareCode(){
        if(isInternetAvailable() || verifyAvailableNetwork(requireActivity() as MoreActionsActivity))
            displayMessage(requireActivity() as MoreActionsActivity, "Something is wrong, try again!")
        else
            displayMessage(requireActivity() as MoreActionsActivity, "Check your internet connection!")
    }

    private fun copyCodeToClipBoard(text: String){
        val clipboard = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("copy text", text)
        clipboard.setPrimaryClip(clip)
    }

    private fun clickListener(){
        binding.btnGenerateCode.setOnClickListener {
            if(binding.etEnterCode.text.toString().length != 8)
                binding.etEnterCode.error = "You have to entered 8 characters!"
            else {
                viewModelService.getValueToGenerateDatabase(binding.etEnterCode.text.toString())
                viewModelService.getValue.observe(requireActivity(), Observer {

                    deleteAllFromTables()

                    val indexOfPlayer = it.indexOf(';')
                    val queryPlayer = it.substring(0, indexOfPlayer + 1)
                    val queryCheck = queryPlayer.substring(indexOfPlayer - 2, indexOfPlayer - 1)

                    if(queryCheck != "S"){
                        viewModelMatches.insertValue(queryPlayer)
                        Log.d("ispisqueryPlayer", queryPlayer)
                    }

                    val indexOfMatchFlow = it.indexOf(';', indexOfPlayer + 1)
                    val queryMatchFlow = it.substring(indexOfPlayer + 1, indexOfMatchFlow + 1)
                    val checkQuery = queryMatchFlow.substring(queryMatchFlow.length - 3, queryMatchFlow.length - 2)

                    if(checkQuery != "S")
                        viewModelMatches.insertValue(queryMatchFlow)

                    val indexOfMatches = it.indexOf(";", indexOfMatchFlow + 1)
                    val queryMatches = it.substring(indexOfMatchFlow + 1, indexOfMatches + 1)
                    val checkQueryMatches = queryMatches.substring(queryMatches.length - 3, queryMatches.length - 2)

                    if(checkQueryMatches != "S")
                        viewModelMatches.insertValue(queryMatches)

                    val indexOfMatchPlayer = it.indexOf(";", indexOfMatches + 1)
                    val queryMatchPlayers = it.substring(indexOfMatches + 1, indexOfMatchPlayer + 1)
                    val checkQueryMatchPlayers = queryMatchPlayers.substring(queryMatchPlayers.length - 3, queryMatchPlayers.length - 2)

                    if(checkQueryMatchPlayers != "S")
                        viewModelMatches.insertValue(queryMatchPlayers)
                    binding.etEnterCode.setText("")

                    binding.etEnterCode.setText("")
                    displayMessage(requireActivity() as AppCompatActivity, "You downloaded a shared database")
                })

                viewModelService.errorObserver.observe(requireActivity(), Observer {
                    if("Expected a string but was BEGIN_OBJECT at path $" == it)
                        binding.etEnterCode.error = "You have entered wrong code, try again!"
                    else
                        onFailureShareCode()
                })
            }
        }
    }

    private fun deleteAllFromTables(){
        viewModelMatchFlow.deleteAll()
        viewModelMatchPlayer.deleteAllMatchPlayers()
        viewModelPlayer.deleteAll()
        viewModelMatches.deleteAllMatches()
    }

    companion object {
        @JvmStatic
        fun newInstance() = CodeFragment()
    }
}