package com.example.myapplication2.ults

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.myapplication2.ults.MyConfig
import org.json.JSONObject

class MyRequest(

    private val context: Context,

    method: Int,
    url: String?,
    jsonRequest: JSONObject?,
    listener: Response.Listener<JSONObject>?,
    errorListener: Response.ErrorListener?
) : JsonObjectRequest(method, MyConfig.SERVER_NAME + "/api" + url, jsonRequest, listener, errorListener) {
    override fun getHeaders(): MutableMap<String, String> {
        val headers = HashMap<String, String>()
        val prefs = context.getSharedPreferences(
            MyConfig.SHARED_PREFS_FILENAME,
            AppCompatActivity.MODE_PRIVATE
        )
        val token = prefs.getString("token", null)
        if(token != null) {
            headers["Authorization"] = "Bearer $token"
        }
        return headers
    }
}