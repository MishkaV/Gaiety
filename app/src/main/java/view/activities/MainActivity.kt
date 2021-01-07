package view.activities

import model.FirebaseRequests
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import view.fragments.*
import view.fragments.homeScreen.detailsScreen.ItemRecyclerMore
import view.fragments.homeScreen.HomeFragment
import view.fragments.loginScreen.LoginFragment
import view.fragments.loginScreen.resetPassword.ResetPasswordFragment
import view.fragments.meScreen.myOrganizationsScreen.AddOrganizationScreen.AddOrganizationFragment
import view.fragments.meScreen.MeFragment
import view.fragments.meScreen.myOrganizationsScreen.MyOrganizationsFragment
import view.fragments.meScreen.myTicketsScreen.myTickets
import view.fragments.registerScreen.RegisterFragment
import view.fragments.startScreen.StartFragment
import com.example.gaiety.R
import com.google.firebase.auth.FirebaseAuth
import github.com.st235.lib_expandablebottombar.ExpandableBottomBar
import kotlinx.android.synthetic.main.change_about_me.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_reset_password.*
import presenter.meScreen.BottomSheetFragment
import view.fragments.meScreen.aboutMe.AboutMe
import view.fragments.meScreen.aboutMe.ChangeAboutMe
import view.fragments.meScreen.myFavoriteEventsScreen.MyFavoriteEvents

private const val TAG = "TAG"
val firebaseRequests = FirebaseRequests()

class MainActivity : AppCompatActivity() {
    lateinit var homeFrag: HomeFragment
    lateinit var myTicketsEventsFrag: myTickets
    lateinit var myFavoriteEventsFrag: MyFavoriteEvents
    lateinit var myOrganizationsFrag: MyOrganizationsFragment
    lateinit var addOrganizationFrag: AddOrganizationFragment
    lateinit var meFrag: MeFragment
    lateinit var startFrag: StartFragment
    lateinit var mainFrag: MainFragment
    lateinit var itemFrag: ItemRecyclerMore
    private lateinit var loginFrag: LoginFragment
    lateinit var registerFrag: RegisterFragment
    lateinit var resetPasswordFragment: ResetPasswordFragment
    lateinit var aboutMe: AboutMe
    lateinit var changeAboutMe: ChangeAboutMe


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        homeFrag = HomeFragment()
        myTicketsEventsFrag = myTickets()
        myFavoriteEventsFrag = MyFavoriteEvents()
        myOrganizationsFrag = MyOrganizationsFragment()
        addOrganizationFrag = AddOrganizationFragment()
        meFrag = MeFragment()
        startFrag = StartFragment()
        mainFrag = MainFragment()
        loginFrag = LoginFragment()
        registerFrag = RegisterFragment()
        resetPasswordFragment = ResetPasswordFragment()
        aboutMe = AboutMe()
        changeAboutMe = ChangeAboutMe()

