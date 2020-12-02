package com.example.gaiety.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gaiety.R
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException

class MeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fetchJson()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_me, container, false)
    }

    private fun fetchJson() {
        val url = "https://api.timepad.ru/v1/organizations"
        val bodyItem =
            """
            {
                "name": "string",
                "subdomain": "string",
                "phone": "88005553535"
            }
            """.trimIndent()
        val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), bodyItem)
        val token = "993e92d9a94e12efb66ab5ee29b0fbdba217f725"

        val request = Request.Builder()
            .url(url)
            .header("Authorization", "Bearer " + token)
            .post(body)
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(
            object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val body = response?.body?.string()
                    println(body)
                }

                override fun onFailure(call: Call, e: IOException) {
                    println("Bad request")
                }
            }
        )
    }
}
