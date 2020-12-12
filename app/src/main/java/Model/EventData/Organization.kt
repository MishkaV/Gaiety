package Model.EventData

data class Organization(
    val description_html: String,
    val id: Int,
    val logo_image: LogoImage,
    val name: String,
    val permissions: List<String>,
    val subdomain: String,
    val url: String
)