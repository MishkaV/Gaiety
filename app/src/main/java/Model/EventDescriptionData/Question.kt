package Model.EventDescriptionData

data class Question(
    val comment: String,
    val field_id: String,
    val is_for_every_visitor: Boolean,
    val is_mandatory: Boolean,
    val meta: Meta,
    val name: String,
    val possible_answers: List<PossibleAnswer>,
    val type: String
)