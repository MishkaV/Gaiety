package Model.EventData

import android.util.Log

abstract class Meta(val meta : String) {
    open fun LogMeta() {
        Log.d("TAG", "Succuss EventData")
    }
}