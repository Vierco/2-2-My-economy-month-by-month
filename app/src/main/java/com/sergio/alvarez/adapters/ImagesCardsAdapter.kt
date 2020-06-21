package com.sergio.alvarez.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sergio.alvarez.mieconomia.R
import com.sergio.alvarez.mieconomia.databinding.ImageCardBinding
import com.sergio.alvarez.mieconomia.inflate

class ImagesCardsAdapter(private val images: List<Int>, private val listener: (Int) -> Unit) :
    RecyclerView.Adapter<ImagesCardsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.image_card)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = images[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { listener(position) }


    }

    override fun getItemCount(): Int {
        return images.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val vb = ImageCardBinding.bind(view)

        fun bind(imageItem: Int) {

            with(vb) {
                image.setImageResource(imageItem)
            }

        }
    }
}