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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.algebra.soccernewtry.additional.actions.MoreActionsActivity
import com.algebra.soccernewtry.additional.actions.isInternetAvailable
import com.algebra.soccernewtry.additional.actions.verifyAvailableNetwork
import com.algebra.soccernewtry.databinding.FragmentCodeBinding
import com.algebra.soccernewtry.dialog.DialogCheck
import com.algebra.soccernewtry.displayMessage
import com.algebra.soccernewtry.networking.main.ServiceViewModel
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
            else{
                binding.tvDisplay.text = ""
                adapter.setList(it)
            }
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
                dialog.listener = object: DialogCheck.Listener{
                    override fun getPress(isPress: Boolean) {
                        viewModelService.deleteCode(shareCode.code)
                        viewModelService.deleteCode.observe(requireActivity(), Observer {
                            adapter.removeCode(shareCode)
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
            viewModelService.getValueToGenerateDatabase(binding.etEnterCode.text.toString())
            viewModelService.getValue.observe(requireActivity(), Observer {
                Log.d("SDasdsd", it)
            })
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = CodeFragment()
    }
}