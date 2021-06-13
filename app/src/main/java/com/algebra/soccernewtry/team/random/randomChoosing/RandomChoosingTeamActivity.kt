package com.algebra.soccernewtry.team.random.randomChoosing

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
import com.algebra.soccernewtry.databinding.ActivityRandomChoosingTeamBinding
import com.algebra.soccernewtry.displayMessage
import com.algebra.soccernewtry.navdrawer.NavDrawerList
import com.algebra.soccernewtry.navdrawer.SetupToolbarDrawer
import com.algebra.soccernewtry.player.addplayer.DialogForAddingPlayer
import com.algebra.soccernewtry.player.main.PlayerViewModel
import com.algebra.soccernewtry.player.model.Player
import com.algebra.soccernewtry.team.random.randompicking.GeneratedRandomTeamsActivity
import dagger.hilt.android.AndroidEntryPoint
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig

@AndroidEntryPoint
class RandomChoosingTeamActivity : AppCompatActivity() {

    lateinit var binding: ActivityRandomChoosingTeamBinding
    private val navDrawerListExpandable = NavDrawerList(this)
    private val adapter = RandomTeamAdapter()
    private val viewModel: PlayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRandomChoosingTeamBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbarAndNavigationDrawer()
        setUpRecyclerView()
        bind()
        clickListener()
        showView()
    }

    private fun setUpRecyclerView(){
        binding.reyclerViewAllPlayers.layoutManager = LinearLayoutManager(this)
        binding.reyclerViewAllPlayers.adapter = adapter

        adapter.listener = object: RandomTeamAdapter.Listener {
            override fun getPlayerCheckToPlay(player: Player) {
                viewModel.addPlayer(player)
            }

            override fun getPlayerUncheckToPlay(player: Player) {
                viewModel.addPlayer(player)
            }

        }
    }

    private fun bind(){
        viewModel.getAllPlayers().observe(this, Observer {
            val listWithoutDeletedPlayers = it.filter {
                it.isDeleted == 0
            }
            if (listWithoutDeletedPlayers.isEmpty()) binding.tvDisplay.text = "You don't have any players added!"
            else binding.tvDisplay.text = ""
            adapter.setList(it.filter {
                it.isDeleted == 0
            })
            var countPlayerSelected = 0
            it.forEach {
               if(it.isPlaying == 1 && it.isDeleted != 1) countPlayerSelected++
            }
            binding.tvSelectedPlayers.text = "Player selected $countPlayerSelected"
            binding.progressBar.visibility = View.GONE
        })
    }

    private fun clickListener(){
        binding.btnAddInRecaclerView.setOnClickListener {
            if(PlayerAdapter.listOfPlayers.isEmpty())
                PlayerAdapter.listOfPlayers.addAll(adapter.getListOfPlayers())
            val dialog = DialogForAddingPlayer()
            dialog.show(supportFragmentManager, "Adding")
            dialog.listenerGetChangePlayer = object: DialogForAddingPlayer.Listener{
                override fun addNewPlayer(player: Player) {
                    viewModel.addPlayer(player)
                    PlayerAdapter.listOfPlayers.add(player)
                }
            }
        }

        binding.btnRandomPicker.setOnClickListener  {
            if(adapter.getListOfPlayers().size >= 2){
                val intent = Intent(this, GeneratedRandomTeamsActivity::class.java)
                val selectedPlayers = adapter.getListOfPlayers().filter {
                    it.isPlaying == 1
                }
                intent.putExtra(Constants.GET_LIST_OF_PLAYERS, selectedPlayers as ArrayList<Player>)
                startActivity(intent)
            } else displayMessage(this, "Please select at least two players!")
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

    private fun showView(){
        val config = ShowcaseConfig()

        val sequence = MaterialShowcaseSequence(this, Constants.SHOWCASE_ID_RANDOM)
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
                .setTarget(binding.btnRandomPicker)
                .setShapePadding(16)
                .setDismissText("GOT IT")
                .setContentText("Button that generates random teams!")
                .withRectangleShape()
                .setShapePadding(16)
                .build()
        )
        sequence.start()
    }
}