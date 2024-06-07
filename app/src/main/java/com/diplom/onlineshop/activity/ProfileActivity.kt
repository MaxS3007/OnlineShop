package com.diplom.onlineshop.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.diplom.onlineshop.Helper.ManagmentCart
import com.diplom.onlineshop.R
import com.diplom.onlineshop.databinding.ActivityMainBinding
import com.diplom.onlineshop.databinding.ActivityProfileBinding

class ProfileActivity : BaseActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var managmentCart: ManagmentCart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart = ManagmentCart(this)

        getProfile()


    }

    private fun getProfile() {

        binding.nameTxt.setText(managmentCart.getStringProfile("Name"))
        binding.famTxt.setText(managmentCart.getStringProfile("Familia"))
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
//        binding.cardPay.
//

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.saveBtn.setOnClickListener {
            managmentCart.putStringProfile("Name", binding.nameTxt.text.toString())
            managmentCart.putStringProfile("Familia", binding.famTxt.text.toString())
            managmentCart.putStringProfile("Address", binding.addressTxt.text.toString())
            managmentCart.putStringProfile("Phone", binding.phoneTxt.text.toString())
            managmentCart.putStringProfile("payMetod", payMetod)
            Toast.makeText(this, "Ваши данные сохранены", Toast.LENGTH_SHORT).show()
            finish()
        }
        binding.clearBtn.setOnClickListener {
            managmentCart.putStringProfile("Name", binding.nameTxt.text.toString())
            managmentCart.putStringProfile("Familia", binding.famTxt.text.toString())
            managmentCart.putStringProfile("Address", binding.addressTxt.text.toString())
            managmentCart.putStringProfile("Phone", binding.phoneTxt.text.toString())
            managmentCart.putStringProfile("payMetod", payMetod)
            Toast.makeText(this, "Ваши данные сохранены", Toast.LENGTH_SHORT).show()

        }
    }
}