package com.example.calc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.calc.api.AuthResponse
import com.example.calc.api.RetrofitClient
import com.example.calc.api.UserReg
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var name: TextInputEditText
    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var confirmPassword: TextInputEditText
    private lateinit var registerBtn: Button
    private lateinit var loginTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)


        name = findViewById(R.id.txtBoxName)
        email = findViewById(R.id.txtBoxEmail)
        password = findViewById(R.id.txtBoxPass)
        confirmPassword = findViewById(R.id.txtBoxConfirm)
        registerBtn = findViewById(R.id.btnRegister)
        loginTextView = findViewById(R.id.txtLogIn)


        registerBtn.setOnClickListener {
            val name = name.text.toString().trim()
            val email = email.text.toString().trim()
            val password = password.text.toString().trim()
            val confirmPassword = confirmPassword.text.toString().trim()

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            registerUser(name, email, password)
        }

        loginTextView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun registerUser(name: String, email: String, password: String) {
        val user = UserReg(name, email, password)


        RetrofitClient.instance.register(user).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful) {

                    Toast.makeText(this@RegisterActivity, "Registered", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {

                    Toast.makeText(this@RegisterActivity, "Registration failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
