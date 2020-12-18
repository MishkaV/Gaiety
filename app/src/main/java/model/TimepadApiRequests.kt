package model

import Model.ClientData.Client
import Model.EventData.Event
import Model.EventData.Value
import Model.EventDescriptionData.EventDescription
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

}