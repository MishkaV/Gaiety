package Model.ClientData.Orders.Tickets

import com.google.gson.annotations.SerializedName

data class Tickets (
    @SerializedName("id")
    val id: Int,
    @SerializedName("number")
    val number: String,
    @SerializedName("price_nominal")
    val price_nominal: Int,
    @SerializedName("ticket_type")
    val ticket_type: TicketType,
    @SerializedName("attendance")
    val attendance: Attendance,
    @SerializedName("place")
    val place: Place,
    @SerializedName("personal_link")
    val personal_link: String,
    @SerializedName("eticket_link")
    val eticket_link: String,
    @SerializedName("check_in")
    val check_in: Boolean
)