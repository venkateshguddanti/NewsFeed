package com.venkat.newsfeed.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.venkat.newsfeed.R
import com.venkat.newsfeed.data.model.Rows
import kotlinx.android.synthetic.main.facts_item_layout.view.*

class FactsAdapter(private val rows : ArrayList<Rows>): RecyclerView.Adapter<FactsAdapter.DataViewHolder>() {
    class DataViewHolder(private val itemVew : View) :RecyclerView.ViewHolder(itemVew) {

        fun bind(row : Rows)
        {
            itemVew.apply {
                factTitle.text = row.title
                factDescription.text = row.description
                Glide.with(imageViewAvatar.context)
                    .load(row.imageHref)
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .into(imageViewAvatar)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {

        return DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.facts_item_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return rows.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(rows[position])
    }

    fun addRows(rows : List<Rows>)
    {
        this.rows.apply {
            clear()
            addAll(rows)
        }
    }
}