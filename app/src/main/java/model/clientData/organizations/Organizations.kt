package model.clientData.organizations

data class Organizations (
    val id: Int,
    val name: String,
    val description_html: String,
    val url: String,
    val logo_image: LogoImage,
    val subdomain: String,
    val permissions: List<String>

)