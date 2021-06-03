package com.algebra.soccernewtry.game.teams.red.blue

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.algebra.fuca.game.teams.SubmitTeamsAdapter
import com.algebra.soccernewtry.R
import com.algebra.soccernewtry.constants.Constants
import com.algebra.soccernewtry.databinding.FragmentBlueTeamBinding
import com.algebra.soccernewtry.displayMessage
import com.algebra.soccernewtry.game.PlayerCheck
import com.algebra.soccernewtry.game.SubmitTeamsActivity
import com.algebra.soccernewtry.game.teams.red.red.RedTeamFragment
import com.algebra.soccernewtry.player.model.Player

class BlueTeamFragment : Fragment() {

    private var _binding: FragmentBlueTeamBinding? = null
    private val binding get() = _binding!!

    private var playerNameAssist = ""
    private var playerNameGoalGeter = ""
    private var playerNameAutoGoal = ""
    private var checkAssist = false
    private var checkGolGeter = false
    private var checkAutoGoal = false
    private val blueTeamAction = BlueTeamAction()
    private val dialogs = DialogActionBlueTeam()
    lateinit var blueTeamList:List<Player>
    lateinit var checkListOfPlayers: MutableList<PlayerCheck>
    private val adapter = SubmitTeamsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentBlueTeamBinding.inflate(inflater, container, false)
        blueTeamList = arguments?.getSerializable(Constants.BLUE_TEAM_FRAGMENT) as List<Player>
        setUpRecyclerViewBlue()
        binding.tvScore.text = "${RedTeamFragment.goalRed}:${RedTeamFragment.goalBlue}"
        clickListeners()
        setScore()
        return binding.root
    }

    private fun setUpRecyclerViewBlue(){
        binding.recyclerViewTeamBlue.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerViewTeamBlue.adapter = adapter

        checkListOfPlayers = mutableListOf()
        blueTeamList.forEach {
            checkListOfPlayers.add(PlayerCheck(0, it.name, false, false, false))
        }
        adapter.setList(checkListOfPlayers)

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
        blueTeamList.forEach {
            checkListOfPlayers.add(PlayerCheck(0, it.name, false, false, false))
        }
        (activity as SubmitTeamsActivity).setScoreRed()
        adapter.setList(checkListOfPlayers)
    }


    fun setScore(){
        binding.tvScore.text = "${RedTeamFragment.goalRed}:${RedTeamFragment.goalBlue}"
    }

    private fun clickListeners(){
        binding.btnGoal.setOnClickListener {
            foundAssistAndGoalGeter()
            when{
                checkAssist && checkGolGeter -> dialogs.dialogAssisst(requireActivity(), playerNameAssist, playerNameGoalGeter, blueTeamAction,  binding.root, activity as SubmitTeamsActivity, this)
                checkGolGeter -> dialogs.dialogGoalgetter(requireActivity(), playerNameGoalGeter, blueTeamAction, binding.root, activity as SubmitTeamsActivity, this)
                checkAutoGoal -> dialogs.dialogAutogoal(requireActivity(), playerNameAutoGoal, blueTeamAction, binding.root, activity as SubmitTeamsActivity, this)
                else -> displayMessage(activity as SubmitTeamsActivity, "There must be a goalgetter!")
            }
        }
    }



    companion object {
        @JvmStatic
        fun newInstance(blueTeam: List<Player>) = BlueTeamFragment().apply {
            val argBundle = Bundle()
            argBundle.putSerializable(Constants.BLUE_TEAM_FRAGMENT, blueTeam as ArrayList<Player>)
            this.arguments = argBundle
        }
    }
}