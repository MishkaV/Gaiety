package com.example.gaiety

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.gaiety.fragments.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*

class MainActivity : AppCompatActivity() {
    lateinit var homeFrag: homeFragment
    lateinit var myTicketsFrag: myTicketsFragment
    lateinit var myOrganizationsFrag: myOrganizationsFragment
    lateinit var addOrganizationFrag: addOrganizationFragment
    lateinit var favoriteFrag: favoriteFragment
    lateinit var meFrag: meFragment
    lateinit var startFrag: StartFragment
    lateinit var mainFrag: MainFragment
    lateinit var itemFrag: ItemRecyclerMore
    lateinit var loginFrag: LoginFragment
    lateinit var registerFrag: RegisterFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        homeFrag = homeFragment()
        myTicketsFrag = myTicketsFragment()
        myOrganizationsFrag = myOrganizationsFragment()
        addOrganizationFrag = addOrganizationFragment()
        favoriteFrag = favoriteFragment()
        meFrag = meFragment()
        startFrag = StartFragment()
        mainFrag = MainFragment()
        loginFrag = LoginFragment()
        registerFrag = RegisterFragment()

        makeCurrentFragment(startFrag, "startFrag")
    }

    private fun makeCurrentFragment(fragment: Fragment, name: String) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.window_fragment, fragment)
            addToBackStack(name)
            commit()
        }
    }

    private fun makeCurrentFragmentMain(fragment: Fragment, name: String) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_fragment, fragment)
            addToBackStack(name)
            commit()
        }
    }

    fun changeFragmentsInMain(item: MenuItem) {
        when (item.itemId) {
            R.id.ic_home -> makeCurrentFragmentMain(homeFrag, "homeFrag")
            R.id.ic_me -> makeCurrentFragmentMain(meFrag, "meFrag")
        }
        true
    }

    fun authorization() {
        mailEditLayoutLog.error = null
        passwordEditLayoutLog.error = null
        if (!(mailEditTextLog?.text.toString() == "" || passwordEditTextLog?.text.toString() == "")) {
            val email = mailEditTextLog?.text.toString()
            val password = passwordEditTextLog?.text.toString()

            Log.d("MainActivity", email)
            Log.d("MainActivity", password)

            FirebaseAuth
                .getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (!it.isSuccessful) {
                        Toast.makeText(
                            this,
                            "Sorry, but it's wrong email/password",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@addOnCompleteListener
                    }
                    makeCurrentFragment(mainFrag, "mainFrag")
                    makeCurrentFragmentMain(homeFrag, "homeFrag")
                    Log.d("MainActivity", "Successfull!")
                }
        } else if (mailEditTextLog?.text.toString() == "") {
            mailEditLayoutLog.error = "Введите почту"
        } else passwordEditLayoutLog.error = "Введите пароль"
    }

    fun registration() {
        mailEditLayoutReg.error = null
        passwordEditLayoutReg.error = null
        if (!(mailEditTextReg?.text.toString() == "" || passwordEditTextReg?.text.toString() == "")) {
            val email = mailEditTextReg?.text.toString()
            val password = passwordEditTextReg?.text.toString()

            Log.d("MainActivity", email)
            Log.d("MainActivity", password)

            FirebaseAuth
                .getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (!it.isSuccessful) {
                        Toast.makeText(
                            this,
                            "Sorry, but this user is already exist",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@addOnCompleteListener
                    }
                    makeCurrentFragment(mainFrag, "mainFrag")
                    makeCurrentFragmentMain(homeFrag, "homeFrag")
                    Log.d("MainActivity", "Successfull!")
                }
        } else if (mailEditTextReg?.text.toString() == "") {
            mailEditLayoutReg.error = "Введите почту"
        } else passwordEditLayoutReg.error = "Введите пароль"
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.loginButton -> makeCurrentFragment(loginFrag, "loginFrag")
            R.id.registrationButton -> makeCurrentFragment(registerFrag, "registerFrag")
            R.id.loginButtonFrag -> {
                authorization()
            }
            R.id.registrationButtonFrag -> {
                registration()
            }
            R.id.myTicketsMe -> makeCurrentFragmentMain(myTicketsFrag, "myTicketsFrag")
            R.id.favoriteEventsMe -> makeCurrentFragmentMain(favoriteFrag, "homeFrag")
            R.id.myOrganizationsMe -> makeCurrentFragmentMain(myOrganizationsFrag, "myOrganizationFrag")
            R.id.floating_action_button_myorganizations -> makeCurrentFragmentMain(addOrganizationFrag, "addOrganizationFrag")
            R.id.exitMe -> makeCurrentFragment(startFrag, "startFrag")
            R.id.recyclerViewCard -> {
                itemFrag = ItemRecyclerMore()
                makeCurrentFragmentMain(itemFrag, "itemFrag")
            }
        }
    }
}
