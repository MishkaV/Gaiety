package com.example.gaiety

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gaiety.fragments.*
import kotlinx.android.synthetic.main.recyclerview_item_myorganizations.view.*

class NumAdapterMyOrganizations(
    val homeFeed: MyOrganizationsFragment.HomeFeed
) : RecyclerView.Adapter<NumAdapterMyOrganizations.NumHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumHolder {
        val itemHolder = LayoutInflater
            .from(parent?.context)
            .inflate(R.layout.recyclerview_item_myorganizations, parent, false)
        return NumHolder(itemHolder)
    }
    override fun onBindViewHolder(holder: NumHolder, position: Int) {
        holder.itemView.myorganizationsName.text = homeFeed.values.get(position).name
    }

    override fun getItemCount(): Int {
        return homeFeed.values.count()
    }

    class NumHolder(view: View) : RecyclerView.ViewHolder(view) {
        // NumHolder
    }
}
