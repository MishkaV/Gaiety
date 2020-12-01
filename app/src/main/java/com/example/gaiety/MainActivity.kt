package com.example.gaiety

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import com.example.gaiety.fragments.*
import kotlinx.android.synthetic.main.fragment_home.*

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

    private fun makeCurrentFragment(fragment: Fragment, name : String) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.window_fragment, fragment)
            addToBackStack(name)
            commit()
        }
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
        when (view.id)
        {
            R.id.loginButton -> makeCurrentFragment(loginFrag, "loginFrag")
            R.id.registrationButton -> makeCurrentFragment(registerFrag, "registerFrag")
            R.id.loginButtonFrag -> {
                makeCurrentFragment(mainFrag, "mainFrag")
                makeCurrentFragmentMain(homeFrag, "homeFrag")
            }
            R.id.registrationButtonFrag-> {
                makeCurrentFragment(mainFrag, "mainFrag")
                makeCurrentFragmentMain(homeFrag, "homeFrag")
            }

            R.id.recyclerViewCard -> {
                itemFrag = ItemRecyclerMore()
                makeCurrentFragmentMain(itemFrag, "itemFrag")
            }
        }
    }
}
