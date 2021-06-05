package com.algebra.soccernewtry.additional.actions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.algebra.soccernewtry.additional.actions.codeDisplay.CodeFragment
import com.algebra.soccernewtry.additional.actions.moreActions.MoreActionsFragment
import com.algebra.soccernewtry.databinding.ActivityMoreActionsBinding
import com.algebra.soccernewtry.exitApp
import com.algebra.soccernewtry.navdrawer.NavDrawerList
import com.algebra.soccernewtry.navdrawer.SetupToolbarDrawer

class MoreActionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoreActionsBinding
    private val navDrawerListExpandable = NavDrawerList(this)
    private lateinit var pagerAdapter: SlidePagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMoreActionsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbarAndNavigationDrawer()
        setUpViewPager()
    }

    private fun setUpViewPager(){
        pagerAdapter = SlidePagerAdapter(supportFragmentManager)
        codeFragment = CodeFragment.newInstance()
        val fragments = listOf(
            MoreActionsFragment.newInstance(),
            codeFragment
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

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }
        override fun getCount(): Int = fragments.size

        override fun getPageTitle(position: Int): CharSequence? {
            return when(position){
                0 -> "MORE ACTIONS"
                1 -> "MY SHARES"
                else -> ""
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return SetupToolbarDrawer(this, binding.root).onOptionsItemSelected(item)
    }

    private fun setupToolbarAndNavigationDrawer(){
        binding.toolbar.title = "More Actions"
        SetupToolbarDrawer(this, binding.root).setUpToolbar()
        navDrawerListExpandable.setUpValues()
        navDrawerListExpandable.prepareListData()
    }

    override fun onBackPressed() {
        exitApp(this)
    }

    companion object{
        var codeFragment = CodeFragment.newInstance()
    }
}