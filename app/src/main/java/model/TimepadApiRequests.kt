package model

import model.ClientData.Client
import model.EventData.Event
import model.EventData.Value
import model.EventDescriptionData.EventDescription
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

private const val token: String = "339db094139f6229bbb3a20009c28dd0da832523"
//private const val token: String = "fb5b9e1ef57d53cf3371140fc1e00aabc32ec3fd"

interface TimepadApiRequests {
    @Headers("Authorization: Bearer ${token}")
    @GET("/v1/events.json")
    fun getEventData(
        @Query("limit") limit : Int,
        @Query("skip") skip : Int,
        @Query("fields") fields : String,
        @Query("sort") sort : String
    ): Call<Event>

    @Headers("Authorization: Bearer ${token}")
    @GET("/v1/events.json")
    fun getEventDataFilteredByCity(
        @Query("limit") limit : Int,
        @Query("skip") skip : Int,
        @Query("cities") cities : String,
        @Query("fields") fields : String,
        @Query("sort") sort : String
    ): Call<Event>

    @Headers("Authorization: Bearer ${token}")
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
        @Query("sort") sort : String
    ): Call<Event>

    @Headers("Authorization: Bearer ${token}")
    @GET("/introspect?token=${token}")
    fun getClientData(
    ): Call<Client>

    @Headers("Authorization: Bearer ${token}")
    @GET("/v1/events/{eventName}")
    fun getEventDecriptionData(
        @Path("eventName") eventName: String
    ): Call<EventDescription>

    @Headers("Authorization: Bearer ${token}")
    @GET("/v1/events/{eventName}")
    fun getEventFavData(
        @Path("eventName") eventName: String
    ): Call<Value>

    @Headers("Authorization: Bearer ${token}")
    @GET("/v1/events.json")
    fun getEventDataMap(
        @Query("limit") limit : Int,
        @Query("cities") cities : String,
        @Query("fields") fields : String,
        @Query("sort") sort : String
    ): Call<Event>
}