package com.algebra.soccernewtry

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.finishAffinity
import com.algebra.soccernewtry.dialog.DialogCheck

fun exitApp(activity: AppCompatActivity){
    val dialog = DialogCheck("Are you sure you want to exit?")
    dialog.show(activity.supportFragmentManager, "Nesto")
    dialog.listener = object: DialogCheck.Listener{
        override fun getPress(isPress: Boolean) {
            finishAffinity(activity)
        }
    }
}