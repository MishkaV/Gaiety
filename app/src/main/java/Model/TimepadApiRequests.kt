package Model

import Model.EventData.Event
import Model.EventDescriptionData.EventDescription
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

private const val token: String = "993e92d9a94e12efb66ab5ee29b0fbdba217f725"


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
    @GET("/v1/events/{eventName}")
    fun getEventDecriptionData(
        @Path("eventName") eventName: String
    ): Call<EventDescription>

}