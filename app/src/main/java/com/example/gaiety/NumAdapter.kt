package com.example.gaiety

import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gaiety.fragments.homeFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class NumAdapter(val homeFeed: homeFragment.HomeFeed) : RecyclerView.Adapter<NumAdapter.NumHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumHolder {
        val itemHolder = LayoutInflater.from(parent?.context).inflate(R.layout.recyclerview_item, parent, false)
        return NumHolder(itemHolder, homeFeed)
    }
    override fun onBindViewHolder(holder: NumHolder, position: Int) {
        holder.itemView.homeName.text = Html.fromHtml(homeFeed.values.get(position).name)
        holder.itemView.homeCity.text = homeFeed.values.get(position).location.city
        holder.itemView.homeDate.text = homeFeed.values.get(position).starts_at

        if (homeFeed?.values?.get(position)?.poster_image == null) {
            holder.itemView.homeImageUrl.setImageResource(R.drawable.logo)
        } else {
            Picasso.get()
                .load(homeFeed?.values?.get(position)?.poster_image?.default_url)
                .into(holder.itemView.homeImageUrl)
        }
    }

    override fun getItemCount(): Int {
        return homeFeed.values.count()
    }

    class NumHolder(view: View, var homeFeed: homeFragment.HomeFeed) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener {
                val intent = Intent(view.context, ItemMore::class.java)
                intent.putExtra("eventId", homeFeed.values.get(adapterPosition).id)
                view.context.startActivity(intent)
            }
        }
    }
}
