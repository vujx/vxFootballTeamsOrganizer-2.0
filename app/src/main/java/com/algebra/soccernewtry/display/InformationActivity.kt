package com.algebra.soccernewtry.display

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.algebra.soccernewtry.R
import com.algebra.soccernewtry.constants.Constants
import com.algebra.soccernewtry.databinding.ActivityInformationBinding
import com.algebra.soccernewtry.display.information.FragmentInformation
import com.algebra.soccernewtry.display.information.FragmentName
import com.algebra.soccernewtry.navdrawer.NavDrawerList
import com.algebra.soccernewtry.navdrawer.SetupToolbarDrawer
import com.algebra.soccernewtry.player.model.Player

class InformationActivity : AppCompatActivity() {

    lateinit var binding: ActivityInformationBinding
    private val navDrawerListExpandable = NavDrawerList(this)

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityInformationBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        startFragments()
        setupToolbarAndNavigationDrawer()
    }

    fun getPlayer(): Player{
        return intent.getSerializableExtra(Constants.GET_PLAYER_NAME_KEY) as Player
    }

    private fun startFragments(){
        supportFragmentManager.beginTransaction().replace(R.id.frgSettingName, FragmentName.newInstance(getPlayer().name)).commit()
        supportFragmentManager.beginTransaction().replace(R.id.frgOtherInformation, FragmentInformation.newInstance()).commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return SetupToolbarDrawer(this, binding.root).onOptionsItemSelected(item)
    }

    private fun setupToolbarAndNavigationDrawer(){
        SetupToolbarDrawer(this, binding.root).setUpToolbar()
        navDrawerListExpandable.setUpValues()
        navDrawerListExpandable.prepareListData()
    }
}