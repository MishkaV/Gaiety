package com.example.gaiety

import android.app.job.JobInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.print.PrinterInfo
import android.view.MenuItem
import android.view.View
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
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

class MainActivity : AppCompatActivity() {

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
        fetchJson();
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }


    private fun fetchJson() {
        val url = "https://api.timepad.ru/v1/events.json?limit=20&skip=0&cities=Москва,Санкт-Петербург&fields=location&sort=+starts_at"
        val token = "4df6676a1f216b99b184c75fe65bb971b140473c"

        val request = Request.Builder()
            .url(url)
            .header("Authorization", "Bearer " + token)
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback{
            override fun onResponse(call: Call, response: Response) {
                val body = response?.body?.string()
                println(body)

                val gson = GsonBuilder().create()

                val homeFeed = gson.fromJson(body, HomeFeed::class.java)
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Bad request")
            }
        })

    }

    class HomeFeed(val total:Int, val values: List<Event>)
    class Event(val id: Int, val starts_at: String, val name: String, val url: String,
                val poster_image: PosterImagemage, val location: Location,
                val categories: List<Сategories>, val moderation_status: String)
    class PosterImagemage(val default_url: String, val uploadcare_url: String)
    class Location(val country: String, val city: String, val address: String)
    class Сategories(val id : Int, val name: String)

}
