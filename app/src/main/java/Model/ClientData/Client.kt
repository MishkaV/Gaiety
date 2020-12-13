package Model.ClientData

import Model.ClientData.Orders.Orders
import Model.ClientData.Organizations.Organizations

data class Client (
    val active: Boolean,
    val client_id: String,
    val user_id: String,
    val user_email: String,
    val organizations: List<Organizations>,
    var orders: List<Orders>
) {
    operator fun plus (newValue: Orders): List<Orders> {
        return orders + newValue
    }
}