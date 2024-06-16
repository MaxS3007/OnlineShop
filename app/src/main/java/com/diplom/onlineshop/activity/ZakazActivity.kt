package com.diplom.onlineshop.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.diplom.onlineshop.Helper.ChangeNumberItemsListener
import com.diplom.onlineshop.Helper.ManagmentCart
import com.diplom.onlineshop.R
import com.diplom.onlineshop.databinding.ActivityZakazBinding

class ZakazActivity : BaseActivity() {
    private lateinit var binding: ActivityZakazBinding
    private lateinit var managmentCart: ManagmentCart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityZakazBinding.inflate(layoutInflater)

        setContentView(binding.root)

        managmentCart = ManagmentCart(this)

        getProfile()


    }

    private fun getProfile() {

        binding.addressTxt.setText(managmentCart.getStringProfile("Address"))
        binding.phoneTxt.setText(managmentCart.getStringProfile("Phone"))

        var payMetod: String = managmentCart.getStringProfile("payMetod")
        if (payMetod != "") {
            when (payMetod) {
                "1" -> {
                    binding.onlineCardPay.setChecked(true)

                }
                "2" -> {
                    binding.cardPay.setChecked(true)

                }
                "3" -> {
                    binding.nalPay.setChecked(true)
                }
            }

        } else {
            binding.onlineCardPay.setChecked(true)
            payMetod = "1"
        }

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            // Handle radio group change
            when (checkedId) {
                R.id.onlineCardPay -> {
                    payMetod = "1"
                }
                R.id.cardPay -> {
                    payMetod = "2"
                }
                R.id.nalPay -> {
                    payMetod = "3"
                }

            }
        }

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.saveBtn.setOnClickListener {

            with(binding) {
                addressTxt.visibility = View.GONE
                phoneTxt.visibility = View.GONE
                payMetodTxt.visibility = View.GONE
                radioGroup.visibility = View.GONE
                saveBtn.visibility = View.GONE
                clearBtn.visibility = View.GONE
                pic.visibility = View.VISIBLE
                zakazTxt.visibility = View.VISIBLE
                kurerTxt.visibility = View.VISIBLE
                timedelTxt.visibility = View.VISIBLE
            }
            var changeNumberItemsListener: ChangeNumberItemsListener? = null
            managmentCart.removeItems(managmentCart.getListCart(),object : ChangeNumberItemsListener {
                override fun onChanged() {
                    changeNumberItemsListener?.onChanged()
                }
            })
//            System.out.println(managmentCart.getListCart())
//            System.out.println(managmentCart.getZakazCart())


        }
        binding.clearBtn.setOnClickListener {
            finish()
        }
    }
}