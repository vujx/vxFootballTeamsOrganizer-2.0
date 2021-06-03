package com.algebra.soccernewtry

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.algebra.soccernewtry.databinding.ActivitySplashBinding
import com.algebra.soccernewtry.game.SubmitTeamsActivity
import com.algebra.soccernewtry.stateactivity.main.StateActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding
    private val viewModel: StateActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        bind()
    }

    private fun bind(){
        viewModel.getStateOfActivity().observe(this, Observer {
            Log.d("ispisda", it.toString())
            if(it.isNotEmpty() && it[0].isEndedGame == 1){
                    val intent = Intent(this, SubmitTeamsActivity::class.java)
                    startActivity(intent)
                } else {
                val intent = Intent(this, CreateTeamsActivity::class.java)
                startActivity(intent)
            }
        })
    }
}