package com.example.gaiety

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class NumAdapter() : RecyclerView.Adapter<NumAdapter.NumHolder>() {

    private var numbers: ArrayList<String> = ArrayList()

    fun init() {
        for (i in 1..100)
            this.numbers.add("$i")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumHolder {
        val itemHolder = LayoutInflater.from(parent?.context).inflate(R.layout.recyclerview_item, parent, false)
        return NumHolder(itemHolder)
    }
    override fun onBindViewHolder(holder: NumHolder, position: Int) {
        holder.number.text = numbers.get(position)
        if (position % 2 == 0) {
            holder.number.setTextColor(ContextCompat.getColor(holder.number.context, R.color.design_default_color_primary_dark))
        } else {
            holder.number.setTextColor(ContextCompat.getColor(holder.number.context, R.color.colorAccent))
        }
    }


    override fun getItemCount(): Int {
        return numbers.size
    }

    class NumHolder(view: View) : RecyclerView.ViewHolder(view) {
        var number = itemView.findViewById<TextView>(R.id.itemRecyclerView)

    }
}
