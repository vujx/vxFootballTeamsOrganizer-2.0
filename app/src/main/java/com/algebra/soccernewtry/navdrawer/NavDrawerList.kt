package com.algebra.soccernewtry.navdrawer

import android.content.Intent
import android.view.View
import android.widget.ExpandableListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.algebra.soccernewtry.CreateTeamsActivity
import com.algebra.soccernewtry.R
import com.algebra.soccernewtry.additional.actions.MoreActionsActivity
import com.algebra.soccernewtry.bonus.BonusPointsActivity
import com.algebra.soccernewtry.constants.Constants
import com.algebra.soccernewtry.dialog.DialogCheck
import com.algebra.soccernewtry.display.achievement.ResultActivity
import com.algebra.soccernewtry.display.efficiency.EfficiencyActivity
import com.algebra.soccernewtry.display.historyOfMatch.HistoryMatchActivity
import com.algebra.soccernewtry.display.realisation.RealisationActivity
import com.algebra.soccernewtry.player.main.PlayerActivity

class NavDrawerList(private val activity: AppCompatActivity) {

    var listAdapter: ExpandableListAdapter? = null
    var expListView: ExpandableListView? = null
    var listDataHeader: MutableList<String>? = null
    var listDataChild: HashMap<String, List<String>>? = null

    fun setUpValues(){
        // get the listview
        expListView = activity.findViewById<View>(R.id.lvExp) as ExpandableListView
        // preparing list data
        prepareListData()
        listAdapter = listDataHeader?.let { listDataChild?.let { it1 ->
            ExpandableListAdapter(activity, it,
                it1
            )
        } }
        // setting list adapter
        expListView!!.setAdapter(listAdapter)
    }

    fun prepareListData() {
        listDataHeader = ArrayList()
        listDataChild = HashMap()


        // Adding child data
        (listDataHeader as ArrayList<String>).add("Create teams and players")
        (listDataHeader as ArrayList<String>).add("Statistics")
        (listDataHeader as ArrayList<String>).add("Additions")

        // Adding child data
        val top250: MutableList<String> = ArrayList()
        top250.add("Create teams")
        top250.add("Players")

        val nowShowing: MutableList<String> = ArrayList()
        nowShowing.add("Achievement")
        nowShowing.add("Efficiency")
        nowShowing.add("Realisation")
        nowShowing.add("History")

        val comingSoon: MutableList<String> = ArrayList()
        comingSoon.add("Bonus Points")
        comingSoon.add("More Actions")
        comingSoon.add("Restart Showcase")
        comingSoon.add("Exit")

        listDataChild!![(listDataHeader as ArrayList<String>).get(0)] = top250 // Header, Child data
        listDataChild!![(listDataHeader as ArrayList<String>).get(1)] = nowShowing
        listDataChild!![(listDataHeader as ArrayList<String>).get(2)] = comingSoon

        expListView?.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            when (listDataChild!![listDataHeader!![groupPosition]]!![childPosition]) {
                "Create teams" -> {
                    val intent = Intent(activity, CreateTeamsActivity::class.java)
                    activity.startActivity(intent)
                }
                "Players" -> {
                    val intent = Intent(activity, PlayerActivity::class.java)
                    activity.startActivity(intent)
                }
                "Efficiency" -> {
                    val intent = Intent(activity, EfficiencyActivity::class.java)
                    activity.startActivity(intent)
                }
                "Realisation" -> {
                    val intent = Intent(activity, RealisationActivity::class.java)
                    activity.startActivity(intent)
                }
                "Achievement" -> {
                    val intent = Intent(activity, ResultActivity::class.java)
                    activity.startActivity(intent)
                }
                "History" -> {
                    val intent = Intent(activity, HistoryMatchActivity::class.java)
                    activity.startActivity(intent)
                }
                "Bonus Points" -> {
                    val intent = Intent(activity, BonusPointsActivity::class.java)
                    activity.startActivity(intent)
                }
                "More Actions" -> {
                    val intent = Intent(activity, MoreActionsActivity::class.java)
                    activity.startActivity(intent)
                }
                "Exit" -> {
                    val dialog = createDialog("Are you sure you want to exit?", "Logout")
                    dialog.listener = object: DialogCheck.Listener{
                        override fun getPress(isPress: Boolean) {
                            ActivityCompat.finishAffinity(activity)
                        }
                    }
                }
                "Restart Showcase" -> {
                    Constants.SHOWCASE_ID_MOREACTIONS += "J"
                    Constants.SHOWCASE_ID_CODE += "J"
                    Constants.SHOWCASE_ID_SETUP += "J"
                    Constants.SHOWCASE_ID_PLAYER += "J"
                    Constants.SHOWCASE_ID_HISTORY += "J"
                    Constants.SHOWCASE_ID_RANDOM += "J"
                    Constants.SHOWCASE_ID_OWN += "J"
                    Constants.SHOWCASE_ID_PLAYERMOREOPTIONS += "J"
                    Constants.SHOWCASE_ID_SCORE += "J"
                    val intent = Intent(activity, activity.javaClass)
                    activity.startActivity(intent)
                }
            }
            false
        }
    }

    private fun createDialog(title: String, tag: String): DialogCheck {
        val dialog = DialogCheck(title)
        dialog.show(activity.supportFragmentManager, tag)
        return dialog
    }
}
