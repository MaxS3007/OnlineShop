package com.diplom.onlineshop.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.diplom.onlineshop.Adapter.CartAdapter
import com.diplom.onlineshop.Helper.ChangeNumberItemsListener
import com.diplom.onlineshop.Helper.ManagmentCart
import com.diplom.onlineshop.R
import com.diplom.onlineshop.databinding.ActivityCartBinding

class CartActivity : BaseActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var managmentCart: ManagmentCart
    private var tax: Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart = ManagmentCart(this)

        setVarible()
        initCartList()
        calculateCart()

    }

    override fun onResume() {
        super.onResume()
        setVarible()
        initCartList()
        calculateCart()
    }

    private fun initCartList() {
        binding.viewCart.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.viewCart.adapter =
            CartAdapter(managmentCart.getListCart(), this, object : ChangeNumberItemsListener {
                override fun onChanged() {
                    calculateCart()
                }
            })
        with(binding) {
            emptyCart.visibility =
                if (managmentCart.getListCart().isEmpty()) View.VISIBLE else View.GONE
            scrollView2.visibility =
                if (managmentCart.getListCart().isEmpty()) View.GONE else View.VISIBLE

        }
    }

    private fun calculateCart() {
        val percentTax = 0.0
        var delivery = 100.0
        tax = Math.round((managmentCart.getTotalFee() * percentTax) * 100) / 100.0
        val itemTotal = Math.round(managmentCart.getTotalFee() * 100) / 100
        if (itemTotal.toInt() == 0) {
           delivery = 0.0
        }
        val total = Math.round((managmentCart.getTotalFee() + tax + delivery) * 100) / 100

        with(binding) {
            totalFeeTxt.text = "₽ $itemTotal"
            taxTxt.text = "₽ $tax"
            deliveryTxt.text = "₽ $delivery"
            totalTxt.text = "₽ $total"
        }
    }

    private fun setVarible() {
        binding.backBtn.setOnClickListener { finish() }
        binding.zakazBtn.setOnClickListener {
            startActivity((Intent(this, ZakazActivity::class.java)))
        }
    }
}