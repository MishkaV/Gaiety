package Model.EventDescriptionData

import com.google.gson.annotations.SerializedName

data class RegistrationData(
    @SerializedName("is_registration_open")
    val is_registration_open: Boolean,
    @SerializedName("price_max")
    val price_max: Int,
    @SerializedName("price_min")
    val price_min: Int,
    @SerializedName("sale_ends_at")
    val sale_ends_at: String,
    @SerializedName("tickets_limit")
    val tickets_limit: Int,
    @SerializedName("tickets_total")
    val tickets_total: Int
)