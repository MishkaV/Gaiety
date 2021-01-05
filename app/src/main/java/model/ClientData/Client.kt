package model.ClientData

import model.ClientData.orders.Orders
import model.ClientData.organizations.Organizations
import com.google.gson.annotations.SerializedName

data class Client (
    @SerializedName("active")
    val active: Boolean,
    @SerializedName("client_id")
    val client_id: String,
    @SerializedName("user_id")
    val user_id: String,
    @SerializedName("user_email")
    val user_email: String,
    @SerializedName("organizations")
    var organizations: List<Organizations>,
    @SerializedName("orders")
    var orders: List<Orders>
) {
    operator fun plus (newValue: Orders): List<Orders> {
        return orders + newValue
    }

    operator fun plus (newValue: Organizations): List<Organizations> {
        return organizations + newValue
    }
}