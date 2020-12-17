package model.clientData.orders

import model.clientData.orders.event.Event
import model.clientData.organizations.Organizations
import model.clientData.orders.tickets.Tickets

data class Orders (
    val id: Int,
    val created_at: String,
    val status: Status,
    val mail: String,
    val payment: Payment,
    val tickets: List<Tickets>,
    val event: Event,
    val organization: Organizations,
    val subscribed_to_newsletter: Boolean
)