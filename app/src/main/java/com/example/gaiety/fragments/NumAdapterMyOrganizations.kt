package com.example.gaiety

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.gaiety.fragments.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyclerview_item.view.*
import kotlinx.android.synthetic.main.recyclerview_item_favorite.view.*
import kotlinx.android.synthetic.main.recyclerview_item_myorganizations.view.*

class NumAdapterMyOrganizations(val homeFeed: myOrganizationsFragment.HomeFeed) : RecyclerView.Adapter<NumAdapterMyOrganizations.NumHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumHolder {
        val itemHolder = LayoutInflater.from(parent?.context).inflate(R.layout.recyclerview_item_myorganizations, parent, false)
        return NumHolder(itemHolder)
    }
    override fun onBindViewHolder(holder: NumHolder, position: Int) {
        holder.itemView.myorganizationsName.text = homeFeed.values.get(position).name
    }

    override fun getItemCount(): Int {
        return homeFeed.values.count()
    }

    class NumHolder(view: View) : RecyclerView.ViewHolder(view) {
        /*init {
            view.setOnClickListener {
                //val intent = Intent(view.context, Test::class.java)
                //val act = itemView.findViewById<View>(R.id.homeImageUrl)
                //val options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, act, "kek")
                //view.context.startActivity(intent)
                val itemMore = ItemRecyclerMore()
            }
        }*/
    }
}
