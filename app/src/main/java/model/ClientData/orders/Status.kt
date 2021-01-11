package model.ClientData.orders

import com.google.gson.annotations.SerializedName

data class Status (
    @SerializedName("name")
    val name: String,
    @SerializedName("title")
    val title: String
)