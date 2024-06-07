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

class LoginActivity : BaseActivity() {
    private lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setVariable()


    }

    private fun setVariable() {
        binding.signupBtn.setOnClickListener {
            val email = binding.emailTxt.text.toString()
            val passwd = binding.passTxt.text.toString()

            if (!passwd.isEmpty() && !email.isEmpty()) {
                mAuth.signInWithEmailAndPassword(email, passwd).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.w(TAG, "createUserWithEmail:success")
                        println("createUserWithEmail:success")
                        val user = mAuth.currentUser
                        startActivity((Intent(this@LoginActivity, MainActivity::class.java)))
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        println("Authentication failed.")
                        Toast.makeText(this, "Сбой авторизации. Проверьте логин и пароль и повторите.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Заполните все данные для входа", Toast.LENGTH_SHORT).show()

            }

        }

        binding.regBtn.setOnClickListener {
            startActivity((Intent(this@LoginActivity, SignUpActivity::class.java)))
        }
    }
}