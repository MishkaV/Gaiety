package Model.ClientData.Orders.Event

import com.google.gson.annotations.SerializedName

data class Location (
    @SerializedName("country")
    val country: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("coordinates")
    val coordinates: List<Float>
)