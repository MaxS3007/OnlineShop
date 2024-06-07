package com.diplom.onlineshop.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.diplom.onlineshop.Adapter.ItemListAdapter
import com.diplom.onlineshop.Model.BrandModel
import com.diplom.onlineshop.Model.ItemsModel
import com.diplom.onlineshop.Model.SliderModel
import com.diplom.onlineshop.databinding.ActivityItemListBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ItemListActivity : BaseActivity() {
    private lateinit var binding: ActivityItemListBinding
    private val firebaseDatabase= FirebaseDatabase.getInstance()
    private var categoryId = -1
    private var categoryName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityItemListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getIntentExtra()
        loadCategory()

    }

    private fun getIntentExtra() {
        categoryId = intent.extras!!.getInt("categoryId", -1)
        categoryName = intent.extras!!.getString("categoryName", "")

        binding.titleTxt.text = "${categoryName}"
        binding.backBtn.setOnClickListener { finish() }

    }

    private fun loadCategory(){

        categoryId = intent.extras!!.getInt("categoryId", -1)
        val Ref=firebaseDatabase.getReference("Items")

        Ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists= mutableListOf<ItemsModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(ItemsModel::class.java)
                    if(list!=null){
//                        lists.add(list)
//                        System.out.println(list.CategoryId)
//                        System.out.println(categoryId)
//                        System.out.println(list.CategoryId == categoryId)
                        if (list.CategoryId == categoryId) {
                            lists.add(list)
                        }
                    }
                }

                if (lists.size > 0) {
                    binding.itemListView.layoutManager=GridLayoutManager(this@ItemListActivity, 2)
                    binding.itemListView.adapter = ItemListAdapter(lists, this@ItemListActivity)
                }
                binding.progressBarItemList.visibility = View.GONE

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}