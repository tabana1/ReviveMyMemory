package com.example.myapplication2.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import com.example.myapplication2.*
import com.example.myapplication2.ults.MyConfig
import com.example.myapplication2.ults.MyRequest
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject

class Login : AppCompatActivity() {
    private lateinit var goHome:Button
    private lateinit var noAccountTv:TextView
    private lateinit var forgetTv:TextView
    private lateinit var mEditEmail:TextInputEditText
    private lateinit var mEditPassword:TextInputEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        goHome=findViewById(R.id.goHome)
        noAccountTv=findViewById(R.id.noAccountTv)
        forgetTv=findViewById(R.id.forgetTv)
        mEditEmail=findViewById(R.id.mEditEmail)
        mEditPassword=findViewById(R.id.mEditPassword)


        goHome.setOnClickListener { loginClicked() }

        noAccountTv.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }

        forgetTv.setOnClickListener {
            startActivity(Intent(this, ForgetPassword::class.java))
        }

    }

    private fun loginClicked() {

        //if(validateData()) {
        // data we send in the request: Email and password
        val params = JSONObject()
        params.put("email", mEditEmail.text.toString())
        params.put("password", mEditPassword.text.toString())

        Log.d("mytag", "Button clicked")

        // send request
        val queue = Volley.newRequestQueue(this)
        val request = MyRequest(
            this,
            Request.Method.POST,
            "/login",
            params,
            { response ->

                Log.d("mytag", "response = $response")

                // if token was sent
                if (response.has("token")) {
                    // store token in shared prefs
                    val token = response.getString("token")

                    val prefs = getSharedPreferences(
                        MyConfig.SHARED_PREFS_FILENAME,
                        MODE_PRIVATE
                    )
                    val prefsEditor = prefs.edit()
                    prefsEditor.putString("token", token)
                    Log.d("mytag","$token")

                    val type = response.getString("user")

                    if (type == "patient") {
                        prefsEditor.putString("type", "patient")
                        val intent = Intent(this@Login, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    prefsEditor.apply()
                }

                // if there is an error (wrong email or password)
                if (response.has("error")) {
                    val errorMesssage = response.getString("error")
                    Toast.makeText(this, errorMesssage, Toast.LENGTH_SHORT).show()

                }
            },
            { error ->
                Log.e(
                    "mytag",
                    "Error: $error - Status Code = ${error.networkResponse?.statusCode}"
                )
                Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)
    }
}