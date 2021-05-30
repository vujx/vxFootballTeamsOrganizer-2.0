package com.algebra.soccernewtry.navdrawer

import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.algebra.soccernewtry.R

class SetupToolbarDrawer(private val activity: AppCompatActivity, private val view: View) {

    private lateinit var drawerToggle: ActionBarDrawerToggle
    private val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
    private val drawerLayout = view.findViewById<DrawerLayout>(R.id.drawerLayout)

    fun setUpToolbar(){
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
        activity.window.statusBarColor = ContextCompat.getColor(activity,
            R.color.greenToolbar
        )
        drawerToggle = ActionBarDrawerToggle(activity, drawerLayout, toolbar,
            R.string.open,
            R.string.close
        )
        drawerToggle.syncState()
        drawerLayout.addDrawerListener(drawerToggle)
    }

    fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.home -> {
                val isOpen = drawerLayout.isDrawerOpen(GravityCompat.START)
                if(!isOpen) drawerLayout.openDrawer(GravityCompat.START)
                else drawerLayout.closeDrawers()
                true
            }
            else -> false
        }
    }
}