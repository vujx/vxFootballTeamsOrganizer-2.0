package com.algebra.soccernewtry.display.historyOfMatch.historyOfPlayer.editHistory

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.algebra.fuca.game.teams.SubmitTeamsAdapter
import com.algebra.soccernewtry.R
import com.algebra.soccernewtry.display.historyOfMatch.historyOfPlayer.FragmentBlueTeamHistoryOfPlayer
import com.algebra.soccernewtry.display.historyOfMatch.historyOfPlayer.FragmentMatchFlow
import com.algebra.soccernewtry.display.historyOfMatch.historyOfPlayer.FragmentRedTeamHistoryOfPlayers
import com.algebra.soccernewtry.game.PlayerCheck
import com.algebra.soccernewtry.game.history.History
import com.algebra.soccernewtry.game.history.HistoryAdapter

class EditDialogMatchFlow(private val history: History): DialogFragment() {

    private val adapter = SubmitTeamsAdapter()
    private val adapterBlue = SubmitTeamsAdapter()
    private val listOfEditHistory = ListOfEditHistoryMatchFlow()
    private lateinit var setupAndDisplay: SetupWidgetsAndDisplayScoreMatchFlow
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
        setupAndDisplay = SetupWidgetsAndDisplayScoreMatchFlow()
        setupAndDisplay.setUpRecyclerView(view, adapter, adapterBlue, requireContext())
        setUpTeamsResult()

