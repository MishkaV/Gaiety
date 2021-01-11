package model.ClientData.orders

import model.ClientData.orders.Event.Event
import model.ClientData.organizations.Organizations
import model.ClientData.orders.Tickets.Tickets
import com.google.gson.annotations.SerializedName

data class Orders (
    @SerializedName("id")
    val id: Int,
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("status")
    val status: Status,
    @SerializedName("mail")
    val mail: String,
    @SerializedName("payment")
    val payment: Payment,
    @SerializedName("tickets")
    val tickets: List<Tickets>,
    @SerializedName("event")
    val event: Event,
    @SerializedName("organization")
    val organization: Organizations,
    @SerializedName("subscribed_to_newsletter")
    val subscribed_to_newsletter: Boolean
)