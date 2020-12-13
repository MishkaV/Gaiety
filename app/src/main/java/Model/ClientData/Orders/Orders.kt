package Model.ClientData.Orders

import Model.ClientData.Orders.Event.Event
import Model.ClientData.Organizations.Organizations
import Model.ClientData.Orders.Tickets.Tickets

data class Orders (
    val id: Int,
    val created_at: String,
    val status: Status,
    val mail: String,
    val payment: Payment,
    val tickets: List<Tickets>,
    val promocodes: List<String>,
    val event: Event,
    val organization: Organizations,
    val subscribed_to_newsletter: Boolean
)