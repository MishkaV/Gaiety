package Model.EventData

import com.google.gson.annotations.SerializedName

data class Organization(
    @SerializedName("description_html")
    val description_html: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("logo_image")
    val logo_image: LogoImage,
    @SerializedName("name")
    val name: String,
    @SerializedName("permissions")
    val permissions: List<String>,
    @SerializedName("subdomain")
    val subdomain: String,
    @SerializedName("url")
    val url: String
)