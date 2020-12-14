package Model.EventData

class Event (
    val total: Int,
    var values: List<Value>
){
    operator fun plus (newValue: Value): List<Value> {
        return values + newValue
    }
}