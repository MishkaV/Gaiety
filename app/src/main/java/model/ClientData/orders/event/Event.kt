package Model.ClientData.Orders.Event

import com.google.gson.annotations.SerializedName

data class Event (
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("location")
    val location: Location,
    @SerializedName("starts_at")
    val starts_at: String,
    @SerializedName("ends_at")
    val ends_at: String
)