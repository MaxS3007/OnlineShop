package com.diplom.onlineshop.Helper

import android.content.Context
import android.widget.Toast
import com.diplom.onlineshop.Helper.TinyDB
import com.diplom.onlineshop.Model.ItemsModel



class ManagmentCart(val context: Context) {

    private val tinyDB = TinyDB(context)

    fun insertFavoriteItem(item: ItemsModel) {
        var listFood = getListFavorite()
        val existAlready = listFood!!.any { it.title == item.title }
        val index = listFood!!.indexOfFirst { it.title == item.title }

        if (existAlready) {
            listFood!![index].BestItem = item.BestItem
        } else {
            if (listFood != null) {
                listFood.add(item)
            }
        }
        tinyDB.putListObject("FavoriteList", listFood)
        if (item.BestItem == 1){
            Toast.makeText(context, "Товар добавлен в избранное", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Товар удален из избранного", Toast.LENGTH_SHORT).show()
        }
    }

    fun getListFavorite(): ArrayList<ItemsModel>? {
        return tinyDB.getListObject("FavoriteList") ?: arrayListOf()
    }

    fun putStringProfile(key: String, value:String) {
        tinyDB.putString(key, value)
    }

    fun getStringProfile(key: String): String{
        return tinyDB.getString(key)
    }

    fun insertFood(item: ItemsModel) {
        var listFood = getListCart()
        val existAlready = listFood.any { it.title == item.title }
        val index = listFood.indexOfFirst { it.title == item.title }

        if (existAlready) {
            listFood[index].numberInCart = item.numberInCart
        } else {
            listFood.add(item)
        }
        tinyDB.putListObject("CartList", listFood)
        Toast.makeText(context, "Товар добавлен в корзину", Toast.LENGTH_SHORT).show()
    }

    fun getListCart(): ArrayList<ItemsModel> {
        return tinyDB.getListObject("CartList") ?: arrayListOf()
    }

    fun minusItem(listFood: ArrayList<ItemsModel>, position: Int, listener: ChangeNumberItemsListener) {
        if (listFood[position].numberInCart == 1) {
            listFood.removeAt(position)
        } else {
            listFood[position].numberInCart--
        }
        tinyDB.putListObject("CartList", listFood)
        listener.onChanged()
    }

    fun removeItems(listFood: ArrayList<ItemsModel>, listener: ChangeNumberItemsListener) {
        listFood.clear()
        tinyDB.putListObject("CartList", listFood)
        listener.onChanged()
    }

    fun plusItem(listFood: ArrayList<ItemsModel>, position: Int, listener: ChangeNumberItemsListener) {
        listFood[position].numberInCart++
        tinyDB.putListObject("CartList", listFood)
        listener.onChanged()
    }

    fun getTotalFee(): Double {
        val listFood = getListCart()
        var fee = 0.0
        for (item in listFood) {
            fee += item.price * item.numberInCart
        }
        return fee
    }
}