package View.Activities

import Model.FirebaseRequests
import Model.NetworkRequests
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gaiety.R
import com.jackandphantom.androidlikebutton.AndroidLikeButton
import com.jackandphantom.androidlikebutton.AndroidLikeButton.OnLikeEventListener


private const val TAG_LIKE = "TAG_LIKE"

class ItemMore : AppCompatActivity() {
    lateinit var itemRecycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_more)
        val image = findViewById<ImageView>(R.id.itemImageUrl)
        val eventId = intent.getIntExtra("eventId", 0)
        itemRecycler = findViewById(R.id.recyclerViewItemMore)
        itemRecycler.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        if (eventId != 0) {
            NetworkRequests().eventDescriptionRequest(itemRecycler, image,eventId)
        }
        createListnerLike(eventId)
    }

    private fun createListnerLike(eventId : Int){
        val likeButton = findViewById<AndroidLikeButton>(R.id.likeButtonDescription)
        likeButton.setOnLikeEventListener(object : OnLikeEventListener {

            override fun onLikeClicked(androidLikeButton: AndroidLikeButton) {
                val firebaseRequests = FirebaseRequests()
                firebaseRequests.postFirestoreRequest("Favorite Event", eventId)
                Log.d(TAG_LIKE, "LIKE")
            }
            override fun onUnlikeClicked(androidLikeButton: AndroidLikeButton) {
                Log.d(TAG_LIKE, "DISLIKE")
            }
        })
    }
}
