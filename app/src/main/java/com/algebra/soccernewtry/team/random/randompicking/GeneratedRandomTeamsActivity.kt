package com.algebra.soccernewtry.team.random.randompicking

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.algebra.fuca.team.random.FixPlayers
import com.algebra.soccernewtry.constants.Constants
import com.algebra.soccernewtry.databinding.ActivityGeneratedRandomTeamsBinding
import com.algebra.soccernewtry.dialog.DialogForSubmitTeams
import com.algebra.soccernewtry.game.SubmitTeamsActivity
import com.algebra.soccernewtry.navdrawer.NavDrawerList
import com.algebra.soccernewtry.navdrawer.SetupToolbarDrawer
import com.algebra.soccernewtry.player.main.PlayerViewModel
import com.algebra.soccernewtry.player.model.Player

class GeneratedRandomTeamsActivity : AppCompatActivity() {

    lateinit var binding: ActivityGeneratedRandomTeamsBinding
    private val navDrawerListExpandable = NavDrawerList(this)
    private val selectedPlayers = mutableListOf<Player>()
    private var isItFixList = false
    lateinit var mainListAndTeams: ManipulateMainListAndTeams
    lateinit var randomTeams: RandomMakeTeam
    private val fixPlayers = FixPlayers()
    private val adapterRed = RandomRedTeamAdapter()
    private val adapterBlue = RandomBlueTeamAdapter()
    private val scroll = ScrollSynchronizer()


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityGeneratedRandomTeamsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        setupToolbarAndNavigationDrawer()
        setUpRecyclerView()

        scroll.add(binding.recyclerViewForRedTeams)
        scroll.add(binding.recyclerViewForBlueTeams)

        selectedPlayers.addAll(intent.getSerializableExtra(Constants.GET_LIST_OF_PLAYERS) as List<Player>)
        randomTeams = RandomMakeTeam(selectedPlayers)
        mainListAndTeams = ManipulateMainListAndTeams(selectedPlayers)


        Log.d("Ispis", selectedPlayers.size.toString())
        randomTeams.makeTeams()
        getTeams()
        clickListeners()
        replacePlayers()
        fixRow()
        Log.d("Ispis", selectedPlayers.size.toString())
    }

    private fun setUpRecyclerView() {
        binding.recyclerViewForRedTeams.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewForRedTeams.adapter = adapterRed

        binding.recyclerViewForBlueTeams.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewForBlueTeams.adapter = adapterBlue
    }

    override fun onBackPressed() {
        RandomMakeTeam.lastPlayer =
            Player(-1, "", 0, 0, 0, 0,0,0, 0)
        mainListAndTeams.onBackButtonAction()
        super.onBackPressed()
    }

    private fun clickListeners() {
        binding.btnMakeTeamsAgain.setOnClickListener {
            randomTeams.createTeams()
            randomTeams.makeTeams()
            getTeams()
        }

        binding.btnSubmitTeams.setOnClickListener {
            val checkDialog = DialogForSubmitTeams(redTeam, blueTeam)
            checkDialog.show(supportFragmentManager, "submitTeams2")
            checkDialog.listener = object: DialogForSubmitTeams.Listener{
                override fun checkPlayers(check: Boolean) {
                    if(check){
                        val intent = Intent(this@GeneratedRandomTeamsActivity, SubmitTeamsActivity::class.java)
                        intent.putExtra(Constants.RED_TEAM, blueTeam.filter {
                            it.name.isNotEmpty()
                        } as ArrayList<Player>)
                        intent.putExtra(Constants.BLUE_TEAM, redTeam.filter {
                            it.name.isNotEmpty()
                        } as ArrayList<Player>)
                        startActivity(intent)

                    }
                }
            }
        }
    }

    private fun replacePlayers() {
        adapterRed.listenerReplacement = object : RandomRedTeamAdapter.ListenerForReplacement {
            override fun getReplacement(position: Int) {
                adapterRed.replacePlayerRedTeam(position, blueTeam[position])
                adapterBlue.replacePlayerBlueTeam(position, redTeam[position])
                mainListAndTeams.replacePlayersTeam(position)
                isItFixList = true
                fixPlayers.checkIfFixRowWantToReplacePlayers(position)
                makeScoreForTeams()
            }
        }
    }

    private fun fixRow() {
        adapterBlue.listenerForFixing = object : RandomBlueTeamAdapter.ListenerForFixing {
            override fun fixRow(isChecked: Boolean, position: Int) {
                if (isChecked) {
                    mainListAndTeams.getPositionOfPlayerToRemove(position)
                    fixPlayers.addFixPlayerAfterCheck(position)
                } else {
                    mainListAndTeams.addFixPlayerToMainList(position)
                    val removePosition = fixPlayers.positionToRemoveFixPlayers(position)
                    fixPlayers.removeFixPlayerAfterUncheck(removePosition)
                }
            }
        }
    }

    private fun getTeams() {
        createTeams()
        displayScoreAfterAddingLastPlayerIfOddNumOfPlayers()
        adapterRed.setList(redTeam)
        adapterBlue.setTeams(blueTeam)
    }

    private fun createTeams(){
        randomTeams.createTeams()
        randomTeams.getToZeroSumOfRatingTeams()

        makeScoreForTeams()
        mainListAndTeams.sortTeams()
        fixPlayers.getPositionOfPlayersInTeamAndAddToTeam()
    }

    private fun displayScoreAfterAddingLastPlayerIfOddNumOfPlayers(){
        //randomTeams = RandomMakeTeam(selectedPlayers)
        if(!randomTeams.checkIfItIsOddNum) RandomMakeTeam.lastPlayer =
            Player(-1, "", 0, 0, 0, 0, 0, 0, 0)
        randomTeams.addLastPlayerIfOddNumOfPlayers(randomTeams.checkIfItIsOddNum)
        randomTeams.getToZeroSumOfRatingTeams()
        mainListAndTeams.checkSumOfRatingPlayers(randomTeams)
        displayMatchBalance()
    }

    private fun makeScoreForTeams() {
        randomTeams.getToZeroSumOfRatingTeams()
        mainListAndTeams.checkSumOfRatingPlayers(randomTeams)

        fixPlayers.addingFixPlayersToTeam(randomTeams, isItFixList)

        isItFixList = false
        displayMatchBalance()
    }


    @SuppressLint("SetTextI18n")
    private fun displayMatchBalance(){
        if(randomTeams.sumaCrvenih > randomTeams.sumaPlavih){
            val result = ((randomTeams.sumaPlavih/randomTeams.sumaCrvenih)*100).toString().substring(0,2)
            binding.scorePlayer1.text = "Match balance $result%"
            Log.d("IspisiRez", result)
        } else if(randomTeams.sumaCrvenih < randomTeams.sumaPlavih){
            val result = ((randomTeams.sumaCrvenih/randomTeams.sumaPlavih)*100).toString().substring(0,2)
            binding.scorePlayer1.text = "Match balance $result%"
            Log.d("IspisiRez", result)

        } else {
            binding.scorePlayer1.text = "Match balance 100%"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return SetupToolbarDrawer(this, binding.root).onOptionsItemSelected(item)
    }

    private fun setupToolbarAndNavigationDrawer(){
        SetupToolbarDrawer(this, binding.root).setUpToolbar()
        navDrawerListExpandable.setUpValues()
        navDrawerListExpandable.prepareListData()
    }

    companion object {
        val blueTeam = mutableListOf<Player>()
        val redTeam = mutableListOf<Player>()
    }
}