package presenter.homeScreen.detailsScreen

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import model.EventData.Event
import view.activities.ItemMore

class NumHolder(view: View, var homeFeed: Event) : RecyclerView.ViewHolder(view) {
    init {
        view.setOnClickListener {
            val intent = Intent(view.context, ItemMore::class.java)
            intent.putExtra("eventId", homeFeed.values.get(adapterPosition).id)
            view.context.startActivity(intent)
        }
    }
}