package com.example.gaiety

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.gaiety.fragments.homeFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class NumAdapter(val homeFeed: homeFragment.HomeFeed) : RecyclerView.Adapter<NumAdapter.NumHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumHolder {
        val itemHolder = LayoutInflater.from(parent?.context).inflate(R.layout.recyclerview_item, parent, false)
        return NumHolder(itemHolder)
    }
    override fun onBindViewHolder(holder: NumHolder, position: Int) {
        holder.homeName.text = homeFeed.values.get(position).name
        holder.homeCity.text = homeFeed.values.get(position).location.city
        holder.itemView.homeDate.text = homeFeed.values.get(position).starts_at
        //holder.number.setTextColor(ContextCompat.getColor(holder.number.context, R.color.design_default_color_primary_dark))
        Picasso.get()
            .load(homeFeed?.values?.get(position)?.poster_image?.default_url)
            .into(holder.itemView.homeImageUrl)
    }


    override fun getItemCount(): Int {
        return homeFeed.values.count()
    }

    class NumHolder(view: View) : RecyclerView.ViewHolder(view) {
        var homeName = itemView.findViewById<TextView>(R.id.homeName)
        var homeCity = itemView.findViewById<TextView>(R.id.homeCity)

/*        var homeImageUrl = itemView.findViewById<TextView>(R.id.homeImageUrl)
        var homeProfileImage = itemView.findViewById<TextView>(R.id.homeProfileImage)
        var homeDate = itemView.findViewById<TextView>(R.id.homeDate)
        */
    }
}
