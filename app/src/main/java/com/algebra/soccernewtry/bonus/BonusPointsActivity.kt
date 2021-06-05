package com.algebra.soccernewtry.bonus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.algebra.soccernewtry.databinding.ActivityBonusPointsBinding
import com.algebra.soccernewtry.dialog.DialogCheck
import com.algebra.soccernewtry.displayMessage
import com.algebra.soccernewtry.exitApp
import com.algebra.soccernewtry.navdrawer.NavDrawerList
import com.algebra.soccernewtry.navdrawer.SetupToolbarDrawer
import com.algebra.soccernewtry.player.main.PlayerViewModel
import com.algebra.soccernewtry.player.model.Player
import dagger.hilt.android.AndroidEntryPoint
import java.lang.StringBuilder

@AndroidEntryPoint
class BonusPointsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBonusPointsBinding
    private val navDrawerListExpandable = NavDrawerList(this)
    private val adapter = BonusAdapter(this)
    private val viewModelPlayer: PlayerViewModel by viewModels()
    private lateinit var allPlayer: List<Player>

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityBonusPointsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbarAndNavigationDrawer()
        setUpRecyclerView()
        clickListener()
    }

    override fun onResume() {
        super.onResume()
        this.lifecycleScope.launchWhenResumed {
            allPlayer = viewModelPlayer.getAllPlayersForStat().filter {
                it.isDeleted == 0
            }
            val listForBonusPoints = mutableListOf<PlayerForBonus>()
            allPlayer.forEach {
                listForBonusPoints.add(PlayerForBonus(it.name, it.bonusPoints))
            }

            if(allPlayer.isEmpty()) binding.tvDisplay.text = "You don't have any players added!"

            adapter.setList(listForBonusPoints)
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setUpRecyclerView(){
        binding.recyclerViewArrival.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewArrival.adapter = adapter
    }

    private fun clickListener(){
        binding.btnSubmitBonusPoints.setOnClickListener {
            val bonusPlayersDisplay = StringBuilder()
            var countOfBonusPlayers = 0
            bonusPlayersDisplay.append("Are you sure you want to add bonus points to")
            adapter.listOfPlayer.forEach {
                if(it.bonusPoints != 0){
                    countOfBonusPlayers++
                    bonusPlayersDisplay.append(" ${it.name.toUpperCase()} ${it.bonusPoints},")
                }
            }
            bonusPlayersDisplay.replace(bonusPlayersDisplay.length - 1, bonusPlayersDisplay.length, "?")
            if(countOfBonusPlayers == 0){
                bonusPlayersDisplay.clear()
            }
            else if(countOfBonusPlayers < 11){
                val checkBonusPointsDialog = DialogCheck(bonusPlayersDisplay.toString())
                checkBonusPointsDialog.show(supportFragmentManager, "BonusPoints")
                checkBonusPointsDialog.listener = object: DialogCheck.Listener{
                    override fun getPress(isPress: Boolean) {
                        if(isPress){
                            adapter.listOfPlayer.forEach {
                                if(it.bonusPoints != 0){
                                    allPlayer.forEach {player ->
                                        if(it.name == player.name){
                                            player.bonusPoints += it.bonusPoints
                                            viewModelPlayer.addPlayer(player)
                                        }
                                    }
                                    it.bonusPoints = 0
                                }
                            }
                            adapter.notifyDataSetChanged()
                            displayMessage(this@BonusPointsActivity, "Bonus points are added!")
                        }
                    }
                }
            } else {
                displayMessage(this,"You can only add bonus points to 10 players at one time!" )
                countOfBonusPlayers = 0
                bonusPlayersDisplay.clear()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return SetupToolbarDrawer(this, binding.root).onOptionsItemSelected(item)
    }

    private fun setupToolbarAndNavigationDrawer(){
        binding.toolbar.title = "Bonus Points"
        SetupToolbarDrawer(this, binding.root).setUpToolbar()
        navDrawerListExpandable.setUpValues()
        navDrawerListExpandable.prepareListData()
    }

    override fun onBackPressed() {
        exitApp(this)
    }
}