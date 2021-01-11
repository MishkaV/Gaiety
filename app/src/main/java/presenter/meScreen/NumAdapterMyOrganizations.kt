package com.example.gaiety

import model.ClientData.Client
import model.ClientData.organizations.Organizations
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recyclerview_item_myorganizations.view.*

class NumAdapterMyOrganizations(
    val homeFeed: Client
) : RecyclerView.Adapter<NumAdapterMyOrganizations.NumHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumHolder {
        val itemHolder = LayoutInflater
            .from(parent?.context)
            .inflate(R.layout.recyclerview_item_myorganizations, parent, false)
        return NumHolder(itemHolder, homeFeed)
    }
    override fun onBindViewHolder(holder: NumHolder, position: Int) {
        holder.itemView.myorganizationsName.text = homeFeed.organizations.get(position).name
    }

    override fun getItemCount(): Int {
        return homeFeed.organizations.count()
    }

    fun addItem(item: Organizations){
        homeFeed.organizations  = homeFeed.organizations + item
    }

    class NumHolder(view: View, var homeFeed: Client) : RecyclerView.ViewHolder(view) {
        //NumHolder
    }
}
