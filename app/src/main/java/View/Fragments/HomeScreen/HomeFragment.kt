package View.Fragments.HomeScreen

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import Presenter.HomeScreen.NumAdapter
import com.example.gaiety.R
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class HomeFragment : Fragment() {
    lateinit var numList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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
        val url = "https://api.timepad.ru/v1/events.json?limit=40&skip=0&fields=location&sort=+starts_at"
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

                    val adapter = NumAdapter(homeFeed)

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

    class HomeFeed(val total: Int, val values: List<Event>)
    class Event(
        val id: Int,
        val starts_at: String,
        val name: String,
        val url: String,
        val poster_image: PosterImagemage,
        val location: Location,
        val categories: List<Categories>,
        val moderation_status: String
    )
    class PosterImagemage(val default_url: String, val uploadcare_url: String)
    class Location(val country: String, val city: String, val address: String)
    class Categories(val id: Int, val name: String)
}
