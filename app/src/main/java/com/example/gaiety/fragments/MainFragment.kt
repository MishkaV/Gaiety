package com.example.gaiety.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.gaiety.R
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {
    lateinit var homeFrag: homeFragment
    lateinit var meFrag: meFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeFrag = homeFragment()
        meFrag = meFragment()

        bottom_navigation?.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> makeCurrentFragmentMain(homeFrag)
                R.id.ic_me -> {makeCurrentFragmentMain(meFrag)
                    println("Hello world")}
            }
            true
        }
    }

    private fun makeCurrentFragmentMain(fragment: Fragment) =
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.main_fragment, fragment)
            commit()
        }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bottom_navigation?.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> makeCurrentFragmentMain(homeFrag)
                R.id.ic_me -> {makeCurrentFragmentMain(meFrag)
                    println("Hello world")}
            }
            true
        }
        return inflater.inflate(R.layout.fragment_main, container, false)
    }


}
