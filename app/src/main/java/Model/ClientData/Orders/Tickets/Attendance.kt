package Model.ClientData.Orders.Tickets

import com.google.gson.annotations.SerializedName

data class Attendance (
    @SerializedName("starts_at")
    val starts_at: String,
    @SerializedName("ends_at")
    val ends_at: String
)