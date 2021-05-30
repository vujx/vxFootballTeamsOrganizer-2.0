package com.algebra.soccernewtry.team.random.own

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.algebra.soccernewtry.databinding.ActivityChooseYourOwnBinding
import com.algebra.soccernewtry.navdrawer.NavDrawerList
import com.algebra.soccernewtry.navdrawer.SetupToolbarDrawer
import com.algebra.soccernewtry.player.addplayer.DialogForAddingPlayer
import com.algebra.soccernewtry.player.main.PlayerViewModel
import com.algebra.soccernewtry.player.model.Player
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseYourOwnActivity : AppCompatActivity() {

    lateinit var binding: ActivityChooseYourOwnBinding
    private val navDrawerListExpandable = NavDrawerList(this)
    private val viewModel: PlayerViewModel by viewModels()
    private val adapter = TeamsAdapter()
    val blueTeam = mutableListOf<Player>()
    val redTeam = mutableListOf<Player>()
    var playerName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityChooseYourOwnBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbarAndNavigationDrawer()
        bind()
        clickListener()
        setUpRecyclerView()
    }

    private fun setUpRecyclerView(){
        binding.recyclerViewForChoosingYourOwnTeam.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewForChoosingYourOwnTeam.adapter = adapter

        adapter.listenerForChoosenTeam = object: TeamsAdapter.ListenerForCheckBox{
            override fun chooseRedTeam(IsCheck: Boolean, player: Player) {
                if(IsCheck){
                    playerName = player.name
                    player.teamId = 1
                    player.isPlaying = 1
                    redTeam.add(player)
                    viewModel.addPlayer(player)
                } else {
                    if(playerName != player.name){
                        player.teamId = 0
                        player.isPlaying = 0
                        redTeam.remove(player)
                        viewModel.addPlayer(player)
                    } else playerName = ""
                }
            }

            override fun chooseBlueTeam(IsCheck: Boolean, player: Player) {
                if(IsCheck) {
                    playerName = player.name
                    player.teamId = 2
                    player.isPlaying = 1
                    viewModel.addPlayer(player)
                    blueTeam.add(player)
                } else {
                    if(playerName != player.name){
                        player.teamId = 0
                        player.isPlaying = 0
                        viewModel.addPlayer(player)
                        blueTeam.remove(player)
                    } else playerName = ""
                }
            }
        }
    }

    private fun bind() {
        viewModel.getAllPlayers().observe(this, Observer {
            if (it.isEmpty()) binding.tvDisplay.text = "You don't have any players added!"
            else binding.tvDisplay.text = ""
            adapter.setList(it)
            var countPlayerRed = 0
            var countPlayerBlue = 0
            it.forEach {
                if (it.teamId == 1) countPlayerRed++
                else if(it.teamId == 2) countPlayerBlue++
            }
            binding.tvRedTeamsPlayers.text = "Red team players $countPlayerRed"
            binding.tvBlueTeamsPlayers.text = "Blue team players $countPlayerBlue"
            binding.progressBar.visibility = View.GONE
        })
    }

    private fun clickListener() {
        binding.btnAddInRecaclerView.setOnClickListener {
            val dialog = DialogForAddingPlayer()
            dialog.show(supportFragmentManager, "Adding")
            dialog.listenerGetChangePlayer = object : DialogForAddingPlayer.Listener {
                override fun addNewPlayer(player: Player) {
                    viewModel.addPlayer(player)
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
}