package Model.ClientData

import Model.ClientData.Orders.Orders
import Model.ClientData.Organizations.Organizations

data class Client (
    val active: Boolean,
    val client_id: String,
    val user_id: String,
    val user_email: String,
    var organizations: List<Organizations>,
    var orders: List<Orders>
) {
    operator fun plus (newValue: Orders): List<Orders> {
        return orders + newValue
    }

    operator fun plus (newValue: Organizations): List<Organizations> {
        return organizations + newValue
    }
}