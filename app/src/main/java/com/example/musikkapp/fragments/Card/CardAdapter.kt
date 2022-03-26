package com.example.musikkapp.fragments.Card

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musikkapp.R

class CardAdapter (val name: Array<String>,val info: Array<String>, val imageId: Array<Int>):
    RecyclerView.Adapter<ModalViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModalViewHolder {
        return ModalViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_model,parent,false))
    }

    override fun onBindViewHolder(holder: ModalViewHolder, position: Int) {
        holder.nameTextView.text = name.get(position)
        holder.infoTextView.text = info.get(position)
        holder.imageIdView.setImageResource(imageId.get(position))
    }

    override fun getItemCount(): Int=name.size
}

class ModalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    internal var infoTextView: TextView = itemView.findViewById(R.id.card_info)
    internal var nameTextView : TextView = itemView.findViewById(R.id.card_name)
    internal var imageIdView : ImageView = itemView.findViewById(R.id.card_img)
}
