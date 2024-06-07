package com.diplom.onlineshop.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.diplom.onlineshop.Helper.ManagmentCart
import com.diplom.onlineshop.Model.ItemsModel
import com.diplom.onlineshop.R
import com.diplom.onlineshop.activity.DetailActivity
import com.diplom.onlineshop.databinding.ViewholderRecomendedBinding

class ItemListAdapter(val items: MutableList<ItemsModel>, context: Context): RecyclerView.Adapter<ItemListAdapter.ViewHolder>() {

    private var context: Context? = null

    class ViewHolder(val binding: ViewholderRecomendedBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
    private val managmentCart = ManagmentCart(context)
    val listFav = managmentCart.getListFavorite()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListAdapter.ViewHolder {
        context=parent.context
        val binding=ViewholderRecomendedBinding.inflate(LayoutInflater.from(context), parent, false)
        return ItemListAdapter.ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemListAdapter.ViewHolder, position: Int) {

        val listFav = managmentCart.getListFavorite()

        holder.binding.titleTxt.text=items[position].title
        holder.binding.priceTxt.text="₽ "+items[position].price.toString()
        holder.binding.ratingTxt.text=items[position].rating.toString()

        val existFavList = listFav!!.any { it.title == items[position].title }

        if (existFavList) {
            val index = listFav.indexOfFirst { it.title == items[position].title }
            if (listFav[index].BestItem == 1) {
                items[position].BestItem = 1
            }
        }

        if (items[position].BestItem == 1) {
            holder.binding.bestBtn.setColorFilter(ContextCompat.getColor(context!!, R.color.red))
        } else {
            holder.binding.bestBtn.setColorFilter(ContextCompat.getColor(context!!, R.color.grey))
        }

        val requestOptions= RequestOptions().transform(CenterCrop())
        Glide.with(holder.itemView.context)
            .load(items[position].picUrl[0])
            .apply(requestOptions)
            .into(holder.binding.pic)

        holder.itemView.setOnClickListener{
            val intent= Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("object", items[position])
            holder.itemView.context.startActivity(intent)
        }
        holder.binding.bestBtn.setOnClickListener {
            if (items[position].BestItem == 0) {
                items[position].BestItem = 1
                managmentCart.insertFavoriteItem(items[position])
                holder.binding.bestBtn.setColorFilter(ContextCompat.getColor(context!!, R.color.red))
            } else {
                items[position].BestItem = 0
                managmentCart.insertFavoriteItem(items[position])
                holder.binding.bestBtn.setColorFilter(ContextCompat.getColor(context!!, R.color.grey))
            }
        }
    }

    override fun getItemCount(): Int = items.size
}