package Model

import Model.EventData.Event
import Model.EventData.Value
import Presenter.HomeScreen.NumAdapter
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.gaiety.NumAdapterMyFavoriteEvent
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.jackandphantom.androidlikebutton.AndroidLikeButton
import java.lang.Exception
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

private const val KEY_TITLE = "title"
private const val KEY_DESCRIPTION = "description"
private const val LOG_POST_FIREBASE = "LOG_POST_FIREBASE"

class FirebaseRequests {
    private var currentUserMail: String = ""

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

    fun setCurrentUser(mail: String) {
        if (currentUserMail == "")
            currentUserMail = mail
    }

    fun createNewUser(user_mail: String){
        val storage = FirebaseFirestore.getInstance()
        val map = HashMap<String, ArrayList<Int>>()
        map.put("favorite_events", arrayListOf())
        storage.collection("Users").document(user_mail)
            .set(map)
            .addOnSuccessListener(object : OnSuccessListener<Void> {
                override fun onSuccess(p0: Void?) {
                    Log.d(LOG_POST_FIREBASE, "Success")
                    setCurrentUser(user_mail)
                }
            })
            .addOnFailureListener(object : OnFailureListener{
                override fun onFailure(p0: Exception) {
                    Log.d(LOG_POST_FIREBASE, p0.toString())
                }
            })
    }

    fun addFavoriteEvent(event: Int) {
        val storage = FirebaseFirestore
            .getInstance()
            .collection("Users")
            .document(currentUserMail)

        storage.update("favorite_events", FieldValue.arrayUnion(event))
    }

    fun deleteFavoriteEvent(event: Int) {
        val storage = FirebaseFirestore
            .getInstance()
            .collection("Users")
            .document(currentUserMail)

        storage.update("favorite_events", FieldValue.arrayRemove(event))
    }

    fun inFavoriteEvents(
        event: Int,
        likeButton: AndroidLikeButton
    ) {
        val storage = FirebaseFirestore
            .getInstance()
            .document("Users/${currentUserMail}")
        var checkIn : Boolean = false
        storage.get()
            .addOnSuccessListener(object : OnSuccessListener<DocumentSnapshot>{
                override fun onSuccess(p0: DocumentSnapshot?){
                    if (p0 != null) {
                        if (p0.exists()){
                            Log.d(LOG_POST_FIREBASE, "Success upload")
                            val arr = p0.get("favorite_events") as List<Long>
                            likeButton.setCurrentlyLiked(arr.contains(event.toLong()))
                        }
                    }
                }
            })
            .addOnFailureListener(object : OnFailureListener {
                override fun onFailure(p0: Exception) {
                    Log.d(LOG_POST_FIREBASE, p0.toString())
                }
            })
    }

    fun getFavoriteEvents(
        numAdapter: NumAdapterMyFavoriteEvent
    ) {

        val storage = FirebaseFirestore
            .getInstance()
            .document("Users/${currentUserMail}")
        storage.get()
            .addOnSuccessListener(object : OnSuccessListener<DocumentSnapshot>{
                override fun onSuccess(p0: DocumentSnapshot?){
                    if (p0 != null) {
                        if (p0.exists()){
                            Log.d(LOG_POST_FIREBASE, "Success upload")
                            var arr = p0.get("favorite_events") as List<Long>
                            for (r in arr) {
                                NetworkRequests().eventFavRequest(numAdapter, r)
                                Log.d(LOG_POST_FIREBASE, r.toString())
                            }
                            Log.d(LOG_POST_FIREBASE, arr.toString())
                        }
                    }
                }
            })
            .addOnFailureListener(object : OnFailureListener {
                override fun onFailure(p0: Exception) {
                    Log.d(LOG_POST_FIREBASE, p0.toString())
                }
            })
    }
}

