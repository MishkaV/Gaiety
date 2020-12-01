package com.example.gaiety

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Html
import android.text.method.ScrollingMovementMethod
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gaiety.fragments.homeFragment
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyclerview_item.view.*
import okhttp3.*
import java.io.IOException

class ItemMore : AppCompatActivity() {
    lateinit var itemRecycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_more)

        val eventId = intent.getIntExtra("eventId", 0)

        if (eventId != 0) {
            fetchJson(eventId)
        }
    }

    private fun fetchJson(id: Int) {
        val url = "https://api.timepad.ru/v1/events/" + id.toString()
        val token = "993e92d9a94e12efb66ab5ee29b0fbdba217f725"

        val request = Request.Builder()
            .url(url)
            .header("Authorization", "Bearer " + token)
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response?.body?.string()
                println(body)
                val gson = GsonBuilder().create()

                val event = gson.fromJson(body, Event::class.java)


                runOnUiThread {
                    setData(event)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Bad request")
            }
        })

    }

    private fun setData(event: Event) {
        val image = findViewById<ImageView>(R.id.itemImageUrl)
        if (event.poster_image == null) {
            image.setImageResource(R.drawable.logo)
        } else {
            Picasso.get()
                .load(event.poster_image.default_url)
                .into(image)
        }

        itemRecycler = findViewById(R.id.recyclerViewItemMore)
        itemRecycler.layoutManager = LinearLayoutManager(this,  RecyclerView.VERTICAL, false)
        itemRecycler.adapter = ItemMoreAdapter(event)
    }



    class Event (val id: Int , val created_at: String, val starts_at: String,
    val ends_at: String, val name: String, val status : String,
    val description_short: String, val description_html: String,
    val url: String, val poster_image: PosterImagemage, val ad_partner_percent: Int,
    val locale: String, val location: Location, val organization: Organization,
    val categories : List<Сategories>)

    class PosterImagemage(val default_url: String, val uploadcare_url: String)

    class Location(val country: String, val city: String, val address: String)

    class Organization(val id: Int, val name: String, val description_html: String,
    val url: String, val logo_image : PosterImagemage, val subdomain: String)

    class Сategories(val id : Int, val name: String)


}
