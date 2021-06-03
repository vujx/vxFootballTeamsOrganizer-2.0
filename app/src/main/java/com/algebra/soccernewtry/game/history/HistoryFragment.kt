package com.algebra.soccernewtry.game.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.algebra.soccernewtry.R
import com.algebra.soccernewtry.databinding.FragmentHistoryBinding
import com.algebra.soccernewtry.game.SubmitTeamsActivity


class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        setUpRecyclerView()
        return binding.root
    }

    private fun setUpRecyclerView(){
        binding.recyclerViewHistory.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerViewHistory.adapter = SubmitTeamsActivity.adapterHistory
    }

    companion object {
        @JvmStatic
        fun newInstance() = HistoryFragment()
    }
}