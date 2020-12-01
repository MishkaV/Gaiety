package com.example.gaiety.fragments

import android.content.res.Configuration
import android.icu.util.LocaleData
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gaiety.NumAdapter

import com.example.gaiety.R
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_main.*
import okhttp3.*
import java.io.IOException
import java.time.format.DateTimeFormatter
import kotlin.concurrent.thread

class homeFragment : Fragment() {
    lateinit var numList: RecyclerView
    lateinit var adapter: NumAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        numList = view.findViewById(R.id.recyclerView)
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            numList.layoutManager =
                GridLayoutManager(requireContext(), 1, RecyclerView.VERTICAL, false)
        else
            numList.layoutManager =
                GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)

        fetchJson()
    }

    private fun fetchJson() {
        val url = "https://api.timepad.ru/v1/events.json?limit=40&skip=0&cities=Москва,Санкт-Петербург&fields=location&sort=+starts_at"
        val token = "993e92d9a94e12efb66ab5ee29b0fbdba217f725"

        val request = Request.Builder()
            .url(url)
            .header("Authorization", "Bearer " + token)
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response?.body?.string()
                //println(body)

                val gson = GsonBuilder().create()

                val homeFeed = gson.fromJson(body, HomeFeed::class.java)

                val adapter = NumAdapter(homeFeed)

                activity?.runOnUiThread {
                    numList.adapter = adapter
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Bad request")
            }
        })

    }

    class HomeFeed (val total:Int, val values: List<Event>)
    class Event(val id: Int, val starts_at: String, val name: String, val url: String,
                val poster_image: PosterImagemage, val location: Location,
                val categories: List<Сategories>, val moderation_status: String)
    class PosterImagemage(val default_url: String, val uploadcare_url: String)
    class Location(val country: String, val city: String, val address: String)
    class Сategories(val id : Int, val name: String)

}
