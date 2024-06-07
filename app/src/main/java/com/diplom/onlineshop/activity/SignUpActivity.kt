package com.diplom.onlineshop.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.diplom.onlineshop.R
import com.diplom.onlineshop.databinding.ActivityLoginBinding
import com.diplom.onlineshop.databinding.ActivitySignUpBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult

class SignUpActivity : BaseActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setVariable()
    }

    private fun setVariable() {
        binding.signupBtn.setOnClickListener {
            val email = binding.emailTxt.text.toString()
            val passwd = binding.passTxt.text.toString()

            if (passwd.length <6 ) {
                Toast.makeText(this, "Пароль должен быть 6 и более символов", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            mAuth.createUserWithEmailAndPassword(email, passwd).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.w(TAG, "createUserWithEmail:success")
                    println("createUserWithEmail:success")
                    val user = mAuth.currentUser
                    startActivity((Intent(this@SignUpActivity, MainActivity::class.java)))
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    println("Authentication failed.")
                    Toast.makeText(this, "Сбой при регистрации. Проверьте присоединение к интернету и повторите.", Toast.LENGTH_SHORT).show()
                }
            }


        }

        binding.loginBtn.setOnClickListener {
            startActivity((Intent(this@SignUpActivity, LoginActivity::class.java)))
        }
    }
}