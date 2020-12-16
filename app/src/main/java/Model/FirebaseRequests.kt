package Model

import android.util.Log
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception
import java.util.*
import kotlin.collections.HashMap

private const val KEY_TITLE = "title"
private const val KEY_DESCRIPTION = "description"
private const val LOG_POST_FIREBASE = "LOG_POST_FIREBASE"


class FirebaseRequests {
    fun postFirestoreRequest (title: String, description: Any){
        val storage = FirebaseFirestore.getInstance()
        val map = HashMap<String, Any>()
        map.put(KEY_TITLE, title)
        map.put(KEY_DESCRIPTION, description)

        storage.collection("Users")
            .document("Misha")
            .set(map)
            .addOnSuccessListener(object : OnSuccessListener<Void> {
                override fun onSuccess(p0: Void?) {
                    Log.d(LOG_POST_FIREBASE, "Success")
                }
            })
            .addOnFailureListener(object : OnFailureListener{
                override fun onFailure(p0: Exception) {
                    Log.d(LOG_POST_FIREBASE, p0.toString())
                }
            })
    }
}

