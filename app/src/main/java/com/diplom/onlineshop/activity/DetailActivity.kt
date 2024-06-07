package com.diplom.onlineshop.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diplom.onlineshop.Adapter.ColordAdapter
import com.diplom.onlineshop.Adapter.SizeAdapter
import com.diplom.onlineshop.Adapter.SliderAdapter
import com.diplom.onlineshop.Helper.ManagmentCart
import com.diplom.onlineshop.Model.ItemsModel
import com.diplom.onlineshop.Model.SliderModel
import com.diplom.onlineshop.R
import com.diplom.onlineshop.databinding.ActivityDetailBinding


class DetailActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var item: ItemsModel
    private var numberOrder = 1
    private lateinit var managmentCart: ManagmentCart


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        managmentCart = ManagmentCart(this)


        getBundle()
        banners()
        initLists()

    }

    private fun initLists() {
        val sizeList = ArrayList<String>()
        for (size in item.size) {
            sizeList.add(size.toString())
        }

        binding.sizeList.adapter = SizeAdapter(sizeList)
        binding.sizeList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val colorList = ArrayList<String>()
        for (imageUrl in item.picUrl) {
            colorList.add(imageUrl)
        }
        binding.colorList.adapter = ColordAdapter(colorList)
        binding.colorList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun banners() {
        val sliderItems = ArrayList<SliderModel>()
        for (imageUrl in item.picUrl) {
            sliderItems.add(SliderModel(imageUrl))
        }
        binding.slider.adapter = SliderAdapter(sliderItems, binding.slider)
        binding.slider.clipToPadding = true
        binding.slider.clipChildren = true
        binding.slider.offscreenPageLimit = 1


        if (sliderItems.size > 1) {
            binding.dotIndicator.visibility = View.VISIBLE
            binding.dotIndicator.attachTo(binding.slider)
        }

    }

    private fun getBundle() {
        item = intent.getParcelableExtra("object")!!
        val listFav = managmentCart.getListFavorite()
        //System.out.println("==============>>>>  Best_Item ${item.BestItem}  CategoryId ${item.CategoryId}")

        binding.titleTxt.text = item.title
        binding.desription.text = item.description
        binding.priceTxt.text = "₽ " + item.price.toString()
        binding.ratingTxt.text = "${item.rating} Рейтинг"
        binding.addToCartBtn.setOnClickListener {
            item.numberInCart = numberOrder
            managmentCart.insertFood(item)
        }
        val existFavList = listFav!!.any { it.title == item.title }
        if (existFavList) {
            val index = listFav.indexOfFirst { it.title == item.title }
            if (listFav[index].BestItem == 1) {
                item.BestItem = 1
            }
        }

        if (item.BestItem == 1) {
            binding.favBtn.setColorFilter(ContextCompat.getColor(this, R.color.red))
        } else {
            binding.favBtn.setColorFilter(ContextCompat.getColor(this, R.color.grey))
            //binding.favBtn.setBackgroundResource(R.color.white)
        }

        binding.backBtn.setOnClickListener {

            finish()
        }
        binding.cartBtn.setOnClickListener {
            startActivity(Intent(this@DetailActivity, CartActivity::class.java ))

        }
        binding.favBtn.setOnClickListener {

            if (item.BestItem == 0) {
                item.BestItem = 1
                managmentCart.insertFavoriteItem(item)
                binding.favBtn.setColorFilter(ContextCompat.getColor(this, R.color.red))
            } else {
                item.BestItem = 0
                managmentCart.insertFavoriteItem(item)
                binding.favBtn.setColorFilter(ContextCompat.getColor(this, R.color.grey))
            }
            //startActivity(Intent(this@DetailActivity, CartActivity::class.java ))

        }


    }
}