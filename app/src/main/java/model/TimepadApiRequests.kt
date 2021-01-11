package model

import android.util.Log
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import model.ClientData.Client
import model.EventData.Event
import model.EventData.Value
import model.EventDescriptionData.EventDescription
import retrofit2.Call
import retrofit2.http.*
import java.lang.Exception

//private const val token: String = "339db094139f6229bbb3a20009c28dd0da832523"
//private const val token: String = "fb5b9e1ef57d53cf3371140fc1e00aabc32ec3fd"

interface TimepadApiRequests {

    @GET("/v1/events.json")
    fun getEventData(
        @Query("limit") limit : Int,
        @Query("skip") skip : Int,
        @Query("fields") fields : String,
        @Query("sort") sort : String,
        @Header("Authorization") token: String
    ): Call<Event>

    @GET("/v1/events.json")
    fun getEventDataFilteredByCity(
        @Query("limit") limit : Int,
        @Query("skip") skip : Int,
        @Query("cities") cities : String,
        @Query("fields") fields : String,
        @Query("sort") sort : String,
        @Header("Authorization") token: String
    ): Call<Event>

    @GET("/v1/events.json")
    fun getEventDataFiltered(
        @Query("limit") limit : Int,
        @Query("skip") skip : Int,
        @Query("cities") cities : String? = null,
        @Query("keywords") keywords : String? = null,
        @Query("price_min") price_min : String? = null,
        @Query("price_max") price_max : String? = null,
        @Query("starts_at_min") starts_at_min : String? = null,
        @Query("starts_at_max") starts_at_max : String? = null,
        @Query("fields") fields : String,
        @Query("sort") sort : String,
        @Header("Authorization") token: String
    ): Call<Event>

    @GET
    fun getClientData(
        @Header("Authorization") token: String,
        @Url tokenGet: String
    ): Call<Client>

    @GET("/v1/events/{eventName}")
    fun getEventDecriptionData(
        @Path("eventName") eventName: String,
        @Header("Authorization") token: String
    ): Call<EventDescription>

    @GET("/v1/events/{eventName}")
    fun getEventFavData(
        @Path("eventName") eventName: String,
        @Header("Authorization") token: String
    ): Call<Value>

    @GET("/v1/events.json")
    fun getEventDataMap(
        @Query("limit") limit : Int,
        @Query("cities") cities : String,
        @Query("fields") fields : String,
        @Query("sort") sort : String,
        @Header("Authorization") token: String
    ): Call<Event>
}