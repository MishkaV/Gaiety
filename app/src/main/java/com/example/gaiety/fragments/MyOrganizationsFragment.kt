package com.example.gaiety.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gaiety.NumAdapterMyOrganizations
import com.example.gaiety.R
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class MyOrganizationsFragment : Fragment() {
    lateinit var numList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_myorganizations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        numList = view.findViewById(R.id.recyclerViewMyOrganizations)
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            numList.layoutManager =
                GridLayoutManager(requireContext(), 1, RecyclerView.VERTICAL, false)
        else
            numList.layoutManager =
                GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)

        fetchJson()
    }

    private fun fetchJson() {
        var url = "https://api.timepad.ru/v1/events.json?limit=40&"
        url = url + "skip=0&cities=Москва,Санкт-Петербург&fields=location&sort=+starts_at"
        val token = "993e92d9a94e12efb66ab5ee29b0fbdba217f725"

        val request = Request.Builder()
            .url(url)
            .header("Authorization", "Bearer " + token)
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(
            object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val body = response?.body?.string()

                    val gson = GsonBuilder().create()

                    val homeFeed = gson.fromJson(body, HomeFeed::class.java)

                    val adapter = NumAdapterMyOrganizations(homeFeed)

                    activity?.runOnUiThread {
                        numList.adapter = adapter
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    println("Bad request")
                }
            }
        )
    }

    class HomeFeed(val total: Int, val values: List<Organization>)
    class Organization(val name: String)
}
