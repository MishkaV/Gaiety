package model.EventDescriptionData

import com.google.gson.annotations.SerializedName

data class Question(
    @SerializedName("comment")
    val comment: String,
    @SerializedName("field_id")
    val field_id: String,
    @SerializedName("is_for_every_visitor")
    val is_for_every_visitor: Boolean,
    @SerializedName("is_mandatory")
    val is_mandatory: Boolean,
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("name")
    val name: String,
    @SerializedName("possible_answers")
    val possible_answers: List<PossibleAnswer>,
    @SerializedName("type")
    val type: String
)