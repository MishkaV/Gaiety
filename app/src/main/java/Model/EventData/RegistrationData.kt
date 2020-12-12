package Model.EventData

data class RegistrationData(
    val is_registration_open: Boolean,
    val price_max: Int,
    val price_min: Int,
    val sale_ends_at: String,
    val tickets_limit: Int,
    val tickets_total: Int
)