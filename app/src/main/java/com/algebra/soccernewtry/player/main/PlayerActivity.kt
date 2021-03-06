package com.algebra.soccernewtry.player.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.algebra.fuca.player.adding.PlayerAdapter
import com.algebra.soccernewtry.constants.Constants
import com.algebra.soccernewtry.databinding.ActivityPlayerBinding
import com.algebra.soccernewtry.dialog.DialogCheck
import com.algebra.soccernewtry.display.InformationActivity
import com.algebra.soccernewtry.exitApp
import com.algebra.soccernewtry.navdrawer.NavDrawerList
import com.algebra.soccernewtry.navdrawer.SetupToolbarDrawer
import com.algebra.soccernewtry.player.addplayer.DialogForAddingPlayer
import com.algebra.soccernewtry.player.editPlayer.DialogForEditPlayer
import com.algebra.soccernewtry.player.model.Player
import dagger.hilt.android.AndroidEntryPoint
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView

@AndroidEntryPoint
class PlayerActivity : AppCompatActivity() {

    lateinit var binding: ActivityPlayerBinding
    private val viewModel: PlayerViewModel by viewModels()
    private val navDrawerListExpandable = NavDrawerList(this)
    private val adapter = PlayerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbarAndNavigationDrawer()
        clickListener()
        setUpRecyclerView()
        showView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllPlayers().observe(this, Observer {
            val listWithoutDeletedPlayers = it.filter {
                it.isDeleted == 0
            }
            if (listWithoutDeletedPlayers.isEmpty()) binding.tvDisplay.text = "You don't have any players added!"
            else binding.tvDisplay.text = ""
            adapter.setList(it.filter {
                it.isDeleted == 0
            })
            Log.d("dajdaovovidim", it.toString())
            binding.progressBar.visibility = View.GONE
        })
    }

    private fun setUpRecyclerView(){
        binding.reyclerViewAllPlayers.layoutManager = LinearLayoutManager(this)
        binding.reyclerViewAllPlayers.adapter = adapter
        adapter.listener = object: PlayerAdapter.Listener{
            override fun deletePlayer(player: Player) {
                val dialog = DialogCheck("Are you sure you want to delete ${player.name.toUpperCase()} from team?")
                dialog.show(supportFragmentManager, "Remove player")
                dialog.listener = object: DialogCheck.Listener{
                    override fun getPress(isPress: Boolean) {
                        player.isDeleted = 1
                        viewModel.addPlayer(player)
                    }
                }
            }

            override fun getInformationAboutPlayer(player: Player) {
                val intent = Intent(this@PlayerActivity, InformationActivity::class.java)
                intent.putExtra(Constants.GET_PLAYER_NAME_KEY, player)
                startActivity(intent)
            }

            override fun getUpdatePlayer(player: Player) {
                val dialog = DialogForEditPlayer(player, "Edit")
                dialog.show(supportFragmentManager, "VIDIS")
                dialog.listenerGetChangePlayer = object: DialogForEditPlayer.Listener{
                    override fun getChangedPlayer(player: Player) {
                        viewModel.addPlayer(player)
                    }
                }
            }
        }
    }

    private fun clickListener(){
        binding.btnAddInRecaclerView.setOnClickListener {
            val dialog = DialogForAddingPlayer()
            dialog.show(supportFragmentManager, "AddingPlayers")
            dialog.listenerGetChangePlayer = object: DialogForAddingPlayer.Listener{
                override fun addNewPlayer(player: Player) {
                    viewModel.addPlayer(player)
                }
            }
        }
    }

    private fun setupToolbarAndNavigationDrawer(){
        SetupToolbarDrawer(this, binding.root).setUpToolbar()
        navDrawerListExpandable.setUpValues()
        navDrawerListExpandable.prepareListData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return SetupToolbarDrawer(this, binding.root).onOptionsItemSelected(item)
    }

    private fun showView(){
        // single example
        MaterialShowcaseView.Builder(this)
            .setTarget(binding.btnAddInRecaclerView)
            .setDismissText("GOT IT")
            .setContentText("Button for creating new players!")
            .singleUse(Constants.SHOWCASE_ID_PLAYER) // provide a unique ID used to ensure it is only shown once
            .show()


    }

    override fun onBackPressed() {
        exitApp(this)
    }
}
