package View.Activities

import Model.NetworkRequests
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import Presenter.HomeScreen.DetailsScreen.ItemMoreAdapter
import com.example.gaiety.R
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import okhttp3.*
import java.io.IOException

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
    }
}
