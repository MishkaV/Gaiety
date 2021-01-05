package model.EventData

import com.google.gson.annotations.SerializedName

data class TicketType(
    @SerializedName("ad_partner_profit")
    val ad_partner_profit: Int,
    @SerializedName("attended")
    val attended: Int,
    @SerializedName("buy_amount_max")
    val buy_amount_max: Int,
    @SerializedName("buy_amount_min")
    val buy_amount_min: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_active")
    val is_active: Boolean,
    @SerializedName("is_promocode_locked")
    val is_promocode_locked: Boolean,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("public_key")
    val public_key: String,
    @SerializedName("remaining")
    val remaining: Int,
    @SerializedName("sale_ends_at")
    val sale_ends_at: String,
    @SerializedName("sale_starts_at")
    val sale_starts_at: String,
    @SerializedName("send_personal_links")
    val send_personal_links: Boolean,
    @SerializedName("sold")
    val sold: Int,
    @SerializedName("status")
    val status: String
)