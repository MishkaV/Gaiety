package view.activities

import model.FirebaseRequests
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
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
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.mapbox.mapboxsdk.maps.MapFragment
import github.com.st235.lib_expandablebottombar.ExpandableBottomBar
import kotlinx.android.synthetic.main.change_about_me.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_reset_password.*
import kotlinx.android.synthetic.main.fragment_token.*
import presenter.meScreen.BottomSheetFragment
import view.fragments.meScreen.aboutMe.AboutMe
import view.fragments.meScreen.aboutMe.ChangeAboutMe
import view.fragments.meScreen.myFavoriteEventsScreen.MyFavoriteEvents
import view.fragments.pkScreen.PkFragment
import view.fragments.pkScreen.tokenScreen.TokenFragment

private const val TAG = "TAG"
val firebaseRequests = FirebaseRequests()
var currentFragMain: String? = null
var currentFrag: String? = null


class MainActivity : AppCompatActivity() {
    lateinit var homeFrag: HomeFragment
    lateinit var myTicketsEventsFrag: myTickets
    lateinit var myFavoriteEventsFrag: MyFavoriteEvents
    lateinit var myOrganizationsFrag: MyOrganizationsFragment
    lateinit var tokenFrag: TokenFragment
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
    lateinit var pkFrag: PkFragment
    lateinit var mapFragment: view.fragments.mapScreen.MapFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        homeFrag = HomeFragment()
        myTicketsEventsFrag = myTickets()
        myFavoriteEventsFrag = MyFavoriteEvents()
        myOrganizationsFrag = MyOrganizationsFragment()
        tokenFrag = TokenFragment()
        addOrganizationFrag = AddOrganizationFragment()
        meFrag = MeFragment()
        startFrag = StartFragment()
        mainFrag = MainFragment()
        loginFrag = LoginFragment()
        registerFrag = RegisterFragment()
        resetPasswordFragment = ResetPasswordFragment()
        aboutMe = AboutMe()
        changeAboutMe = ChangeAboutMe()
        pkFrag = PkFragment()
        mapFragment = view.fragments.mapScreen.MapFragment()

