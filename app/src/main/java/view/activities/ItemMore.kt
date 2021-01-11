package view.activities

import android.content.Intent
import android.net.Uri
import model.NetworkRequests
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gaiety.R
import com.jackandphantom.androidlikebutton.AndroidLikeButton
import com.jackandphantom.androidlikebutton.AndroidLikeButton.OnLikeEventListener
import com.mapbox.mapboxsdk.Mapbox


private const val TAG_LIKE = "TAG_LIKE"

class ItemMore : AppCompatActivity() {
    lateinit var itemRecycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_more)
        val checkFavorite = intent.getIntExtra("favCheck", 0)
        val image = findViewById<ImageView>(R.id.itemImageUrl)
        var eventId = 0
        eventId = if (checkFavorite == 0) {
            intent.getIntExtra("eventId", 0)
        } else {
            intent.getIntExtra("favId", 0)
        }
        itemRecycler = findViewById(R.id.recyclerViewItemMore)
        itemRecycler.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        if (eventId != 0) {
            NetworkRequests().eventDescriptionRequest(itemRecycler, image, eventId)
        }
        createListnerLike(eventId)
        stateOfLike(eventId)
    }

    private fun createListnerLike(eventId : Int){
        val likeButton = findViewById<AndroidLikeButton>(R.id.likeButtonDescription)
        likeButton.setOnLikeEventListener(object : OnLikeEventListener {

            override fun onLikeClicked(androidLikeButton: AndroidLikeButton) {
                firebaseRequests.addFavoriteEvent(eventId)
                Log.d(TAG_LIKE, "LIKE")
            }
            override fun onUnlikeClicked(androidLikeButton: AndroidLikeButton) {
                firebaseRequests.deleteFavoriteEvent(eventId)
                Log.d(TAG_LIKE, "DISLIKE")
            }
        })
    }

    private fun stateOfLike(eventId : Int){
        val likeButton = findViewById<AndroidLikeButton>(R.id.likeButtonDescription)
        firebaseRequests.inFavoriteEvents(eventId, likeButton)
    }
}
