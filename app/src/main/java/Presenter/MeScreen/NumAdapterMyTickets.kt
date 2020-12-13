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
import com.google.android.gms.common.api.Api
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyclerview_item.view.*
import kotlinx.android.synthetic.main.recyclerview_item_mytickets.view.*

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
        init {
            view.setOnClickListener {
                val intent = Intent(view.context, ItemMore::class.java)
                intent.putExtra("orderId", homeFeed.orders.get(adapterPosition).id)
                view.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return homeFeed.orders.count()
    }

    fun addItem(item: Orders){
        homeFeed.orders  = homeFeed.orders + item
    }
}
