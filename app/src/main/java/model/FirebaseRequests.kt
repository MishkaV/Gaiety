package model

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gaiety.NumAdapterMyFavoriteEvent
import com.example.gaiety.R
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.jackandphantom.androidlikebutton.AndroidLikeButton
import java.lang.Exception
import kotlin.collections.HashMap
private const val KEY_TITLE = "title"
private const val KEY_DESCRIPTION = "description"
private const val LOG_POST_FIREBASE = "LOG_POST_FIREBASE"

class FirebaseRequests {
    private var currentUserMail: String = ""
    private var favevents: Any = arrayListOf<Int>()
    private var name: String = ""
    private var surname: String = ""
    private var token: String = ""

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
        if (currentUserMail == "") {
            currentUserMail = mail
            val storage = FirebaseFirestore
                .getInstance()
                .document("Users/${currentUserMail}")
            storage.get()
                .addOnSuccessListener(object : OnSuccessListener<DocumentSnapshot> {
                    override fun onSuccess(p0: DocumentSnapshot?) {
                        if (p0 != null) {
                            if (p0.exists()) {
                                Log.d(LOG_POST_FIREBASE, "Success upload")
                                name = p0.get("name") as String
                                surname = p0.get("surname") as String
                                favevents = p0.get("favorite_events") as List<Long>
                                token = p0.get("token") as String

                            }
                        }
                    }
                })

        }
    }

    fun createNewUser(user_mail: String, user_name: String, user_surname: String){
        val storage = FirebaseFirestore.getInstance()
        val map = HashMap<String, Any>()
        map["favorite_events"] = arrayListOf<Int>()
        map["name"] = user_name
        map["surname"] = user_surname
        map["token"] = ""
        name = user_name
        surname = user_surname
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

    fun changeUser (user_name: String, user_surname: String){
        val storage = FirebaseFirestore.getInstance()
        val map = HashMap<String, Any>()
        map["favorite_events"] = favevents
        map["name"] = user_name
        map["surname"] = user_surname
        map["token"] = token
        name = user_name
        surname = user_surname
        storage.collection("Users").document(currentUserMail)
            .set(map)
    }

    fun changeToken(tokenNew: String) {
        val storage = FirebaseFirestore.getInstance()
        val map = HashMap<String, Any>()
        map["favorite_events"] = favevents
        map["name"] = name
        map["surname"] = surname
        map["token"] = tokenNew
        token = tokenNew
        storage.collection("Users").document(currentUserMail)
            .set(map)

    }

    fun addFavoriteEvent(event: Int) {
        val storage = FirebaseFirestore
            .getInstance()
            .collection("Users")
            .document(currentUserMail)
        favevents = FieldValue.arrayUnion(event)
        storage.update("favorite_events", FieldValue.arrayUnion(event))
    }

    fun deleteFavoriteEvent(event: Int) {
        val storage = FirebaseFirestore
            .getInstance()
            .collection("Users")
            .document(currentUserMail)
        favevents = FieldValue.arrayRemove(event)
        storage.update("favorite_events", FieldValue.arrayRemove(event))
    }

    fun inFavoriteEvents(
        event: Int,
        likeButton: AndroidLikeButton
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
        numList: RecyclerView
    ) {

        val storage = FirebaseFirestore
            .getInstance()
            .document("Users/${currentUserMail}")
        storage.get()
            .addOnSuccessListener(object : OnSuccessListener<DocumentSnapshot> {
                override fun onSuccess(p0: DocumentSnapshot?) {
                    if (p0 != null) {
                        if (p0.exists()) {
                            Log.d(LOG_POST_FIREBASE, "Success upload")
                            var arr = p0.get("favorite_events") as List<Long>
                            for (r in arr) {
                                NetworkRequests().eventFavRequest(numList.adapter as NumAdapterMyFavoriteEvent, r)
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

    fun getAboutMe(
        view: View
    ) {
        val name: TextView = view.findViewById(R.id.nameText) as TextView
        val surname: TextView = view.findViewById(R.id.surnameText) as TextView
        val storage = FirebaseFirestore
            .getInstance()
            .document("Users/${currentUserMail}")
        storage.get()
            .addOnSuccessListener(object : OnSuccessListener<DocumentSnapshot> {
                override fun onSuccess(p0: DocumentSnapshot?) {
                    if (p0 != null) {
                        if (p0.exists()) {
                            Log.d(LOG_POST_FIREBASE, "Success upload")
                            var nameText = p0.get("name") as String
                            var surnameText = p0.get("surname") as String
                            nameText = "Имя: $nameText"
                            surnameText = "Фамилия: $surnameText"
                            name.text = nameText
                            surname.text = surnameText

                            Log.d(LOG_POST_FIREBASE, nameText)
                            Log.d(LOG_POST_FIREBASE, surnameText)
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

    fun tokenGetter(): String {
        return if (token == "") "339db094139f6229bbb3a20009c28dd0da832523"
        else token
    }

}

