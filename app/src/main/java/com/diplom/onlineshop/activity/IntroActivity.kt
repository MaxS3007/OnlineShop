package com.diplom.onlineshop.activity

import android.content.Intent
import android.os.Bundle
import com.diplom.onlineshop.databinding.ActivityIntroBinding

class IntroActivity : BaseActivity() {
    private lateinit var binding:ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setVariable()

    }

    private fun setVariable() {
        binding.loginBtn.setOnClickListener {
            if (mAuth.currentUser != null) {
                startActivity(Intent(this@IntroActivity, MainActivity::class.java))
            } else {
                startActivity(Intent(this@IntroActivity, LoginActivity::class.java))
            }
        }

        binding.signupBtn.setOnClickListener {
            startActivity(Intent(this@IntroActivity, SignUpActivity::class.java))
        }
    }
}