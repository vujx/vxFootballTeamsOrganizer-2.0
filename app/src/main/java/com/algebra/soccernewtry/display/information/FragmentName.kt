package com.algebra.soccernewtry.display.information

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.algebra.soccernewtry.constants.Constants
import com.algebra.soccernewtry.databinding.FragmentNameBinding

class FragmentName : Fragment() {
    private var _binding: FragmentNameBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentNameBinding.inflate(inflater, container, false)
        binding.tvSetName.text = arguments?.getString(Constants.GET_PLAYER_NAME_KEY)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(name: String) = FragmentName().apply {
            val argBundle = Bundle()
            argBundle.putString(Constants.GET_PLAYER_NAME_KEY, name)
            this.arguments = argBundle
        }
    }
}