package com.algebra.soccernewtry

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.algebra.soccernewtry.databinding.ActivityCreateTeamsBinding
import com.algebra.soccernewtry.navdrawer.NavDrawerList
import com.algebra.soccernewtry.navdrawer.SetupToolbarDrawer
import com.algebra.soccernewtry.team.random.own.ChooseYourOwnActivity
import com.algebra.soccernewtry.team.random.randomChoosing.RandomChoosingTeamActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateTeamsActivity : AppCompatActivity() {

    lateinit var binding: ActivityCreateTeamsBinding
    private val navDrawerListExpandable = NavDrawerList(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCreateTeamsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbarAndNavigationDrawer()
        clickListeners()
    }

    private fun setupToolbarAndNavigationDrawer(){
        SetupToolbarDrawer(this, binding.root).setUpToolbar()
        navDrawerListExpandable.setUpValues()
        navDrawerListExpandable.prepareListData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return SetupToolbarDrawer(this, binding.root).onOptionsItemSelected(item)
    }

    private fun clickListeners(){
        binding.btnRandomPicker.setOnClickListener {
            val intent = Intent(this, RandomChoosingTeamActivity::class.java)
            startActivity(intent)
        }

        binding.btnYourPickTeam.setOnClickListener {
            val intent = Intent(this, ChooseYourOwnActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        exitApp(this)
    }
}