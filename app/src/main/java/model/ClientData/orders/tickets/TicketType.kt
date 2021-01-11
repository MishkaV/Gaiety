package model.ClientData.orders.Tickets

import com.google.gson.annotations.SerializedName

data class TicketType (
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("buy_amount_min")
    val buy_amount_min: Int,
    @SerializedName("buy_amount_max")
    val buy_amount_max: Int,
    @SerializedName("price")
    val price: Int,
    @SerializedName("is_promocode_locked")
    val is_promocode_locked: Boolean,
    @SerializedName("remaining")
    val remaining: Int,
    @SerializedName("sale_ends_at")
    val sale_ends_at: String,
    @SerializedName("sale_starts_at")
    val sale_starts_at: String,
    @SerializedName("public_key")
    val public_key: String,
    @SerializedName("is_active")
    val is_active: Boolean,
    @SerializedName("ad_partner_profit")
    val ad_partner_profit: Int,
    @SerializedName("send_personal_links")
    val send_personal_links: Boolean,
    @SerializedName("sold")
    val sold: Int,
    @SerializedName("attended")
    val attended: Int,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("status")
    val status: String
)