package model

import android.text.Html
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.gaiety.NumAdapterMyFavoriteEvent
import com.example.gaiety.NumAdapterMyOrganizations
import com.example.gaiety.NumAdapterMyTickets
import com.example.gaiety.R
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import model.ClientData.Client
import model.EventData.Event
import model.EventData.Value
import model.EventDescriptionData.EventDescription
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import presenter.homeScreen.NumAdapter
import presenter.homeScreen.detailsScreen.ItemMoreAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val TAG = "TAG_EVENT_REQUEST"
private const val TAG_EVEN_DESCRIPTION = "TAG_EVEN_DESCRIPTION"


class NetworkRequests {
    private val urlTimepad: String = "https://api.timepad.ru"

    private fun createRetrofit(url: String): Retrofit {
        //Можно interceptor убрать(вместе с client в api), для логов
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val api = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return api
    }

    fun eventRequest(
        numList: RecyclerView,
        skip: Int,
        numAdapter: NumAdapter
    ) {
        val api = createRetrofit(urlTimepad)
        val timepadApiRequests = api.create(TimepadApiRequests::class.java)
        val call = timepadApiRequests.getEventData(10, skip, "location", "+starts_at")

        if (numAdapter.itemCount < 100) {
            call.enqueue(
                object : Callback<Event> {
                    override fun onResponse(call: Call<Event>, response: Response<Event>) {
                        for (item in response.body()!!.values)
                            if (!(item in numAdapter.homeFeed.values)) {
                                numAdapter.addItem(item)
                            }
                        numAdapter.notifyDataSetChanged()
                        Log.d(TAG, "Success")
                    }

                    override fun onFailure(call: Call<Event>, t: Throwable) {
                        Log.d(TAG, "Someting wrong...")
                        Log.d(TAG, t.localizedMessage)
                    }
                })
        } else
            Log.d(TAG, "Stop or will be overload")
    }

    fun eventRequestDataFiltered(
        numList: RecyclerView,
        view: View?,
        skip: Int,
        numAdapter: NumAdapter,
        cities: String?,
        keywords: String?,
        price_min: String?,
        price_max: String?,
        starts_at_min: String?,
        starts_at_max: String?
    ) {

        val api = createRetrofit(urlTimepad)
        val timepadApiRequests = api.create(TimepadApiRequests::class.java)
        val call = timepadApiRequests.getEventDataFiltered(
            10,
            skip,
            cities,
            keywords,
            price_min,
            price_max,
            starts_at_min,
            starts_at_max,
            "location",
            "+starts_at"
        )

        call.enqueue(
            object : Callback<Event> {
                override fun onResponse(call: Call<Event>, response: Response<Event>) {
                    for (item in response.body()!!.values)
                        if (item !in numAdapter.homeFeed.values) {
                            numAdapter.addItem(item)
                        }
                    numAdapter.notifyDataSetChanged()
                    val progressBar = view?.findViewById<ProgressBar>(R.id.progressBar)
                    if (progressBar != null) {
                        progressBar.visibility = View.INVISIBLE
                    }
                    Log.d(TAG, "Success")
                }

                override fun onFailure(call: Call<Event>, t: Throwable) {
                    Log.d(TAG, t.localizedMessage)
                }
            })

    }

    fun eventDescriptionRequest(
        numList: RecyclerView,
        image: ImageView,
        id: Int
    ) {
        val api = createRetrofit(urlTimepad)
        val timepadApiRequests = api.create(TimepadApiRequests::class.java)
        val call = timepadApiRequests.getEventDecriptionData(id.toString())

        call.enqueue(
            object : Callback<EventDescription> {
                override fun onFailure(call: Call<EventDescription>, t: Throwable) {
                    Log.d(TAG_EVEN_DESCRIPTION, t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<EventDescription>,
                    response: Response<EventDescription>
                ) {
                    numList.adapter = ItemMoreAdapter(response.body()!!, image)
                    Log.d(TAG_EVEN_DESCRIPTION, "Success")
                }
            })
    }

    fun myTicketsRequest(
        numAdapter: NumAdapterMyTickets
    ) {
        val api = createRetrofit(urlTimepad)
        val timepadApiRequests = api.create(TimepadApiRequests::class.java)
        val call = timepadApiRequests.getClientData()

        call.enqueue(
            object : Callback<Client> {
                override fun onResponse(call: Call<Client>, response: Response<Client>) {
                    for (item in response.body()!!.orders)
                        if (!(item in numAdapter.homeFeed.orders)) {
                            numAdapter.addItem(item)
                        }
                    numAdapter.notifyDataSetChanged()
                    Log.d(TAG, "Success")
                }

                override fun onFailure(call: Call<Client>, t: Throwable) {
                    Log.d(TAG, t.localizedMessage)
                }
            })
    }

    fun eventFavRequest(
        numAdapter: NumAdapterMyFavoriteEvent,
        id: Long
    ) {
        val api = createRetrofit(urlTimepad)
        val timepadApiRequests = api.create(TimepadApiRequests::class.java)
        val call = timepadApiRequests.getEventFavData(id.toString())

        call.enqueue(
            object : Callback<Value> {

                override fun onResponse(
                    call: Call<Value>,
                    response: Response<Value>
                ) {
                    if (response.body()?.id.toString().isNotEmpty()) {
                        numAdapter.addItem(response.body()!!)
                        numAdapter.notifyDataSetChanged()
                        Log.d(TAG_EVEN_DESCRIPTION, "Success")
                    }
                }

                override fun onFailure(call: Call<Value>, t: Throwable) {
                    Log.d(TAG_EVEN_DESCRIPTION, t.localizedMessage)
                }
            })
    }

    fun myOrganizationsRequest(
        numAdapter: NumAdapterMyOrganizations
    ) {
        val api = createRetrofit(urlTimepad)
        val timepadApiRequests = api.create(TimepadApiRequests::class.java)
        val call = timepadApiRequests.getClientData()

        call.enqueue(
            object : Callback<Client> {
                override fun onResponse(call: Call<Client>, response: Response<Client>) {
                    for (item in response.body()!!.organizations)
                        if (!(item in numAdapter.homeFeed.organizations)) {
                            numAdapter.addItem(item)
                        }
                    numAdapter.notifyDataSetChanged()
                    Log.d(TAG, "Success")
                }

                override fun onFailure(call: Call<Client>, t: Throwable) {
                    Log.d(TAG, t.localizedMessage)
                }
            })
    }


    fun eventRequestMap(mapboxMap: MapboxMap, city: String) {
        val api = createRetrofit(urlTimepad)
        val timepadApiRequests = api.create(TimepadApiRequests::class.java)
        val call = timepadApiRequests.getEventDataMap(100, city, "location","+starts_at")
        call.enqueue(
            object : Callback<Event> {
                override fun onResponse(call: Call<Event>, response: Response<Event>) {
                    for (item in response.body()!!.values) {
                        if (item.location != null) {
                            if (item.location.coordinates != null) {
                                mapboxMap?.addMarker(
                                    MarkerOptions()
                                        .position(
                                            LatLng(
                                                item.location.coordinates[0].toDouble(),
                                                item.location.coordinates[1].toDouble()
                                            )
                                        )
                                        .title(Html.fromHtml(item.name).toString())
                                )
                            }
                        }
                    }
                    Log.d(TAG, "Success")
                }

                override fun onFailure(call: Call<Event>, t: Throwable) {
                    Log.d(TAG, "Someting wrong...")
                    Log.d(TAG, t.localizedMessage)
                }
            })
    }
}

