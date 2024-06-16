package com.diplom.onlineshop.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.diplom.onlineshop.Helper.ChangeNumberItemsListener
import com.diplom.onlineshop.Helper.ManagmentCart
import com.diplom.onlineshop.Model.ItemsModel
import com.diplom.onlineshop.databinding.ViewholderMyzakazBinding

class MyZakazAdapter(
    private val listItemSelected: ArrayList<ItemsModel>,
    context: Context,
    var changeNumberItemsListener: ChangeNumberItemsListener? = null
) : RecyclerView.Adapter<MyZakazAdapter.ViewHolder>() {
    class ViewHolder(val binding: ViewholderMyzakazBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    private val managmentCart = ManagmentCart(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyZakazAdapter.ViewHolder {
        val binding =
            ViewholderMyzakazBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyZakazAdapter.ViewHolder, position: Int) {
        val item = listItemSelected[position]
        holder.binding.titleTxt.text = item.title
        holder.binding.feeEachItem.text = "₽ ${item.price}"
        holder.binding.totalEchItem.text = "₽ ${Math.round(item.price * item.numberInCart)}"
        holder.binding.numberItemTxt.text = item.numberInCart.toString()

        Glide.with(holder.itemView.context)
            .load(item.picUrl[0])
            .apply(RequestOptions().transform(CenterCrop()))
            .into(holder.binding.pic)

//        holder.binding.plusBtn.setOnClickListener {
//
//            managmentCart.plusItem(listItemSelected, position, object : ChangeNumberItemsListener {
//                override fun onChanged() {
//                    notifyDataSetChanged()
//                    changeNumberItemsListener?.onChanged()
//                }
//
//            })
//        }
//
//        holder.binding.minusBtn.setOnClickListener {
//
//            managmentCart.minusItem(listItemSelected, position, object : ChangeNumberItemsListener {
//                override fun onChanged() {
//                    notifyDataSetChanged()
//                    changeNumberItemsListener?.onChanged()
//                }
//
//            })
//        }


    }

    override fun getItemCount(): Int = listItemSelected.size
}