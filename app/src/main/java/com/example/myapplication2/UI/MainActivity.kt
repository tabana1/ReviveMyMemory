package com.example.myapplication2.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import com.example.myapplication2.R
import com.example.myapplication2.UI.mytasks.MyTasksActivty
import com.example.myapplication2.ults.MyConfig
import com.example.myapplication2.ults.MyRequest

class MainActivity : AppCompatActivity() {
    private lateinit var myTasks:CardView
    private lateinit var emeRgency:CardView
    private lateinit var myDoctor:CardView
    private lateinit var hosNews:CardView
    private lateinit var videoCard:CardView
    private lateinit var LogOut:CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myTasks=findViewById(R.id.myTasksCard)
        emeRgency=findViewById(R.id.emergencyCard)
        myDoctor=findViewById(R.id.myDoctorCard)
        hosNews=findViewById(R.id.hosNewsCard)
        videoCard=findViewById(R.id.videoCard)
        LogOut=findViewById(R.id.logoutCard)
        myTasks.setOnClickListener {
            val intent = Intent(this, MyTasksActivty::class.java)
            startActivity(intent)
        }
        emeRgency.setOnClickListener {
            val intent = Intent(this, EmergencyActivity::class.java)
            startActivity(intent)
        }
        myDoctor.setOnClickListener {
            val intent = Intent(this, MyDoctorActivity::class.java)
            startActivity(intent)
        }
        hosNews.setOnClickListener {
            val intent = Intent(this, HospitalNewsActivity::class.java)
            startActivity(intent)
        }
        videoCard.setOnClickListener {
            val intent = Intent(this, MyTasksActivty::class.java)
            startActivity(intent)
        }
       LogOut.setOnClickListener {
           btnLogoutClicked()
        }






    }
    private fun btnLogoutClicked() {

        Log.d("mytag", "Logged Out")
        // send request
        val queue = Volley.newRequestQueue(this)
        val request = MyRequest(
            this,
            Request.Method.GET,
            "/logout-patient",
            null,
            { response ->
                Log.d("mytag", "response = $response")

                // remove token from shared prefs
                val prefs = getSharedPreferences(MyConfig.SHARED_PREFS_FILENAME, MODE_PRIVATE)
                val prefsEditor = prefs.edit()
                prefsEditor.remove("token")
                prefsEditor.apply()

                // go to login screen
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
            },
            { error ->
                val statusCode = error.networkResponse?.statusCode
                Log.e("mytag", "Error: $error - Status Code = $statusCode")
                // if 401 unauthorized
                if (statusCode == 401) {
                    val intent = Intent(this, Login::class.java)
                    startActivity(intent)
                    finish()
                }
                // if unknown error
                else {
                    Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show()
                }
            }
        )
        queue.add(request)
    }
}