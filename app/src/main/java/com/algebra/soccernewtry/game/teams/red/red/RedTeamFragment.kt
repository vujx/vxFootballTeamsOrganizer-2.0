package com.algebra.soccernewtry.game.teams.red

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.algebra.fuca.game.teams.SubmitTeamsAdapter
import com.algebra.soccernewtry.constants.Constants
import com.algebra.soccernewtry.databinding.FragmentRedTeamBinding
import com.algebra.soccernewtry.displayMessage
import com.algebra.soccernewtry.game.PlayerCheck
import com.algebra.soccernewtry.game.SubmitTeamsActivity
import com.algebra.soccernewtry.game.teams.SameMethodInFragments
import com.algebra.soccernewtry.player.model.Player

class RedTeamFragment : Fragment() {

    private var _binding: FragmentRedTeamBinding? = null
    private val binding get() = _binding!!
    private var playerNameAssist = ""
    private var playerNameGoalGeter = ""
    private var playerNameAutoGoal = ""
    private var checkAssist = false
    private var checkGolGeter = false
    private var checkAutoGoal = false
    private val redTeamAction = RedTeamAction()
    private val dialogs = DialogActionRedTeam()
    lateinit var redTeamList:List<Player>
    private val sameMethods = SameMethodInFragments()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRedTeamBinding.inflate(inflater, container, false)
        redTeamList = arguments?.getSerializable(Constants.RED_TEAM_FRAGMENT) as List<Player>
        setUpRecyclerViewRed()
        setScore()
        clickListener()
        return binding.root
    }

    private fun setUpRecyclerViewRed() {
        binding.recyclerViewTeamRed.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTeamRed.adapter = adapter

        if(counter == 0){
            checkListOfPlayers = mutableListOf()
            redTeamList.forEach {
                checkListOfPlayers.add(PlayerCheck(it.id, it.name, false, false, false))
            }
            adapter.setList(checkListOfPlayers)
        }
        counter++
        adapter.listenerForGoalsAndAssist = object: SubmitTeamsAdapter.ListenerForGoalAndAssist{
            override fun getGoal(item: PlayerCheck) {
                adapter.setList(checkListOfPlayers)
            }

            override fun getAssist(item: PlayerCheck) {
                adapter.setList(checkListOfPlayers)
            }

            override fun getAutogoal(item: PlayerCheck) {
                adapter.setList(checkListOfPlayers)
            }
        }
    }

    private fun clickListener(){
        binding.btnGoal.setOnClickListener {
            foundAssistAndGoalGeter()
            when{
                checkAssist && checkGolGeter -> {
                    dialogs.dialogAssisstAndGoalGetter(requireActivity(), playerNameAssist, playerNameGoalGeter, redTeamAction,
                        requireContext(), adapter, binding.root, activity as SubmitTeamsActivity, this)
                }
                checkGolGeter -> {
                    dialogs.dialogGoalgetterOnly(requireActivity(),  playerNameGoalGeter, redTeamAction, activity as SubmitTeamsActivity,
                        requireContext(), adapter, binding.root, this)
                }
                checkAutoGoal -> {
                    dialogs.dialogAutogoal(requireActivity(), playerNameAutoGoal, redTeamAction,
                        activity as SubmitTeamsActivity, requireContext(), adapter, binding.root, this)
                }
                else -> {
                    displayMessage(activity as SubmitTeamsActivity, "There must be a goalgetter!")
                }
            }
            setScore()
        }
        binding.btnEnd.setOnClickListener {
            sameMethods.endMatch(activity as SubmitTeamsActivity)
        }
    }

    private fun foundAssistAndGoalGeter(){
        checkListOfPlayers.forEach {
            if (it.isAssist) {
                checkAssist = true
                playerNameAssist = it.name
            }
            if(it.isGoalgeter){
                checkGolGeter = true
                playerNameGoalGeter = it.name
            }
            if(it.isAutoGol){
                checkAutoGoal = true
                playerNameAutoGoal = it.name
            }
        }
    }

     fun setAssistAndGoalToDefaultValue(){
        checkAssist = false
        checkGolGeter = false
        checkAutoGoal = false
        checkListOfPlayers = mutableListOf()
        redTeamList.forEach {
            checkListOfPlayers.add(PlayerCheck(it.id, it.name, false, false, false))
        }
        adapter.setList(checkListOfPlayers)
        (activity as SubmitTeamsActivity).setScore()
    }


    fun setScore(){
        binding.tvScore.text = "$goalRed:$goalBlue"
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(redTeam: List<Player>) = RedTeamFragment().apply {
            val argBundle = Bundle()
            argBundle.putSerializable(Constants.RED_TEAM_FRAGMENT, redTeam as ArrayList<Player>)
            this.arguments = argBundle
        }
        var goalRed = 0
        var goalBlue = 0
        var idForHistory = 0
        lateinit var checkListOfPlayers: MutableList<PlayerCheck>
        val adapter = SubmitTeamsAdapter()
        var counter = 0
    }
}