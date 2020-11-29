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
import com.example.gaiety.fragments.homeFragment
import com.example.gaiety.fragments.meFragment
import com.example.gaiety.fragments.settingsFragment
import com.google.android.material.navigation.NavigationView
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    var nameList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val homeFrag = homeFragment()
        val meFrag = meFragment()
        val settingsFrag = settingsFragment()

        makeCurrentFragment(homeFrag)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> makeCurrentFragment(homeFrag)
                R.id.ic_me -> makeCurrentFragment(meFrag)
                R.id.ic_settings -> makeCurrentFragment(settingsFrag)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
    
}
