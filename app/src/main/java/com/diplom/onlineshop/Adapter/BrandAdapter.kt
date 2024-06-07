package com.diplom.onlineshop.Adapter

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.diplom.onlineshop.Model.BrandModel
import com.diplom.onlineshop.R
import com.diplom.onlineshop.activity.DetailActivity
import com.diplom.onlineshop.activity.ItemListActivity
import com.diplom.onlineshop.databinding.ViewholderBrandBinding

class BrandAdapter(val items: MutableList<BrandModel>) :
    RecyclerView.Adapter<BrandAdapter.Viewholder>() {

        private var selectedPosition = -1
    private var lastSelectPosition = -1
    private lateinit var context: Context

    class Viewholder(val binding: ViewholderBrandBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandAdapter.Viewholder {
        context=parent.context
        val binding=ViewholderBrandBinding.inflate(LayoutInflater.from(context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: BrandAdapter.Viewholder, position: Int) {
        val item = items[position]
        holder.binding.title1.text=item.title
        Glide.with(holder.itemView.context)
            .load(item.picUrl)
            .into(holder.binding.pic)

        holder.binding.root.setOnClickListener {
            lastSelectPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(lastSelectPosition)
            notifyItemChanged(selectedPosition)

            holder.binding.title1.setTextColor(context.resources.getColor(R.color.black))
            if (selectedPosition == position) {
                holder.binding.pic.setBackgroundColor(0)
                holder.binding.mainLayout.setBackgroundResource(R.drawable.grey_bg)
                ImageViewCompat.setImageTintList(
                    holder.binding.pic,
                    ColorStateList.valueOf(context.getColor(R.color.black))
                )
                holder.binding.title1.visibility = View.VISIBLE
            } else {
                holder.binding.pic.setBackgroundResource(R.drawable.grey_bg)
                holder.binding.mainLayout.setBackgroundResource(0)
                ImageViewCompat.setImageTintList(
                    holder.binding.pic,
                    ColorStateList.valueOf(context.getColor(R.color.black))
                )
                holder.binding.title1.visibility = View.VISIBLE
            }
            val intent = Intent(holder.itemView.context, ItemListActivity::class.java)
            intent.putExtra("categoryId", items[position].id)
            intent.putExtra("categoryName", items[position].title)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int  = items.size
}