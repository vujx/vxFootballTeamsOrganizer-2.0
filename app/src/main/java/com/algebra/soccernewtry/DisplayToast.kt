package com.algebra.soccernewtry

import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

fun displayMessage(activity: AppCompatActivity, message: String){
    val toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT)
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.show()
}