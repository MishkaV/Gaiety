package Model

import Model.ClientData.Client
import Model.ClientData.Orders.Orders
import Model.EventData.Event
import Model.EventData.Value
import Model.EventDescriptionData.EventDescription
import Presenter.HomeScreen.DetailsScreen.ItemMoreAdapter
import Presenter.HomeScreen.NumAdapter
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.gaiety.NumAdapterMyOrganizations
import com.example.gaiety.NumAdapterMyTickets
import com.google.android.gms.common.api.Api
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val TAG  = "TAG"
private const val TAG_EVEN_DESCRIPTION  = "TAG_EVEN_DESCRIPTION"


class NetworkRequests <T> () {
    private val urlTimepad: String = "https://api.timepad.ru"

    private fun createRetrofit(url : String) : Retrofit {
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
    ){
        val api = createRetrofit(urlTimepad)
        val timepadApiRequests = api.create(TimepadApiRequests::class.java)
        val call = timepadApiRequests.getEventData(10,skip,"location","+starts_at")

        call.enqueue(
            object : Callback<Event> {
                override fun onResponse(call: Call<Event>, response: Response<Event>) {
                    for (item in response.body()!!.values)
                        if (!(item in numAdapter.homeFeed.values)){
                            numAdapter.addItem(item)
                        }
                    numAdapter.notifyDataSetChanged()
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
    ){
        val api = createRetrofit(urlTimepad)
        val timepadApiRequests = api.create(TimepadApiRequests::class.java)
        val call = timepadApiRequests.getEventDecriptionData(id.toString())

        call.enqueue(
            object :Callback<EventDescription> {
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
    ){
        val api = createRetrofit(urlTimepad)
        val timepadApiRequests = api.create(TimepadApiRequests::class.java)
        val call = timepadApiRequests.getClientData()

        call.enqueue(
            object : Callback<Client> {
                override fun onResponse(call: Call<Client>, response: Response<Client>) {
                    for (item in response.body()!!.orders)
                        if (!(item in numAdapter.homeFeed.orders)){
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

    fun myOrganizationsRequest(
        numAdapter: NumAdapterMyOrganizations
    ){
        val api = createRetrofit(urlTimepad)
        val timepadApiRequests = api.create(TimepadApiRequests::class.java)
        val call = timepadApiRequests.getClientData()

        call.enqueue(
            object : Callback<Client> {
                override fun onResponse(call: Call<Client>, response: Response<Client>) {
                    for (item in response.body()!!.organizations)
                        if (!(item in numAdapter.homeFeed.organizations)){
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

}

