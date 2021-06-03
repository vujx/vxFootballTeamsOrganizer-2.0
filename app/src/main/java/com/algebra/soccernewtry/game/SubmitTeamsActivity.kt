package com.algebra.soccernewtry.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import com.algebra.soccernewtry.R
import com.algebra.soccernewtry.constants.Constants
import com.algebra.soccernewtry.databinding.ActivitySubmitTeamsBinding
import com.algebra.soccernewtry.displayMessage
import com.algebra.soccernewtry.game.history.HistoryAdapter
import com.algebra.soccernewtry.game.history.HistoryFragment
import com.algebra.soccernewtry.game.teams.red.blue.BlueTeamFragment
import com.algebra.soccernewtry.game.teams.red.red.RedTeamFragment
import com.algebra.soccernewtry.player.model.Player
import com.algebra.soccernewtry.stateactivity.main.StateActivityViewModel
import com.algebra.soccernewtry.stateactivity.model.StateOfActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubmitTeamsActivity : AppCompatActivity() {

    lateinit var binding: ActivitySubmitTeamsBinding
    private lateinit var pagerAdapter: SlidePagerAdapter
    private lateinit var frgBlut: Fragment
    private lateinit var frgRed: Fragment
    private val viewModel: StateActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySubmitTeamsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpToolbar()
        setUpViewPager()
        bind()
    }

    private fun bind(){
        viewModel.getStateOfActivity().observe(this, Observer {
            if(it.isNotEmpty() && it[0].isEndedGame == 0) viewModel.addStateActiity(StateOfActivity(it[0].id, 1))
        })
    }

    private fun setUpViewPager(){
        pagerAdapter = SlidePagerAdapter(supportFragmentManager)
        frgBlut = BlueTeamFragment.newInstance(getBlueTeam())
        frgRed = RedTeamFragment.newInstance(getRedTeam())
        val fragments = listOf<Fragment>(frgRed, frgBlut, HistoryFragment.newInstance())
        pagerAdapter.setPages(fragments)
        binding.viewPager.adapter = pagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    private fun getRedTeam(): List<Player>{
        return intent.getSerializableExtra(Constants.RED_TEAM) as List<Player>
    }

    private fun getBlueTeam(): List<Player>{
        return intent.getSerializableExtra(Constants.BLUE_TEAM) as List<Player>
    }

    fun setScore(){
        if(frgBlut is BlueTeamFragment){
            (frgBlut as BlueTeamFragment).setScore()
        }
    }

    fun setScoreRed(){
        if(frgRed is RedTeamFragment){
            (frgRed as RedTeamFragment).setScore()
        }
    }

    private fun setUpToolbar(){
        window.statusBarColor = ContextCompat.getColor(this, R.color.greenToolbar)
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

    override fun onBackPressed() {
       displayMessage(this, "If you want to end game press END MATCH!")
    }

    companion object{
        val adapterHistory = HistoryAdapter()
    }
}