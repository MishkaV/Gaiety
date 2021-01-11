package model.EventDescriptionData

import com.google.gson.annotations.SerializedName

data class LogoImage(
    @SerializedName("default_url")
    val default_url: String,
    @SerializedName("uploadcare_url")
    val uploadcare_url: String
)