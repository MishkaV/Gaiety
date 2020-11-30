package com.example.gaiety

import android.app.ActivityOptions
import android.app.job.JobInfo
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.print.PrinterInfo
import android.transition.TransitionInflater
import android.view.MenuItem
import android.view.SurfaceControl
import android.view.View
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.gaiety.fragments.MainFragment
import com.example.gaiety.fragments.StartFragment
import com.example.gaiety.fragments.homeFragment
import com.example.gaiety.fragments.meFragment
import com.google.android.material.navigation.NavigationView
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_start.*
import okhttp3.*
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var homeFrag: homeFragment
    lateinit var meFrag: meFragment
    lateinit var startFrag: StartFragment
    lateinit var mainFrag: MainFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        homeFrag = homeFragment()
        meFrag = meFragment()
        startFrag = StartFragment()
        mainFrag = MainFragment()

        makeCurrentFragment(startFrag)

        user?.setOnClickListener {
            makeCurrentFragment(mainFrag)
            makeCurrentFragmentMain(homeFrag)
        }


    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.window_fragment, fragment)
            commit()
        }

    private fun makeCurrentFragmentMain(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_fragment, fragment)
            commit()
        }

    private fun changeFramentMain() =
        bottom_navigation?.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> makeCurrentFragmentMain(homeFrag)
                R.id.ic_me -> makeCurrentFragmentMain(meFrag)
            }
            true
        }

    fun onClick(view: View){
        when (view.id)
        {
            R.id.user -> { makeCurrentFragment(mainFrag)
                makeCurrentFragmentMain(homeFrag)
                }
        }
    }
}
