package Model.ClientData.Orders

import com.google.gson.annotations.SerializedName

data class Payment (
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("discount")
    val discount: Int,
    @SerializedName("paid_at")
    val paid_at: String,
    @SerializedName("payment_type")
    val payment_type: String,
    @SerializedName("payment_link")
    val payment_link: String
)