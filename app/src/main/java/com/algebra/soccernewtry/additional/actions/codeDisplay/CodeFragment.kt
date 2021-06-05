package com.algebra.soccernewtry.additional.actions.codeDisplay

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.algebra.soccernewtry.R
import com.algebra.soccernewtry.databinding.FragmentCodeBinding

class CodeFragment : Fragment() {

    private var _binding: FragmentCodeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = CodeFragment()
    }
}