        if (savedInstanceState != null) {
            currentFrag = savedInstanceState.getString("currentFrag")
            currentFragMain = savedInstanceState.getString("currentFragMain")
            if (currentFrag != null)
                when (currentFrag) {
                    "loginFrag" -> makeCurrentFragment(loginFrag, "loginFrag")
                    "registerFrag" -> makeCurrentFragment(registerFrag, "registerFrag")
                    "startFrag" -> makeCurrentFragment(startFrag, "startFrag")
                    "resetPasswordFragment" -> makeCurrentFragment(
                        resetPasswordFragment,
                        "resetPasswordFragment"
                    )
                    "mainFrag" -> {
                        if (currentFragMain != null)
                            when (currentFragMain) {
                                "homeFrag" -> {
                                    makeCurrentFragment(mainFrag, "mainFrag")
                                    makeCurrentFragmentMain(homeFrag, "homeFrag")
                                }
                                "mapFrag" -> {
                                    makeCurrentFragment(mainFrag, "mainFrag")
                                    makeCurrentFragmentMain(mapFragment, "mapFrag")
                                }
                                "meFrag" -> {
                                    makeCurrentFragment(mainFrag, "mainFrag")
                                    makeCurrentFragmentMain(meFrag, "meFrag")
                                }
                                "myTicketsFrag" -> {
                                    makeCurrentFragment(mainFrag, "mainFrag")
                                    makeCurrentFragmentMain(myTicketsEventsFrag, "myTicketsFrag")
                                }
                                "myOrganizationFrag" -> {
                                    makeCurrentFragment(mainFrag, "mainFrag")
                                    makeCurrentFragmentMain(
                                        myOrganizationsFrag,
                                        "myOrganizationFrag"
                                    )
                                }
                                "itemFrag" -> {
                                    makeCurrentFragment(mainFrag, "mainFrag")
                                    makeCurrentFragmentMain(itemFrag, "itemFrag")
                                }
                            }
                    }
                }
        } else
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
                    currentFrag = "mainFrag"
                    currentFragMain = "homeFrag"
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
                    mailEditTextReg?.text.toString() == "" || passwordEditTextReg?.text.toString() == "")
        ) {
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
                    currentFrag = "mainFrag"
                    currentFragMain = "homeFrag"
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

    fun resetPassword() {
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
                    } else {
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
        bottomSheetFragment: BottomSheetFragment
    ) {
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
            currentFrag = "mainFrag"
            currentFragMain = "meFrag"
            bottomSheetFragment.show(supportFragmentManager, "BottomSheetDialog")
            Log.d("Change", "Successfull!")

        } else if (nameEditTextChange?.text.toString() == "") {
            nameEditLayoutChange.error = "Введите имя"
        } else surnameEditLayoutChange.error = "Введите фамилию"
    }

    fun putToken() {
        tokenEditLayoutLog.error = null
        if (tokenEditTextLog?.text.toString() != "") {
            val token = tokenEditTextLog?.text.toString()

            Log.d("MainActivity", token)

            firebaseRequests.changeToken(token)
            makeCurrentFragment(mainFrag, "mainFrag")
            makeCurrentFragmentMain(pkFrag, "pkFrag")
            Log.d("Change", "Successfull!")

        } else tokenEditLayoutLog.error = "Введите токен"
    }
    fun onClick(view: View) {
        val bottomSheetDialog = BottomSheetDialog(this)
        when (view.id) {
            R.id.loginButton -> {
                makeCurrentFragment(loginFrag, "loginFrag")
                currentFrag = "loginFrag"
            }
            R.id.registrationButton -> {
                makeCurrentFragment(registerFrag, "registerFrag")
                currentFrag = "registerFrag"
            }
            R.id.loginButtonFrag -> {
                authorization()
            }
            R.id.registrationButtonFrag -> {
                registration()
            }
            R.id.myTicketsMe -> {
                makeCurrentFragmentMain(myTicketsEventsFrag, "myTicketsFrag")
                currentFragMain = "myTicketsFrag"
            }
            R.id.myFavoriteEvent -> {
                makeCurrentFragmentMain(myFavoriteEventsFrag, "myTicketsFrag")
                currentFragMain = "myTicketsFrag"
            }
            R.id.myOrganizationsMe -> {
                makeCurrentFragmentMain(myOrganizationsFrag, "myOrganizationFrag")
                currentFragMain = "myOrganizationFrag"
            }
            R.id.exitMe -> {
                makeCurrentFragment(startFrag, "startFrag")
                currentFrag = "startFrag"
            }
            R.id.recyclerViewCard -> {
                itemFrag =
                    ItemRecyclerMore()
                makeCurrentFragmentMain(itemFrag, "itemFrag")
                currentFragMain = "itemFrag"
            }
            R.id.floating_action_button -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://afisha.timepad.ru/"))
                startActivity(intent)
            }

            R.id.resetButton -> {
                makeCurrentFragment(resetPasswordFragment, "resetPasswordFragment")
                currentFrag = "resetPasswordFragment"
            }
            R.id.resetButtonFrag -> resetPassword()
            R.id.aboutMe -> { //makeCurrentFragmentMain(aboutMe, "aboutMe")
                val bottomSheetView = LayoutInflater.from(this)
                    .inflate(
                        R.layout.fragment_about_me,
                        findViewById(R.id.aboutMeFrag)
                    )
                val buttonChange = bottomSheetView.findViewById<Button>(R.id.changeAboutMe)
                buttonChange.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(p0: View?) {
                        bottomSheetDialog.dismiss()
                        makeCurrentFragmentMain(changeAboutMe, "changeAboutMe")
                    }
                })
                firebaseRequests.getAboutMe(bottomSheetView)
                bottomSheetDialog?.setContentView(bottomSheetView)
                bottomSheetDialog?.show()


            }

            R.id.changeButtonFrag -> {
                change(bottomSheetDialog)
            }

            R.id.myTickets -> {
                makeCurrentFragmentMain(myTicketsEventsFrag, "myTicketsFrag")
            }

            R.id.myOrganizations -> makeCurrentFragmentMain(myOrganizationsFrag, "myOrganizationFrag")

            R.id.token -> makeCurrentFragment(tokenFrag, "tokenFrag")
            R.id.getToken -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://dev.timepad.ru/api/oauth/"))
                startActivity(intent)
            }
            R.id.tokenButtonFrag -> putToken()
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState?.run {
            putString("currentFrag", currentFragMain)
            putString("currentFragMain", currentFrag)
        }
        super.onSaveInstanceState(outState, outPersistentState)
    }

}
