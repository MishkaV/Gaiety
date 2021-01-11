package presenter.homeScreen

import model.EventData.Event
import model.EventData.Value
import view.activities.ItemMore
import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.gaiety.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyclerview_item.view.*
import presenter.homeScreen.detailsScreen.NumHolder
import presenter.homeScreen.detailsScreen.ProgressBarHolder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NumAdapter(var homeFeed: Event) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isLoading = false
    private val VIEWTYPE_EVENT = 1
    private val VIEWTYPE_PROGRESS = 2


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //if (viewType == VIEWTYPE_EVENT) {
            val itemHolder = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
            return NumHolder(itemHolder, homeFeed)
        //} else {
            return ProgressBarHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_item_progressbar, parent, false))
        //}
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //if (holder is NumHolder) {
            holder.itemView.homeName.text =
                Html.fromHtml(homeFeed.values.get(position).name).toString()
            holder.itemView.homeCity.text = homeFeed.values.get(position).location.city
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")
                val date = LocalDateTime.parse(homeFeed.values.get(position).starts_at, formatter)
                holder.itemView.homeDate.text =
                    "Дата: " + date.dayOfMonth.toString() + ":" + date.monthValue + ":" + date.year +
                            "\nВремя: " + date.hour + ":" + date.minute.toInt().format(2)
            } else
                holder.itemView.homeDate.text = homeFeed.values.get(position).starts_at
            if (homeFeed?.values?.get(position)?.poster_image == null) {
                holder.itemView.homeImageUrl.setImageResource(R.drawable.logo)
            } else {
                Picasso.get()
                    .load(homeFeed?.values?.get(position)?.poster_image?.default_url)
                    .into(holder.itemView.homeImageUrl)
            }
        //}
    }

    private fun Int.format(digits: Int) = "%0${digits}d".format(this)

    override fun getItemCount(): Int {
        return if (isLoading) {
            homeFeed.values.count() + 1
        } else {
            homeFeed.values.count()
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == homeFeed.values.size - 1 && isLoading)
            return VIEWTYPE_PROGRESS
        else
            return VIEWTYPE_EVENT
    }

    fun addItem(item: Value){
        homeFeed.values  = homeFeed.values + item
    }

    fun removeAllItems() {
        homeFeed.values = emptyList()
    }

    fun showProgress() {
        isLoading = true
        notifyItemInserted(homeFeed.values.count() + 1)
    }


    fun hideProgress() {
        isLoading = false
    }
}