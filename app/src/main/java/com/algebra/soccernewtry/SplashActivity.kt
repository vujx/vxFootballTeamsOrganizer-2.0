package com.algebra.soccernewtry

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.algebra.soccernewtry.constants.Constants
import com.algebra.soccernewtry.databinding.ActivitySplashBinding
import com.algebra.soccernewtry.game.SubmitTeamsActivity
import com.algebra.soccernewtry.player.main.PlayerViewModel
import com.algebra.soccernewtry.player.model.Player
import com.algebra.soccernewtry.stateactivity.main.StateActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding
    private val viewModel: StateActivityViewModel by viewModels()
    private val viewModelPlayers: PlayerViewModel by viewModels()
    var checkIfPause = false

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        bind()
    }

    override fun onPause() {
        super.onPause()
        checkIfPause = true
    }

    private fun bind() {

        viewModel.getStateOfActivity().observe(this , Observer {
            if (it.isNotEmpty() && it[0].isEndedGame == 1) {
                val redTeam = mutableListOf<Player>()
                val blueTeam = mutableListOf<Player>()
                var counter = 0
                viewModelPlayers.getAllPlayers().observe(this, Observer {listOfPlayer ->
                    listOfPlayer.forEach {
                        if(it.isPlaying == 1 && it.isDeleted != 1 && it.teamId == 1){
                            redTeam.add(it)
                            counter++
                        } else if(it.isPlaying == 1 && it.isDeleted != 1 && it.teamId == 2){
                            blueTeam.add(it)
                            counter++
                        } else counter++

                        if(listOfPlayer.size == counter) startMatch(redTeam, blueTeam)
                    }
                })
            }
            else {
                if(!checkIfPause){
                    val intent = Intent(this, CreateTeamsActivity::class.java)
                    startActivity(intent)
                }
            }
        })
    }

    private fun startMatch(redTeam: List<Player>, blueTeam: List<Player>){
        val intent = Intent(this, SubmitTeamsActivity::class.java)
        intent.putExtra(Constants.BLUE_TEAM, blueTeam as ArrayList<Player>)
        intent.putExtra(Constants.RED_TEAM, redTeam as ArrayList<Player>)
        startActivity(intent)
    }
}