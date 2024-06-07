package com.diplom.onlineshop.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.diplom.onlineshop.R
import com.google.firebase.auth.FirebaseAuth

open class BaseActivity : AppCompatActivity() {
    val mAuth = FirebaseAuth.getInstance()
    val TAG = "onlineShop"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }
}