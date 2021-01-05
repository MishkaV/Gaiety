package model.ClientData.organizations

import com.google.gson.annotations.SerializedName

data class Organizations (
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description_html")
    val description_html: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("logo_image")
    val logo_image: LogoImage,
    @SerializedName("subdomain")
    val subdomain: String,
    @SerializedName("permissions")
    val permissions: List<String>

)