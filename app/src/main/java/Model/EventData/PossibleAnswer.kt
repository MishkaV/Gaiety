package Model.EventData

import com.google.gson.annotations.SerializedName

data class PossibleAnswer(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)