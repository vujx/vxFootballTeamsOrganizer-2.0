package com.algebra.soccernewtry.team.random.own

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.algebra.fuca.player.adding.PlayerAdapter
import com.algebra.soccernewtry.constants.Constants
import com.algebra.soccernewtry.databinding.ActivityChooseYourOwnBinding
import com.algebra.soccernewtry.dialog.DialogForSubmitTeams
import com.algebra.soccernewtry.displayMessage
import com.algebra.soccernewtry.game.SubmitTeamsActivity
import com.algebra.soccernewtry.navdrawer.NavDrawerList
import com.algebra.soccernewtry.navdrawer.SetupToolbarDrawer
import com.algebra.soccernewtry.player.addplayer.DialogForAddingPlayer
import com.algebra.soccernewtry.player.main.PlayerViewModel
import com.algebra.soccernewtry.player.model.Player
import dagger.hilt.android.AndroidEntryPoint
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig

@AndroidEntryPoint
class ChooseYourOwnActivity : AppCompatActivity() {

    lateinit var binding: ActivityChooseYourOwnBinding
    private val navDrawerListExpandable = NavDrawerList(this)
    private val viewModel: PlayerViewModel by viewModels()
    private val adapter = TeamsAdapter()
    val blueTeam = mutableListOf<Player>()
    val redTeam = mutableListOf<Player>()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityChooseYourOwnBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbarAndNavigationDrawer()
        bind()
        clickListener()
        setUpRecyclerView()
        showCaseView()
    }

    private fun setUpRecyclerView(){
        binding.recyclerViewForChoosingYourOwnTeam.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewForChoosingYourOwnTeam.adapter = adapter

        adapter.listenerForChoosenTeam = object: TeamsAdapter.ListenerForCheckBox{
            override fun chooseRedTeam(IsCheck: Boolean, player: Player) {
                if(IsCheck){
                    player.teamId = 1
                    viewModel.addPlayer(player)
                } else {
                    if(player.teamId == 1){
                        player.teamId = 0
                        viewModel.addPlayer(player)
                    }
                }
            }

            override fun chooseBlueTeam(IsCheck: Boolean, player: Player) {
                if(IsCheck) {
                    player.teamId = 2
                    viewModel.addPlayer(player)
                } else {
                    if(player.teamId == 2){
                        player.teamId = 0
                        viewModel.addPlayer(player)
                    }
                }
            }
        }
    }

    private fun bind() {
        viewModel.getAllPlayers().observe(this, Observer {
            val listWithoutDeletedPlayers = it.filter {
                it.isDeleted == 0
            }
            if (listWithoutDeletedPlayers.isEmpty()) binding.tvDisplay.text = "You don't have any players added!"
            else binding.tvDisplay.text = ""
            adapter.setList(it.filter {
                it.isDeleted == 0
            })
            redTeam.clear()
            blueTeam.clear()
            var countPlayerRed = 0
            var countPlayerBlue = 0
            it.forEach {
                if (it.teamId == 1 && it.isDeleted != 1) {
                    countPlayerRed++
                    redTeam.add(it)
                }
                else if(it.teamId == 2 && it.isDeleted != 1){
                    countPlayerBlue++
                    blueTeam.add(it)
                }
            }

            binding.tvRedTeamsPlayers.text = "Red team players $countPlayerRed"
            binding.tvBlueTeamsPlayers.text = "Blue team players $countPlayerBlue"
            binding.progressBar.visibility = View.GONE
        })
    }

    private fun clickListener() {
        binding.btnAddInRecaclerView.setOnClickListener {
            if(PlayerAdapter.listOfPlayers.isEmpty())
                PlayerAdapter.listOfPlayers.addAll(adapter.getListOfPlayers())
            val dialog = DialogForAddingPlayer()
            dialog.show(supportFragmentManager, "Adding")
            dialog.listenerGetChangePlayer = object : DialogForAddingPlayer.Listener {
                override fun addNewPlayer(player: Player) {
                    viewModel.addPlayer(player)
                    PlayerAdapter.listOfPlayers.add(player)
                }
            }
        }
        binding.btnSubmitTeams.setOnClickListener {
            if(redTeam.size < 1 || blueTeam.size < 1) displayMessage(this, "Please select at least one player per team")
            else{
                val dialog = DialogForSubmitTeams(redTeam.sortedBy {
                    it.name.toLowerCase()
                }, blueTeam.sortedBy {
                    it.name.toLowerCase()
                })
                dialog.show(supportFragmentManager, "submitteams")
                dialog.listener = object: DialogForSubmitTeams.Listener{
                    override fun checkPlayers(check: Boolean) {
                        if(check){
                            val intent = Intent(this@ChooseYourOwnActivity, SubmitTeamsActivity::class.java)
                            intent.putExtra(Constants.BLUE_TEAM, blueTeam as ArrayList<Player>)
                            intent.putExtra(Constants.RED_TEAM, redTeam as ArrayList<Player>)
                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }

    private fun setupToolbarAndNavigationDrawer() {
        SetupToolbarDrawer(this, binding.root).setUpToolbar()
        navDrawerListExpandable.setUpValues()
        navDrawerListExpandable.prepareListData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return SetupToolbarDrawer(this, binding.root).onOptionsItemSelected(item)
    }

    private fun showCaseView(){
        val config = ShowcaseConfig()

        val sequence = MaterialShowcaseSequence(this, Constants.SHOWCASE_ID_OWN)
        sequence.setConfig(config)
        sequence.addSequenceItem(
            MaterialShowcaseView.Builder(this)
                .setSkipText("SKIP")
                .setTarget(binding.btnAddInRecaclerView)
                .setDismissText("GOT IT")
                .setContentText("Button for creating new players!")
                .build()
        )

        sequence.addSequenceItem(
            MaterialShowcaseView.Builder(this)
                .setSkipText("SKIP")
                .setTarget(binding.btnSubmitTeams)
                .setShapePadding(16)
                .setDismissText("GOT IT")
                .setContentText("Button for submitting your own teams!")
                .withRectangleShape()
                .setShapePadding(16)
                .build()
        )
        sequence.start()
    }
}