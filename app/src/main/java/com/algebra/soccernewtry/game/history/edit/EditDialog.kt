package com.algebra.soccernewtry.game.history.edit

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.algebra.fuca.game.teams.SubmitTeamsAdapter
import com.algebra.soccernewtry.R
import com.algebra.soccernewtry.game.PlayerCheck
import com.algebra.soccernewtry.game.SubmitTeamsActivity
import com.algebra.soccernewtry.game.history.History
import com.algebra.soccernewtry.game.history.HistoryAdapter
import com.algebra.soccernewtry.game.teams.blue.BlueTeamFragment
import com.algebra.soccernewtry.game.teams.red.RedTeamFragment

class EditDialog(private val history: History) : DialogFragment() {

    private val adapter = SubmitTeamsAdapter()
    private val adapterBlue = SubmitTeamsAdapter()
    private val listOfEditHistory = ListOfEditHistory()
    private lateinit var setupAndDisplay: SetupWidgetsAndDisplayScore
    private var checkAssisst = false
    private var checkGoalGeter = false
    private var checkAutoGol = false
    private var newAssist = ""
    private var newGoalGeter = ""
    private var newAutoGol = ""
    private var oldAssisst = ""
    private var oldGoalGeter = ""
    private var oldAutoGoal = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_for_edit_history, null)
        setupAndDisplay =
            SetupWidgetsAndDisplayScore(activity as SubmitTeamsActivity, requireActivity())
        setupAndDisplay.setUpRecyclerView(view, adapter, adapterBlue, requireContext())
        setUpTeamsResult()

        val alertDialog = setupAndDisplay.setupAlerDialog(view, requireContext())
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            setToDefulat()
            setNewChanges(RedTeamFragment.checkListOfPlayers, BlueTeamFragment.checkListOfPlayers)
            setOldValueOfAssAndGoalgeter()
            when {
                checkGoalGeter && checkAssisst -> checkAssisstAndGoalgeter(alertDialog)
                checkGoalGeter -> checkGoalgeter(alertDialog)
                checkAutoGol -> checkAutogoal(alertDialog)
                else -> setupAndDisplay.displayErrorMessage(requireContext())
            }
        }
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener {
            clearAddToDatabaseAndSetup()
            alertDialog.dismiss()
        }
        alertDialog.setOnDismissListener {
            clearAddToDatabaseAndSetup()
        }
        return alertDialog
    }

    private fun setToDefulat() {
        checkAssisst = false
        checkGoalGeter = false
        checkAutoGol = false
    }

    private fun setOldValueOfAssAndGoalgeter() {
        if (!checkAssisst) {
            oldAssisst = newAssist
            newAssist = ""
        }
        if (!checkGoalGeter) {
            oldGoalGeter = newGoalGeter
            newGoalGeter = ""
        }
        if (!checkAutoGol) {
            oldAutoGoal = newAutoGol
            newAutoGol = ""
        }
    }

    private fun setUpTeamsResult() {
        setToDefulat()
        setNewChanges(RedTeamFragment.checkListOfPlayers, BlueTeamFragment.checkListOfPlayers)
        val manimupalteList = RedTeamFragment.checkListOfPlayers
        val manimupateListBlue = BlueTeamFragment.checkListOfPlayers
        manimupalteList.forEach {
            if (it.name == history.assisst) {
                it.isAssist = true
                checkAssisst = true
                newAssist = it.name
            } else if (it.name == history.goalGeter) {
                it.isGoalgeter = true
                checkGoalGeter = true
                newGoalGeter = it.name
            } else if (it.name == history.autoGoal) {
                it.isAutoGol = true
                checkAutoGol = true
                newAutoGol = it.name
            } else {
                it.isAutoGol = false
                it.isGoalgeter = false
                it.isAssist = false
            }
        }

        manimupateListBlue.forEach {
            if (it.name == history.assisst) {
                it.isAssist = true
                checkAssisst = true
                newAssist = it.name
            } else if (it.name == history.goalGeter) {
                it.isGoalgeter = true
                checkGoalGeter = true
                newGoalGeter = it.name
            } else if (it.name == history.autoGoal) {
                it.isAutoGol = true
                checkAutoGol = true
                newAutoGol = it.name
            } else {
                it.isAutoGol = false
                it.isGoalgeter = false
                it.isAssist = false
            }
        }
        adapter.setList(manimupalteList)
        adapterBlue.setList(manimupateListBlue)
        setNewChanges(manimupalteList, manimupateListBlue)
        listOfEditHistory.setCheckBoxAfterClickRedTeam(
            adapter,
            adapterBlue,
            manimupalteList,
            manimupateListBlue
        )
        listOfEditHistory.setCheckBoxAfterClick(
            adapter,
            adapterBlue,
            manimupalteList,
            manimupateListBlue
        )
    }

    private fun setNewChanges(list: List<PlayerCheck>, listOfBlue: List<PlayerCheck>) {
        list.forEach { player ->
            settingNewAssistantGoalgetter(player)
        }
        listOfBlue.forEach { player ->
            settingNewAssistantGoalgetter(player)
        }
    }

    private fun settingNewAssistantGoalgetter(player: PlayerCheck) {

        when {
            player.isAssist -> {
                checkAssisst = true
                newAssist = player.name
            }
            player.isGoalgeter -> {
                checkGoalGeter = true
                newGoalGeter = player.name
            }
            player.isAutoGol -> {
                checkAutoGol = true
                newAutoGol = player.name
            }
        }
    }

    private fun checkAssisstAndGoalgeter(alertDialog: AlertDialog) {
        val check = listOfEditHistory.checkIfTeamsGoalsChanges(
            history.isRed,
            RedTeamFragment.checkListOfPlayers,
            BlueTeamFragment.checkListOfPlayers
        )
        var goalgetterId = -1
        var assisterId = -1

        RedTeamFragment.checkListOfPlayers.forEach {
            if (it.name.toLowerCase() == newGoalGeter.toLowerCase()) goalgetterId = it.id
            if (it.name.toLowerCase() == newAssist.toLowerCase()) assisterId = it.id
        }
        BlueTeamFragment.checkListOfPlayers.forEach {
                if (it.name.toLowerCase() == newGoalGeter.toLowerCase()) goalgetterId = it.id
                if (it.name.toLowerCase() == newAssist.toLowerCase()) assisterId = it.id
        }
        if (!check) SubmitTeamsActivity.adapterHistory.editResult(
            History(history.goalRed, history.goalBlue, newAssist, newGoalGeter,
                history.isRed, 0, "", goalgetterId, assisterId))
        else {
            if (history.isRed) {
                for (i: Int in HistoryAdapter.positonOfEdit until SubmitTeamsActivity.adapterHistory.listOfResults.size) {
                    if (SubmitTeamsActivity.adapterHistory.listOfResults[i].goalRed != 0)
                        SubmitTeamsActivity.adapterHistory.listOfResults[i].goalRed--
                    SubmitTeamsActivity.adapterHistory.listOfResults[i].goalBlue++
                }
                SubmitTeamsActivity.adapterHistory.editResult(
                    History(history.goalRed, history.goalBlue, newAssist, newGoalGeter,
                        !history.isRed, 0, "", goalgetterId, assisterId))
            } else {
                for (i: Int in HistoryAdapter.positonOfEdit until SubmitTeamsActivity.adapterHistory.listOfResults.size) {
                    if (SubmitTeamsActivity.adapterHistory.listOfResults[i].goalBlue != 0)
                        SubmitTeamsActivity.adapterHistory.listOfResults[i].goalBlue--
                    SubmitTeamsActivity.adapterHistory.listOfResults[i].goalRed++
                }
                SubmitTeamsActivity.adapterHistory.editResult(
                    History(history.goalRed, history.goalBlue, newAssist, newGoalGeter,
                        !history.isRed, 0, "", goalgetterId, assisterId))
            }
        }

        clearAddToDatabaseAndSetup()
        alertDialog.dismiss()
    }

    private fun checkGoalgeter(alertDialog: AlertDialog) {
        val check = listOfEditHistory.checkIfTeamsGoalsChanges(
            history.isRed,
            RedTeamFragment.checkListOfPlayers,
            BlueTeamFragment.checkListOfPlayers
        )
        var goalgetterId = 0

        RedTeamFragment.checkListOfPlayers.forEach {
            if (it.name.toLowerCase() == newGoalGeter.toLowerCase()) goalgetterId = it.id
        }
        BlueTeamFragment.checkListOfPlayers.forEach {
            if (it.name.toLowerCase() == newGoalGeter.toLowerCase()) goalgetterId = it.id
        }

        if (!check) SubmitTeamsActivity.adapterHistory.editResult(
            History(history.goalRed, history.goalBlue, "", newGoalGeter,
                history.isRed, 0, "", goalgetterId, -1))
        else {
            if (history.isRed) {
                for (i: Int in HistoryAdapter.positonOfEdit until SubmitTeamsActivity.adapterHistory.listOfResults.size) {
                    if (SubmitTeamsActivity.adapterHistory.listOfResults[i].goalRed != 0)
                        SubmitTeamsActivity.adapterHistory.listOfResults[i].goalRed--
                    SubmitTeamsActivity.adapterHistory.listOfResults[i].goalBlue++
                }
                SubmitTeamsActivity.adapterHistory.editResult(
                    History(history.goalRed, history.goalBlue, "", newGoalGeter, !history.isRed, 0, "",
                        goalgetterId, -1))
            } else {
                for (i: Int in HistoryAdapter.positonOfEdit until SubmitTeamsActivity.adapterHistory.listOfResults.size) {
                    if (SubmitTeamsActivity.adapterHistory.listOfResults[i].goalBlue != 0)
                        SubmitTeamsActivity.adapterHistory.listOfResults[i].goalBlue--
                    SubmitTeamsActivity.adapterHistory.listOfResults[i].goalRed++
                }
                SubmitTeamsActivity.adapterHistory.editResult(
                    History(history.goalRed, history.goalBlue, "", newGoalGeter, !history.isRed, 0, "",
                        goalgetterId, -1))
            }
        }
        clearAddToDatabaseAndSetup()
        alertDialog.dismiss()
    }

    private fun checkAutogoal(alertDialog: AlertDialog) {
        val check = listOfEditHistory.checkIfTeamsGoalsChanges(
            history.isRed,
            RedTeamFragment.checkListOfPlayers,
            BlueTeamFragment.checkListOfPlayers
        )
        var goalgetterId = 0

        RedTeamFragment.checkListOfPlayers.forEach {
            if (it.name.toLowerCase() == newAutoGol.toLowerCase()) goalgetterId = it.id
        }
        BlueTeamFragment.checkListOfPlayers.forEach {
            if (it.name.toLowerCase() == newAutoGol.toLowerCase()) goalgetterId = it.id
        }

        if (check) SubmitTeamsActivity.adapterHistory.editResult(
            History(history.goalRed, history.goalBlue, "", "",
                history.isRed, 0, newAutoGol, goalgetterId, -1))
        else {
            if (history.isRed) {
                for (i: Int in HistoryAdapter.positonOfEdit until SubmitTeamsActivity.adapterHistory.listOfResults.size) {
                    if (SubmitTeamsActivity.adapterHistory.listOfResults[i].goalRed != 0)
                        SubmitTeamsActivity.adapterHistory.listOfResults[i].goalRed--
                    SubmitTeamsActivity.adapterHistory.listOfResults[i].goalBlue++
                }
                SubmitTeamsActivity.adapterHistory.editResult(
                    History(history.goalRed, history.goalBlue, "", "", !history.isRed, 0, newAutoGol,
                        goalgetterId, -1))
            } else {
                for (i: Int in HistoryAdapter.positonOfEdit until SubmitTeamsActivity.adapterHistory.listOfResults.size) {
                    if (SubmitTeamsActivity.adapterHistory.listOfResults[i].goalBlue != 0)
                        SubmitTeamsActivity.adapterHistory.listOfResults[i].goalBlue--
                    SubmitTeamsActivity.adapterHistory.listOfResults[i].goalRed++
                }
                SubmitTeamsActivity.adapterHistory.editResult(
                    History(history.goalRed, history.goalBlue, "", "", !history.isRed, 0, newAutoGol,
                        goalgetterId, -1))
            }
        }
        clearAddToDatabaseAndSetup()
        alertDialog.dismiss()
    }

    private fun clearAddToDatabaseAndSetup() {
        setupAndDisplay.setUpForFragmentsScore()
        RedTeamFragment.checkListOfPlayers.forEach {
            it.isAssist = false
            it.isGoalgeter = false
            it.isAutoGol = false
        }
        BlueTeamFragment.checkListOfPlayers.forEach {
            it.isAutoGol = false
            it.isGoalgeter = false
            it.isAssist = false
        }
    }

}