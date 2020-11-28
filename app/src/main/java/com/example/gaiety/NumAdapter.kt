package com.example.gaiety

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.gaiety.fragments.homeFragment

class NumAdapter(val homeFeed: homeFragment.HomeFeed) : RecyclerView.Adapter<NumAdapter.NumHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumHolder {
        val itemHolder = LayoutInflater.from(parent?.context).inflate(R.layout.recyclerview_item, parent, false)
        return NumHolder(itemHolder)
    }
    override fun onBindViewHolder(holder: NumHolder, position: Int) {
        holder.number.text =homeFeed.values.get(position).name

        holder.number.setTextColor(ContextCompat.getColor(holder.number.context, R.color.design_default_color_primary_dark))

    }


    override fun getItemCount(): Int {
        return homeFeed.values.count()
    }

    class NumHolder(view: View) : RecyclerView.ViewHolder(view) {
        var number = itemView.findViewById<TextView>(R.id.itemRecyclerView)

    }
}
