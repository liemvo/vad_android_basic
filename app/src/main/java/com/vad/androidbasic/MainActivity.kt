package com.vad.androidbasic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlin.time.ExperimentalTime

@ExperimentalTime
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.containerSimple, ListCounterFragment.newInstance())
//                .commit()
//        }
    }

//
//    override fun onBackPressed() {
//        if (supportFragmentManager.fragments.size > 1) {
//            supportFragmentManager.beginTransaction()
//                .remove(supportFragmentManager.fragments.last()).commitNow()
//        } else {
//            super.onBackPressed()
//        }
//    }
}