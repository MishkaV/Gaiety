package com.example.gaiety

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.gaiety.fragments.ItemRecyclerMore
import com.example.gaiety.fragments.homeFragment
import com.example.gaiety.fragments.myTicketsFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class NumAdapter(val homeFeed: homeFragment.HomeFeed) : RecyclerView.Adapter<NumAdapter.NumHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumHolder {
        val itemHolder = LayoutInflater.from(parent?.context).inflate(R.layout.recyclerview_item, parent, false)
        return NumHolder(itemHolder)
    }
    override fun onBindViewHolder(holder: NumHolder, position: Int) {
        holder.itemView.homeName.text = homeFeed.values.get(position).name
        holder.itemView.homeCity.text = homeFeed.values.get(position).location.city
        holder.itemView.homeDate.text = homeFeed.values.get(position).starts_at

        if (homeFeed?.values?.get(position)?.poster_image == null){
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
