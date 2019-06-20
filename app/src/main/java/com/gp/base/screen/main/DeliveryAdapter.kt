package com.gp.base.screen.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.gp.base.R
import com.gp.base.network.model.Delivery
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_delivery.view.*

class DeliveryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM = 0
    private val PROGRESS = 1

    private var deliveries = mutableListOf<Delivery>()

    fun addItems(newDeliveries: List<Delivery>) {
        if (deliveries.isNotEmpty() && deliveries.last().id == -1) {
            val lastIndex = deliveries.lastIndex
            deliveries.removeAt(lastIndex)
            notifyItemRemoved(lastIndex)
        }

        if (newDeliveries.isEmpty()) return

        val oldSize = deliveries.size
        deliveries.addAll(newDeliveries)
        deliveries.add(Delivery())

        val newSize = deliveries.size
        notifyItemRangeInserted(oldSize, newSize)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            ITEM -> ItemViewHolder(parent.inflate(R.layout.item_delivery, false))
            else -> ProgressViewHolder(parent.inflate(R.layout.item_progress, false))
        }
    }

    override fun getItemViewType(position: Int) = if (deliveries[position].id >= 0) ITEM else PROGRESS

    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    override fun getItemCount() = deliveries.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == ITEM) {
            holder as ItemViewHolder
            holder.bind(deliveries[position])
        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var view: View = itemView

        fun bind(delivery: Delivery) {
            view.tvDescription.text = "${delivery.id} ${delivery.description}"
            Picasso.get().load(delivery.imageUrl).resize(100, 100).centerCrop().into(view.imgDelivery)
        }
    }

    class ProgressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}