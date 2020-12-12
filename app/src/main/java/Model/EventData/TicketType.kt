package Model.EventData

data class TicketType(
    val ad_partner_profit: Int,
    val attended: Int,
    val buy_amount_max: Int,
    val buy_amount_min: Int,
    val description: String,
    val id: Int,
    val is_active: Boolean,
    val is_promocode_locked: Boolean,
    val limit: Int,
    val name: String,
    val price: Int,
    val public_key: String,
    val remaining: Int,
    val sale_ends_at: String,
    val sale_starts_at: String,
    val send_personal_links: Boolean,
    val sold: Int,
    val status: String
)