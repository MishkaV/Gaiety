package Model.EventData

import com.google.gson.annotations.SerializedName

data class Widget(
    @SerializedName("code_html")
    val code_html: String
)