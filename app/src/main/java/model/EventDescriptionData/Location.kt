package model.EventDescriptionData

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("address")
    val address: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("coordinates")
    val coordinates: List<Float>,
    @SerializedName("country")
    val country: String
)