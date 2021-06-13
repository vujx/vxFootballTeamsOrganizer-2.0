package com.algebra.soccernewtry.display.historyOfMatch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.algebra.soccernewtry.constants.Constants
import com.algebra.soccernewtry.databinding.ActivityHistoryMatchBinding
import com.algebra.soccernewtry.dialog.DialogCheck
import com.algebra.soccernewtry.display.historyOfMatch.historyOfPlayer.PlayerHistoryActivity
import com.algebra.soccernewtry.exitApp
import com.algebra.soccernewtry.historyOfGame.main.MatchViewModel
import com.algebra.soccernewtry.matchFlow.main.MatchFlowViewModel
import com.algebra.soccernewtry.matchPlayers.main.MatchPlayerViewModel
import com.algebra.soccernewtry.navdrawer.NavDrawerList
import com.algebra.soccernewtry.navdrawer.SetupToolbarDrawer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryMatchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryMatchBinding
    private val navDrawerListExpandable = NavDrawerList(this)
    private val adapter = HistoryOfGameAdapter(this)
    private val viewModelMatch: MatchViewModel by viewModels()
    private val viewModelMatchPlayers: MatchPlayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHistoryMatchBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbarAndNavigationDrawer()
        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        viewModelMatch.getMatchesResults().observe(this, Observer {
            if(it.isEmpty())
                binding.tvDisplay.text = "You haven't played any matches!"
            adapter.setList(it)
            binding.progressBar.visibility = View.GONE
        })
    }

    private fun setupRecyclerView(){
        binding.recyclerViewHistoryOfGame.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewHistoryOfGame.adapter = adapter

        adapter.listener = object: HistoryOfGameAdapter.Listener{
            override fun getDetailsAboutMatch(id: Int) {
                val intent = Intent(this@HistoryMatchActivity ,PlayerHistoryActivity::class.java)
                intent.putExtra(Constants.MATCH_ID, id)
                startActivity(intent)
            }

            override fun deleteMatch(id: Int) {
                val dialog = DialogCheck("Are you sure you want to delete match?")
                dialog.show(supportFragmentManager, "DeleteMatch")
                dialog.listener = object: DialogCheck.Listener{
                    override fun getPress(isPress: Boolean) {
                        if(isPress){
                            viewModelMatch.deleteMatch(id)
                            viewModelMatch.deleteMatchFlowOfMatch(id)
                            viewModelMatchPlayers.deleteMatchPlayersForMatch(id)
                        }
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return SetupToolbarDrawer(this, binding.root).onOptionsItemSelected(item)
    }

    private fun setupToolbarAndNavigationDrawer(){
        binding.toolbar.title = "History"
        SetupToolbarDrawer(this, binding.root).setUpToolbar()
        navDrawerListExpandable.setUpValues()
        navDrawerListExpandable.prepareListData()
    }

    override fun onBackPressed() {
        exitApp(this)
    }
}