package Model.EventDescriptionData

import com.google.gson.annotations.SerializedName

data class EventDescription(
    @SerializedName("access_status")
    val access_status: String,
    @SerializedName("ad_partner_percent")
    val ad_partner_percent: Int,
    @SerializedName("age_limit")
    val age_limit: String,
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("description_html")
    val description_html: String,
    @SerializedName("description_short")
    val description_short: String,
    @SerializedName("ends_at")
    val ends_at: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_sending_free_tickets")
    val is_sending_free_tickets: Boolean,
    @SerializedName("locale")
    val locale: String,
    @SerializedName("location")
    val location: Location,
    @SerializedName("moderation_status")
    val moderation_status: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("organization")
    val organization: Organization,
    @SerializedName("personal_link_title")
    val personal_link_title: String,
    @SerializedName("personal_links")
    val personal_links: List<String>,
    @SerializedName("poster_image")
    val poster_image: PosterImage,
    @SerializedName("properties")
    val properties: List<String>,
    @SerializedName("questions")
    val questions: List<Question>,
    @SerializedName("registration_data")
    val registration_data: RegistrationData,
    @SerializedName("reservation_period")
    val reservation_period: Int,
    @SerializedName("starts_at")
    val starts_at: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("ticket_types")
    val ticket_types: List<TicketType>,
    @SerializedName("tickets_limit")
    val tickets_limit: Int,
    @SerializedName("url")
    val url: String
)