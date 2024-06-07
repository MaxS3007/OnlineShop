package com.diplom.onlineshop.activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.diplom.onlineshop.Adapter.ItemListAdapter
import com.diplom.onlineshop.Helper.ManagmentCart
import com.diplom.onlineshop.Model.ItemsModel
import com.diplom.onlineshop.R
import com.diplom.onlineshop.databinding.ActivityFavItemBinding
import com.diplom.onlineshop.databinding.ActivityItemListBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FavItemActivity : BaseActivity() {
    private lateinit var binding: ActivityFavItemBinding
    private val firebaseDatabase= FirebaseDatabase.getInstance()
    private lateinit var managmentCart: ManagmentCart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart = ManagmentCart(this)

        getIntentExtra()
        loadFavItem()

    }

    private fun loadFavItem() {
        val Ref=firebaseDatabase.getReference("Items")
        val listFav = managmentCart.getListFavorite()
        System.out.println(listFav)
        Ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists= mutableListOf<ItemsModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(ItemsModel::class.java)

                    if(list!=null){
                        val existFavList = listFav!!.any { it.title == list.title }

                        if (existFavList) {
                            val index = listFav.indexOfFirst { it.title == list.title }
                            if (listFav[index].BestItem == 1) {
                                list.BestItem = 1
                            }
                        }
//                        lists.add(list)
                        System.out.println(list.BestItem)
//                        System.out.println(categoryId)
                        System.out.println(list.BestItem == 1)
                        if (list.BestItem == 1) {
                            lists.add(list)
                        }
                    }
                }

                if (lists.size > 0) {
                    binding.itemListView.layoutManager= GridLayoutManager(this@FavItemActivity, 2)
                    binding.itemListView.adapter = ItemListAdapter(lists, this@FavItemActivity)
                }
                binding.progressBarItemList.visibility = View.GONE

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun getIntentExtra() {
        binding.backBtn.setOnClickListener { finish() }
    }
}