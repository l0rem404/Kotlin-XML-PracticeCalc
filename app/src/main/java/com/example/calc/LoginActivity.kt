package com.example.calc

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.calc.api.AuthResponse
import com.example.calc.api.AuthUtils
import com.example.calc.api.RetrofitClient
import com.example.calc.api.UserLogIn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginBtn: Button
    private lateinit var signUpBtn: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        email = findViewById(R.id.txtBoxEmail)
        password = findViewById(R.id.txtBoxPass)
        loginBtn = findViewById(R.id.btnLogin)

        signUpBtn = findViewById(R.id.txtSignup)

        sharedPreferences = getSharedPreferences("AuthPrefs", MODE_PRIVATE)

        loginBtn.setOnClickListener {
            val user = UserLogIn(email.text.toString(), password.text.toString())
            loginUser(user)
        }

        signUpBtn.setOnClickListener{
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun loginUser(user: UserLogIn) {
        RetrofitClient.instance.login(user).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@LoginActivity, "Login Successful!", Toast.LENGTH_SHORT).show()

                    val authResponse = response.body()
                    Log.d("LoginResponse", "Response body: $authResponse")

                    val token = authResponse?.token

                    if (token != null) {
                        AuthUtils.storeToken(this@LoginActivity, token)
                        Log.d("AuthUtils", "Stored token: $token")
                    } else {
                        Log.e("AuthUtils", "Token is null")
                    }

                    val editor = sharedPreferences.edit()
                    editor.putBoolean("isLoggedIn", true)
                    editor.apply()

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    Toast.makeText(this@LoginActivity, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                    Log.e("LoginError", "Response code: ${response.code()}, Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                Log.e("LoginFailure", "Network error: ${t.message}")
            }
        })
    }


}