        val alertDialog = setupAndDisplay.setupAlerDialog(view, requireContext())
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            setToDefulat()
            setNewChanges(FragmentRedTeamHistoryOfPlayers.listOfPlayersRed, FragmentBlueTeamHistoryOfPlayer.blueTeamPlayers)
            setOldValueOfAssAndGoalgeter()
            when {
                checkGoalGeter && checkAssisst -> checkAssisstAndGoalgeter(alertDialog)
                checkGoalGeter -> checkGoalgeter(alertDialog)
                checkAutoGol -> checkAutogoal(alertDialog)
                else -> setupAndDisplay.displayErrorMessage(requireContext())
            }
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
        setNewChanges(FragmentRedTeamHistoryOfPlayers.listOfPlayersRed, FragmentBlueTeamHistoryOfPlayer.blueTeamPlayers)
        val manimupalteList = FragmentRedTeamHistoryOfPlayers.listOfPlayersRed
        val manimupateListBlue = FragmentBlueTeamHistoryOfPlayer.blueTeamPlayers
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
            FragmentRedTeamHistoryOfPlayers.listOfPlayersRed,
            FragmentBlueTeamHistoryOfPlayer.blueTeamPlayers
        )
        var goalgetterId = -1
        var assisterId = -1

        FragmentRedTeamHistoryOfPlayers.listOfPlayersRed.forEach {
            if (it.name.toLowerCase() == newGoalGeter.toLowerCase()) goalgetterId = it.id
            if (it.name.toLowerCase() == newAssist.toLowerCase()) assisterId = it.id
        }
        if (goalgetterId != -1)
            FragmentBlueTeamHistoryOfPlayer.blueTeamPlayers.forEach {
                if (it.name.toLowerCase() == newGoalGeter.toLowerCase()) goalgetterId = it.id
                if (it.name.toLowerCase() == newAssist.toLowerCase()) assisterId = it.id
            }
        if (!check) FragmentMatchFlow.adapterHistory.editResult(
            History(history.goalRed, history.goalBlue, newAssist, newGoalGeter,
                history.isRed, history.id, "", goalgetterId, assisterId)
        )
        else {
            if (history.isRed) {
                for (i: Int in HistoryAdapter.positonOfEdit until FragmentMatchFlow.adapterHistory.listOfResults.size) {
                    if (FragmentMatchFlow.adapterHistory.listOfResults[i].goalRed != 0)
                        FragmentMatchFlow.adapterHistory.listOfResults[i].goalRed--
                    FragmentMatchFlow.adapterHistory.listOfResults[i].goalBlue++
                }
                FragmentMatchFlow.adapterHistory.editResult(
                    History(history.goalRed, history.goalBlue, newAssist, newGoalGeter,
                        !history.isRed, history.id, "", goalgetterId, assisterId)
                )
            } else {
                for (i: Int in HistoryAdapter.positonOfEdit until FragmentMatchFlow.adapterHistory.listOfResults.size) {
                    if (FragmentMatchFlow.adapterHistory.listOfResults[i].goalBlue != 0)
                        FragmentMatchFlow.adapterHistory.listOfResults[i].goalBlue--
                    FragmentMatchFlow.adapterHistory.listOfResults[i].goalRed++
                }
                FragmentMatchFlow.adapterHistory.editResult(
                    History(history.goalRed, history.goalBlue, newAssist, newGoalGeter,
                        !history.isRed, history.id, "", goalgetterId, assisterId)
                )
            }
        }

        clearAddToDatabaseAndSetup()
        alertDialog.dismiss()
    }

    private fun checkGoalgeter(alertDialog: AlertDialog) {
        val check = listOfEditHistory.checkIfTeamsGoalsChanges(
            history.isRed,
            FragmentRedTeamHistoryOfPlayers.listOfPlayersRed,
            FragmentBlueTeamHistoryOfPlayer.blueTeamPlayers
        )
        var goalgetterId = 0

        FragmentRedTeamHistoryOfPlayers.listOfPlayersRed.forEach {
            if (it.name.toLowerCase() == newGoalGeter.toLowerCase()) goalgetterId = it.id
        }
        FragmentBlueTeamHistoryOfPlayer.blueTeamPlayers.forEach {
            if (it.name.toLowerCase() == newGoalGeter.toLowerCase()) goalgetterId = it.id
        }

        if (!check) FragmentMatchFlow.adapterHistory.editResult(
            History(history.goalRed, history.goalBlue, "", newGoalGeter,
                history.isRed, history.id, "", goalgetterId, -1)
        )
        else {
            if (history.isRed) {
                for (i: Int in HistoryAdapter.positonOfEdit until FragmentMatchFlow.adapterHistory.listOfResults.size) {
                    if (FragmentMatchFlow.adapterHistory.listOfResults[i].goalRed != 0)
                        FragmentMatchFlow.adapterHistory.listOfResults[i].goalRed--
                    FragmentMatchFlow.adapterHistory.listOfResults[i].goalBlue++
                }
                FragmentMatchFlow.adapterHistory.editResult(
                    History(history.goalRed, history.goalBlue, "", newGoalGeter, !history.isRed, history.id, "",
                        goalgetterId, -1)
                )
            } else {
                for (i: Int in HistoryAdapter.positonOfEdit until FragmentMatchFlow.adapterHistory.listOfResults.size) {
                    if (FragmentMatchFlow.adapterHistory.listOfResults[i].goalBlue != 0)
                        FragmentMatchFlow.adapterHistory.listOfResults[i].goalBlue--
                    FragmentMatchFlow.adapterHistory.listOfResults[i].goalRed++
                }
                FragmentMatchFlow.adapterHistory.editResult(
                    History(history.goalRed, history.goalBlue, "", newGoalGeter, !history.isRed, history.id, "",
                        goalgetterId, -1)
                )
            }
        }
        clearAddToDatabaseAndSetup()
        alertDialog.dismiss()
    }

    private fun checkAutogoal(alertDialog: AlertDialog) {
        val check = listOfEditHistory.checkIfTeamsGoalsChanges(
            history.isRed,
            FragmentRedTeamHistoryOfPlayers.listOfPlayersRed,
            FragmentBlueTeamHistoryOfPlayer.blueTeamPlayers
        )
        var goalgetterId = 0

        FragmentRedTeamHistoryOfPlayers.listOfPlayersRed.forEach {
            if (it.name.toLowerCase() == newAutoGol.toLowerCase()) goalgetterId = it.id
        }
       FragmentBlueTeamHistoryOfPlayer.blueTeamPlayers.forEach {
            if (it.name.toLowerCase() == newAutoGol.toLowerCase()) goalgetterId = it.id
        }

        if (check) FragmentMatchFlow.adapterHistory.editResult(
            History(history.goalRed, history.goalBlue, "", "",
                history.isRed, history.id, newAutoGol, goalgetterId, -1)
        )
        else {
            if (history.isRed) {
                for (i: Int in HistoryAdapter.positonOfEdit until FragmentMatchFlow.adapterHistory.listOfResults.size) {
                    if (FragmentMatchFlow.adapterHistory.listOfResults[i].goalRed != 0)
                        FragmentMatchFlow.adapterHistory.listOfResults[i].goalRed--
                    FragmentMatchFlow.adapterHistory.listOfResults[i].goalBlue++
                }
                FragmentMatchFlow.adapterHistory.editResult(
                    History(history.goalRed, history.goalBlue, "", "", !history.isRed, history.id, newAutoGol,
                        goalgetterId, -1)
                )
            } else {
                for (i: Int in HistoryAdapter.positonOfEdit until FragmentMatchFlow.adapterHistory.listOfResults.size) {
                    if (FragmentMatchFlow.adapterHistory.listOfResults[i].goalBlue != 0)
                        FragmentMatchFlow.adapterHistory.listOfResults[i].goalBlue--
                    FragmentMatchFlow.adapterHistory.listOfResults[i].goalRed++
                }
                FragmentMatchFlow.adapterHistory.editResult(
                    History(history.goalRed, history.goalBlue, "", "", !history.isRed, history.id, newAutoGol,
                        goalgetterId, -1)
                )
            }
        }
        clearAddToDatabaseAndSetup()
        alertDialog.dismiss()
    }

    private fun clearAddToDatabaseAndSetup() {
        FragmentRedTeamHistoryOfPlayers.listOfPlayersRed.forEach {
            it.isAssist = false
            it.isGoalgeter = false
            it.isAutoGol = false
        }
        FragmentBlueTeamHistoryOfPlayer.blueTeamPlayers.forEach {
            it.isAutoGol = false
            it.isGoalgeter = false
            it.isAssist = false
        }
    }
}