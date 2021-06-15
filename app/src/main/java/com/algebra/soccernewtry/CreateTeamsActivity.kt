package com.algebra.soccernewtry

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.algebra.soccernewtry.constants.Constants
import com.algebra.soccernewtry.databinding.ActivityCreateTeamsBinding
import com.algebra.soccernewtry.navdrawer.NavDrawerList
import com.algebra.soccernewtry.navdrawer.SetupToolbarDrawer
import com.algebra.soccernewtry.stateactivity.main.StateActivityViewModel
import com.algebra.soccernewtry.stateactivity.model.StateOfActivity
import com.algebra.soccernewtry.team.random.own.ChooseYourOwnActivity
import com.algebra.soccernewtry.team.random.randomChoosing.RandomChoosingTeamActivity
import dagger.hilt.android.AndroidEntryPoint
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig

@AndroidEntryPoint
class CreateTeamsActivity : AppCompatActivity() {

    lateinit var binding: ActivityCreateTeamsBinding
    private val navDrawerListExpandable = NavDrawerList(this)
    private val viewModel: StateActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCreateTeamsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbarAndNavigationDrawer()
        clickListeners()
        bind()
        showCaseView()
    }

    private fun bind(){
        viewModel.getStateOfActivity().observe(this, Observer {
            if(it.isNotEmpty() && it[0].isEndedGame == 1) viewModel.addStateActiity(StateOfActivity(it[0].id, 0))
            else if(it.isEmpty()) viewModel.addStateActiity(StateOfActivity(0, 0))
        })
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

    private fun showCaseView(){
        val config = ShowcaseConfig()

        val sequence = MaterialShowcaseSequence(this, Constants.SHOWCASE_ID_SETUP)
        sequence.setConfig(config)
        sequence.addSequenceItem(
            MaterialShowcaseView.Builder(this)
                .setSkipText("SKIP")
                .setTarget(binding.btnRandomPicker)
                .setDismissText("GOT IT")
                .setContentText("Button for creating random teams!")
                .setShapePadding(16)
                .withRectangleShape(false)
                .build()
        )

        sequence.addSequenceItem(
            MaterialShowcaseView.Builder(this)
                .setSkipText("SKIP")
                .setTarget(binding.btnYourPickTeam)
                .setShapePadding(16)
                .setDismissText("GOT IT")
                .setContentText("Button for creating your own teams!")
                .withRectangleShape()
                .setShapePadding(16)
                .build()
        )
        sequence.start()
    }
}