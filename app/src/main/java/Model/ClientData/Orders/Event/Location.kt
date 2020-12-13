package Model.ClientData.Orders.Event

data class Location (
    val country: String,
    val city: String,
    val address: String,
    val coordinates: List<Int>
)