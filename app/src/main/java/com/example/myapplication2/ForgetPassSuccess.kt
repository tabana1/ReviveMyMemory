package com.example.myapplication2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.airbnb.lottie.LottieAnimationView
import com.example.myapplication2.UI.Login

class ForgetPassSuccess : AppCompatActivity() {
    private lateinit var repasslottie: LottieAnimationView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_pass_success)

        repasslottie=findViewById(R.id.repasslottiesucess)

        repasslottie.visibility = View.VISIBLE

        Handler().postDelayed({
            startActivity(Intent(this, Login::class.java))
        },3500)

    }
}