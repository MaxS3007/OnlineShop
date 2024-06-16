package com.diplom.onlineshop.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.diplom.onlineshop.Adapter.BrandAdapter
import com.diplom.onlineshop.Adapter.PopularAdapter
import com.diplom.onlineshop.Model.SliderModel
import com.diplom.onlineshop.Adapter.SliderAdapter
import com.diplom.onlineshop.Helper.ManagmentCart
import com.diplom.onlineshop.ViewModel.MainViewModel
import com.diplom.onlineshop.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private val viewModel = MainViewModel()
    private lateinit var binding:ActivityMainBinding
    private lateinit var managmentCart: ManagmentCart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart = ManagmentCart(this)

        initUser()
        initBanner()
        initBrand()
        initPopular()
        initBottomMenu()

    }

    private fun initUser() {
        var name = managmentCart.getStringProfile("Name")
        if (name == "") { name = "Имя"}
        var fam = managmentCart.getStringProfile("Familia")
        if (fam == "") { fam = "Пользователя"}
        binding.userNameTxt.text = "${name} ${fam}"

        binding.logoutBtn.setOnClickListener {
            mAuth.signOut()
            startActivity(Intent(this@MainActivity, IntroActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        initUser()
        initPopular()
    }

    private fun initBottomMenu() {
        binding.cartBtn.setOnClickListener {
            startActivity((Intent(this@MainActivity, CartActivity::class.java)))
        }
        binding.favBtn.setOnClickListener {
            startActivity((Intent(this@MainActivity, FavItemActivity::class.java)))
        }
        binding.orderBtn.setOnClickListener {
            startActivity((Intent(this@MainActivity, MyZakazActivity::class.java)))
        }
        binding.userBtn.setOnClickListener {
            startActivity((Intent(this@MainActivity, ProfileActivity::class.java)))
            initUser()
        }
    }

    private fun initBanner() {
        binding.progressBarBaner.visibility= View.VISIBLE
        viewModel.banners.observe(this, Observer { items->

            banners(items)
            binding.progressBarBaner.visibility=View.GONE

        })
        viewModel.loadBanners()
    }

    private fun banners(images:List<SliderModel>){
        binding.viewPagerSlider.adapter= SliderAdapter(images, binding.viewPagerSlider)
        binding.viewPagerSlider.clipToPadding=false
        binding.viewPagerSlider.clipChildren=false
        binding.viewPagerSlider.offscreenPageLimit=3
        binding.viewPagerSlider.getChildAt(0).overScrollMode=RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer=CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
        }
        binding.viewPagerSlider.setPageTransformer(compositePageTransformer)
        if (images.size>1){
            binding.dotIndicator.visibility=View.VISIBLE
            binding.dotIndicator.attachTo(binding.viewPagerSlider)
        }
    }

    private fun initBrand(){
        binding.progressBarBrand.visibility=View.VISIBLE
        viewModel.brand.observe(this, Observer {
            binding.viewBrand.layoutManager=LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            binding.viewBrand.adapter=BrandAdapter(it)
            binding.progressBarBrand.visibility = View.GONE
        })
        viewModel.loadBrands()
    }

    private fun initPopular(){
        binding.progressBarPopular.visibility=View.VISIBLE
        viewModel.popular.observe(this, Observer {
            binding.viewPopular.layoutManager=GridLayoutManager(this@MainActivity, 2)
            binding.viewPopular.adapter=PopularAdapter(it, this)
            binding.progressBarPopular.visibility = View.GONE
        })
        viewModel.loadPopular()
    }
}