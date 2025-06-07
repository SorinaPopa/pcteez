package com.example.pcteez.ui.home.photocards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pcteez.R

class PhotocardAdapter(
    private var items: List<Photocard>,
    private var userCollection: List<Photocard> = emptyList(),
    private var userWishlist: List<Photocard> = emptyList(),
    private val onActionClick: (Photocard, ActionType) -> Unit
) : RecyclerView.Adapter<PhotocardAdapter.PhotocardViewHolder>() {

    enum class ActionType {
        COLLECTION,
        WISHLIST
    }

    inner class PhotocardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.photo_card_image)
        val title: TextView = itemView.findViewById(R.id.photocard_title)
        val addBtn: ImageButton = itemView.findViewById(R.id.button_add_collection)
        val starBtn: ImageButton = itemView.findViewById(R.id.button_add_wishlist)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotocardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_photocard, parent, false)
        return PhotocardViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotocardViewHolder, position: Int) {
        val card = items[position]
        val isCollected = userCollection.any { it.id == card.id }
        val isWishlisted = userWishlist.any { it.id == card.id }

        Glide.with(holder.itemView.context)
            .load(card.imgUrl)
            .into(holder.image)

        holder.title.text = card.pcTitle

        holder.addBtn.setOnClickListener {
            onActionClick(card, ActionType.COLLECTION)
        }

        holder.starBtn.setOnClickListener {
            onActionClick(card, ActionType.WISHLIST)
        }

        holder.addBtn.setColorFilter(
            if (isCollected) holder.itemView.context.getColor(R.color.yellow)
            else holder.itemView.context.getColor(R.color.black)
        )

        holder.starBtn.setColorFilter(
            if (isWishlisted) holder.itemView.context.getColor(R.color.yellow)
            else holder.itemView.context.getColor(R.color.black)
        )
    }

    override fun getItemCount() = items.size

    fun updateData(
        newItems: List<Photocard>,
        newCollection: List<Photocard> = emptyList(),
        newWishlist: List<Photocard> = emptyList()
    ) {
        items = newItems
        userCollection = newCollection
        userWishlist = newWishlist
        notifyDataSetChanged()
    }

}
