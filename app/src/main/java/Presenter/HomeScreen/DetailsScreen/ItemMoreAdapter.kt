package Presenter.HomeScreen.DetailsScreen

import Model.EventDescriptionData.EventDescription
import View.Activities.ItemMore
import android.os.Build
import android.text.Html
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.gaiety.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyclerview_item.view.*
import kotlinx.android.synthetic.main.recyclerview_item_more.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ItemMoreAdapter(val event: EventDescription, val image: ImageView) : RecyclerView.Adapter<ItemMoreAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder = LayoutInflater.from(parent?.context).inflate(R.layout.recyclerview_item_more, parent, false)
        return ItemHolder(
            itemHolder
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        if (event.poster_image == null) {
            image.setImageResource(R.drawable.logo)
        } else {
            Picasso.get()
                .load(event.poster_image.default_url)
                .into(image)
        }
        when (position) {
            0 -> {
                holder.itemView.textOfHead.text = Html.fromHtml(event.description_html)
                holder.itemView.textOfHead.movementMethod = ScrollingMovementMethod()
                holder.itemView.head.text = "Описание"
            }
            1 -> {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")
                var string: String = ""
                if (event.starts_at != null && event.ends_at != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                        val dateBegin = LocalDateTime.parse(event.starts_at, formatter)
                        val dateEnd = LocalDateTime.parse(event.ends_at, formatter)

                        string = "Начало: " + dateBegin.dayOfMonth.toString() + ":" +
                                dateBegin.monthValue + ":" + dateBegin.year +
                                " - " + dateBegin.hour + ":" + dateBegin.minute.toInt().format(2)
                        string = string + "\nКонец: " + dateEnd.dayOfMonth.toString() + ":" +
                                dateEnd.monthValue + ":" + dateEnd.year +
                                " - " + dateEnd.hour + ":" + dateEnd.minute.toInt().format(2)
                    } else {
                        string = "Начало: " + event.starts_at
                        string = string + "\nКонец: " + event.ends_at
                    }
                }
                holder.itemView.textOfHead.text = string
                holder.itemView.textOfHead.movementMethod = ScrollingMovementMethod()
                holder.itemView.head.text = "Когда"
            }
            2 -> {
                var string: String = "Страна: " + event.location.country
                string = string + "\nГород: " + event.location.city
                if (event.location.address == null)
                    string = string + "\nАдрес: -"
                else
                    string = string + "\nАдрес: " + event.location.address
                holder.itemView.textOfHead.text = string
                holder.itemView.textOfHead.movementMethod = ScrollingMovementMethod()
                holder.itemView.head.text = "Где"
            }
        }
    }

    fun Int.format(digits: Int) = "%0${digits}d".format(this)

    override fun getItemCount(): Int {
        return 3
    }

    class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        // ItemHolder
    }
}
