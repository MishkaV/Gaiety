package Model.ClientData.Orders.Tickets

data class Tickets (
    val id: Int,
    val number: String,
    val price_nominal: Int,
    val ticket_type: TicketType,
    val attendance: Attendance,
    val place: Place,
    val personal_link: String,
    val eticket_link: String,
    val check_in: Boolean
)