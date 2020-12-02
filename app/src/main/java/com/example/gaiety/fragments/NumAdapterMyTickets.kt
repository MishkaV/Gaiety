package com.example.gaiety

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gaiety.fragments.myTicketsFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyclerview_item_mytickets.view.*

class NumAdapterMyTickets(val homeFeed: myTicketsFragment.HomeFeed) : RecyclerView.Adapter<NumAdapterMyTickets.NumHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumHolder {
        val itemHolder = LayoutInflater.from(parent?.context).inflate(R.layout.recyclerview_item_mytickets, parent, false)
        return NumHolder(itemHolder)
    }
    override fun onBindViewHolder(holder: NumHolder, position: Int) {
        holder.itemView.myticketsName.text = homeFeed.values.get(position).name
        holder.itemView.myticketsCity.text = homeFeed.values.get(position).location.city
        holder.itemView.myticketsDate.text = homeFeed.values.get(position).starts_at

        if (homeFeed?.values?.get(position)?.poster_image == null) {
            holder.itemView.myticketsImageUrl.setImageResource(R.drawable.logo)
        } else {
            Picasso.get()
                .load(homeFeed?.values?.get(position)?.poster_image?.default_url)
                .into(holder.itemView.myticketsImageUrl)
        }
    }

    override fun getItemCount(): Int {
        return homeFeed.values.count()
    }

    class NumHolder(view: View) : RecyclerView.ViewHolder(view) {
        // NumHolder
    }
}
