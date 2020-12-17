package model.clientData.orders.event

data class Location (
    val country: String,
    val city: String,
    val address: String,
    val coordinates: List<Float>
)