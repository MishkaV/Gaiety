package model.EventData

import com.google.gson.annotations.SerializedName

data class Event (
    @SerializedName("total")
    val total: Int,
    @SerializedName("values")
    var values: List<Value>
)