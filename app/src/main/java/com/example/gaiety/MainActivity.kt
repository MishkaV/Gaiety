package com.example.gaiety

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.gaiety.fragments.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*


private var mAuth: FirebaseAuth? = null

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

    fun authorization (){
        val email = mailEditTextLog?.text.toString()
        val password = passwordEditTextLog?.text.toString()

        Log.d("MainActivity", email)
        Log.d("MainActivity", password)


        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful)
                    return@addOnCompleteListener
                Log.d("MainActivity", "Successfull!")
            }
    }

    fun registration (){
        val email = mailEditTextReg?.text.toString()
        val password = passwordEditTextReg?.text.toString()

        Log.d("MainActivity", email)
        Log.d("MainActivity", password)


        FirebaseAuth
            .getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful)
                    return@addOnCompleteListener
                Log.d("MainActivity", "Successfull!")
            }
    }

    fun onClick(view: View){
        when (view.id)
        {
            R.id.loginButton -> makeCurrentFragment(loginFrag, "loginFrag")
            R.id.registrationButton -> makeCurrentFragment(registerFrag, "registerFrag")
            R.id.loginButtonFrag -> {
                authorization()
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


