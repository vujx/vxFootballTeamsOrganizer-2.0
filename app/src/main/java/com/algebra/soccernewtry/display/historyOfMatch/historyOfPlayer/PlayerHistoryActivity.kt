package com.algebra.soccernewtry.display.historyOfMatch.historyOfPlayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.algebra.soccernewtry.databinding.ActivityPlayerBinding
import com.algebra.soccernewtry.databinding.ActivityPlayerHistoryBinding
import com.algebra.soccernewtry.navdrawer.NavDrawerList
import com.algebra.soccernewtry.navdrawer.SetupToolbarDrawer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayerHistoryActivity : AppCompatActivity() {


    private lateinit var binding: ActivityPlayerHistoryBinding
    private val navDrawerListExpandable = NavDrawerList(this)
    private lateinit var pagerAdapter: SlidePagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPlayerHistoryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        matchId = intent.getIntExtra("d", 0)
        setUpViewPager()
        setupToolbarAndNavigationDrawer()
    }

    private fun setUpViewPager(){
        pagerAdapter = SlidePagerAdapter(supportFragmentManager)
        val fragments = listOf(
            FragmentRedTeamHistoryOfPlayers.newInstance(),
            FragmentBlueTeamHistoryOfPlayer.newInstance(),
            FragmentMatchFlow.newInstance()
        )
        pagerAdapter.setPages(fragments)
        binding.viewPager.adapter = pagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    private inner class SlidePagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm){

        private val fragments = mutableListOf<Fragment>()

        fun setPages(listOfFragments: List<Fragment>){
            fragments.clear()
            fragments.addAll(listOfFragments)
            notifyDataSetChanged()
        }

        override fun getItem(position: Int): Fragment = fragments[position]
        override fun getCount(): Int = fragments.size

        override fun getPageTitle(position: Int): CharSequence? {
            return when(position){
                0 -> "RED TEAM"
                1 -> "BLUE TEAM"
                2 -> "HISTORY"
                else -> ""
            }
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

    override fun onBackPressed() {
        super.onBackPressed()
        FragmentRedTeamHistoryOfPlayers.listOfRedTeamMatchScore.clear()
        FragmentMatchFlow.adapterHistory.listOfResults.clear()
        FragmentBlueTeamHistoryOfPlayer.blueTeamPlayers.clear()
        FragmentRedTeamHistoryOfPlayers.listOfPlayersRed.clear()
    }

    override fun onPause() {
        super.onPause()
        FragmentRedTeamHistoryOfPlayers.listOfRedTeamMatchScore.clear()
        FragmentMatchFlow.adapterHistory.listOfResults.clear()
        FragmentBlueTeamHistoryOfPlayer.blueTeamPlayers.clear()
        FragmentRedTeamHistoryOfPlayers.listOfPlayersRed.clear()
    }

    companion object{
        var matchId = 0
    }
}