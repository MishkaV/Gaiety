package Model.ClientData.Orders.Tickets

data class TicketType (
    val id: Int,
    val name: String,
    val description: String,
    val buy_amount_min: Int,
    val buy_amount_max: Int,
    val price: Int,
    val is_promocode_locked: Boolean,
    val remaining: Int,
    val sale_ends_at: String,
    val sale_starts_at: String,
    val public_key: String,
    val is_active: Boolean,
    val ad_partner_profit: Int,
    val send_personal_links: Boolean,
    val sold: Int,
    val attended: Int,
    val limit: Int,
    val status: String
)