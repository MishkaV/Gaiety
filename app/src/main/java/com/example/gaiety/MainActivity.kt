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
import com.example.gaiety.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_start.*
import kotlinx.android.synthetic.main.recyclerview_item.view.*
import okhttp3.*
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var homeFrag: homeFragment
    lateinit var meFrag: meFragment
    lateinit var startFrag: StartFragment
    lateinit var mainFrag: MainFragment
    lateinit var itemFrag: ItemRecyclerMore
    lateinit var loginFrag : LoginFragment
    lateinit var registerFrag : RegisterFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        homeFrag = homeFragment()
        meFrag = meFragment()
        startFrag = StartFragment()
        mainFrag = MainFragment()
        loginFrag = LoginFragment()
        registerFrag = RegisterFragment()

        makeCurrentFragment(startFrag, "startFrag")

    }

    private fun makeCurrentFragment(fragment: Fragment, name : String) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.window_fragment, fragment)
            addToBackStack(name)
            commit()
        }

    private fun makeCurrentFragmentMain(fragment: Fragment, name : String) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_fragment, fragment)
            addToBackStack(name)
            commit()
        }
    }


    fun changeFragmentsInMain(item : MenuItem){
            when (item.itemId) {
                R.id.ic_home -> makeCurrentFragmentMain(homeFrag, "homeFrag")
                R.id.ic_me -> makeCurrentFragmentMain(meFrag, "meFrag")
            }
            true
    }

    fun onClick(view: View){
        println(view.id)
        when (view.id)
        {
            R.id.loginButton -> makeCurrentFragment(loginFrag, "loginFrag")
            R.id.registrationButton -> makeCurrentFragment(registerFrag, "registerFrag")
            R.id.loginButtonFrag -> { makeCurrentFragment(mainFrag, "mainFrag")
                makeCurrentFragmentMain(homeFrag, "homeFrag")
            }
            R.id.registrationButtonFrag-> { makeCurrentFragment(mainFrag, "mainFrag")
                makeCurrentFragmentMain(homeFrag, "homeFrag")
            }
        }
    }

    fun onClickItemRecycler(view: View) {
            itemFrag = ItemRecyclerMore()
            makeCurrentFragmentMain(itemFrag, "itemFrag")
    }
}
