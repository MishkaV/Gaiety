package model.clientData.orders

data class Payment (
    val amount: Int,
    val discount: Int,
    val paid_at: String,
    val payment_type: String,
    val payment_link: String
)