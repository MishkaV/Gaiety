package com.example.gaiety

import Model.ClientData.Client
import Model.ClientData.Orders.Orders
import Model.EventData.Event
import Model.EventData.Value
import Presenter.HomeScreen.NumAdapter
import View.Activities.ItemMore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import View.Fragments.MeScreen.MyTicketsScreen.MyTicketsFragment
import android.content.Intent
import android.os.Build
import com.google.android.gms.common.api.Api
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyclerview_item.view.*
import kotlinx.android.synthetic.main.recyclerview_item_mytickets.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NumAdapterMyTickets(
    val homeFeed: Client
) : RecyclerView.Adapter<NumAdapterMyTickets.NumHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumHolder {
        val itemHolder = LayoutInflater.from(parent?.context).inflate(R.layout.recyclerview_item_mytickets, parent, false)
        return NumHolder(itemHolder, homeFeed)
    }
    override fun onBindViewHolder(holder: NumHolder, position: Int) {
        holder.itemView.myticketsName.text = homeFeed.orders.get(position).event.name
        holder.itemView.myticketsCity.text = homeFeed.orders.get(position).event.city
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")
            val date = LocalDateTime.parse(homeFeed.orders.get(position).event.starts_at, formatter)
            holder.itemView.myticketsDate.text =
                "Дата: " + date.dayOfMonth.toString() + ":" + date.monthValue + ":" + date.year +
                        "\nВремя: " + date.hour + ":%02d".format(date.minute.toInt())
        }
        else
            holder.itemView.myticketsDate.text = homeFeed.orders.get(position).event.starts_at


        if (homeFeed.orders.get(position).organization.logo_image == null) {
            holder.itemView.myticketsImageUrl.setImageResource(R.drawable.logo)
        } else {
            Picasso.get()
                .load(homeFeed.orders.get(position).organization.logo_image.default_url)
                .into(holder.itemView.myticketsImageUrl)
        }
    }

    class NumHolder(view: View, var homeFeed: Client) : RecyclerView.ViewHolder(view) {
        //NumHolder
    }

    override fun getItemCount(): Int {
        return homeFeed.orders.count()
    }

    fun addItem(item: Orders){
        homeFeed.orders  = homeFeed.orders + item
    }
}
