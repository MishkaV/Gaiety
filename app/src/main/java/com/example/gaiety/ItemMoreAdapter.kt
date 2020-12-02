package com.example.gaiety

import android.content.Intent
import android.text.Html
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gaiety.fragments.homeFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_item_more.view.*
import kotlinx.android.synthetic.main.recyclerview_item.view.*
import kotlinx.android.synthetic.main.recyclerview_item_more.view.*

class ItemMoreAdapter(val event: ItemMore.Event) : RecyclerView.Adapter<ItemMoreAdapter.ItemHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMoreAdapter.ItemHolder {
        val itemHolder = LayoutInflater.from(parent?.context).inflate(R.layout.recyclerview_item_more, parent, false)
        return ItemMoreAdapter.ItemHolder(itemHolder)
    }
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        when(position) {
            0 -> {
                holder.itemView.textOfHead.text = Html.fromHtml(event.description_html)
                holder.itemView.textOfHead.movementMethod = ScrollingMovementMethod ()
                holder.itemView.head.text = "Описание"
            }
            1 -> {
                holder.itemView.textOfHead.text = Html.fromHtml("Начало: "
                        + event.starts_at + "\nКонец: " + event.ends_at)
                holder.itemView.textOfHead.movementMethod = ScrollingMovementMethod ()
                holder.itemView.head.text = "Когда"

            }
            2 -> {
                holder.itemView.textOfHead.text = "Страна: " +
                        event.location.country + "\nГород: " +
                        event.location.city +"\nАдрес: " + event.location.address
                holder.itemView.textOfHead.movementMethod = ScrollingMovementMethod ()
                holder.itemView.head.text = "Где"
            }

        }
    }

    override fun getItemCount(): Int {
        return 3
    }
    class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

}