        makeCurrentFragment(startFrag, "startFrag")
    }

    private fun makeCurrentFragment(fragment: Fragment, name: String) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.window_fragment, fragment)
            addToBackStack(name.toString())
            commit()
        }
    }

    private fun makeCurrentFragmentMain(fragment: Fragment, name: String) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_fragment, fragment)
            addToBackStack(name.toString())
            commit()
        }
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
                    firebaseRequests.setCurrentUser(email)
                    Log.d("MainActivity", "Successfull!")
                }
        } else if (mailEditTextLog?.text.toString() == "") {
            mailEditLayoutLog.error = "Введите почту"
        } else passwordEditLayoutLog.error = "Введите пароль"
    }

    fun registration() {
        nameEditLayoutReg.error = null
        surnameEditLayoutReg.error = null
        mailEditLayoutReg.error = null
        passwordEditLayoutReg.error = null
        if (!(nameEditTextReg?.text.toString() == "" || surnameEditTextReg?.text.toString() == "" ||
                    mailEditTextReg?.text.toString() == "" || passwordEditTextReg?.text.toString() == "")) {
            val name = nameEditTextReg?.text.toString()
            val surname = surnameEditTextReg?.text.toString()
            val email = mailEditTextReg?.text.toString()
            val password = passwordEditTextReg?.text.toString()

            Log.d("MainActivity", name)
            Log.d("MainActivity", surname)
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
                    firebaseRequests.createNewUser(email, name, surname)
                    Log.d("MainActivity", "Successfull!")
                }
        } else if (nameEditTextReg?.text.toString() == "") {
            nameEditLayoutReg.error = "Введите имя"
        } else if (surnameEditTextReg?.text.toString() == "") {
            surnameEditLayoutReg.error = "Введите фамилию"
        } else if (mailEditTextReg?.text.toString() == "") {
            mailEditLayoutReg.error = "Введите почту"
        } else passwordEditLayoutReg.error = "Введите пароль"
    }

    fun resetPassword(){
        mailResetLayout?.error = null
        if (mailResetText?.text.toString() != "") {
            val email = mailResetText?.text.toString()

            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Email sent.")
                        Toast.makeText(
                            this,
                            "Email sent.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                     else {
                         Toast.makeText(
                             this,
                             "Sorry, but there is no such email",
                             Toast.LENGTH_SHORT
                         ).show()
                         return@addOnCompleteListener
                     }
                }
        } else
            mailResetLayout?.error = "Введите почту"
    }

    fun change(
        bottomSheetFragment: BottomSheetFragment) {
        nameEditLayoutChange.error = null
        surnameEditLayoutChange.error = null
        if (!(nameEditTextChange?.text.toString() == "" || surnameEditTextChange?.text.toString() == "")) {
            val name = nameEditTextChange?.text.toString()
            val surname = surnameEditTextChange?.text.toString()

            Log.d("MainActivity", name)
            Log.d("MainActivity", surname)

            firebaseRequests.changeUser(name, surname)
            makeCurrentFragment(mainFrag, "mainFrag")
            makeCurrentFragmentMain(meFrag, "meFrag")
            bottomSheetFragment.show(supportFragmentManager, "BottomSheetDialog")
            Log.d("Change", "Successfull!")

        } else if (nameEditTextChange?.text.toString() == "") {
            nameEditLayoutChange.error = "Введите имя"
        } else surnameEditLayoutChange.error = "Введите фамилию"
    }

    fun onClick(view: View) {
        val bottomSheetFragment = BottomSheetFragment()
        when (view.id) {
            R.id.loginButton -> makeCurrentFragment(loginFrag, "loginFrag")
            R.id.registrationButton -> makeCurrentFragment(registerFrag, "registerFrag")
            R.id.loginButtonFrag -> {
                authorization()
            }
            R.id.registrationButtonFrag -> {
                registration()
            }
            R.id.myTicketsMe -> makeCurrentFragmentMain(myTicketsEventsFrag, "myTicketsFrag")
            R.id.myFavoriteEvent -> makeCurrentFragmentMain(myFavoriteEventsFrag, "myTicketsFrag")
            R.id.myOrganizationsMe -> makeCurrentFragmentMain(myOrganizationsFrag, "myOrganizationFrag")
            R.id.exitMe -> makeCurrentFragment(startFrag, "startFrag")
            R.id.recyclerViewCard -> {
                itemFrag =
                    ItemRecyclerMore()
                makeCurrentFragmentMain(itemFrag, "itemFrag")
            }
            R.id.floating_action_button -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://afisha.timepad.ru/"))
                startActivity(intent)
            }
            R.id.resetButton -> {
                makeCurrentFragment(resetPasswordFragment, "resetPasswordFragment")
            }
            R.id.resetButtonFrag -> resetPassword()
            R.id.aboutMe -> { //makeCurrentFragmentMain(aboutMe, "aboutMe")
                bottomSheetFragment.show(supportFragmentManager, "BottomSheetDialog")
            }
            R.id.changeAboutMe -> {
                makeCurrentFragment(changeAboutMe, "changeAboutMe")

            }

            R.id.changeButtonFrag -> {
                change(bottomSheetFragment)
            }
        }
    }
}
