package com.example.myapplication2.ults

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.example.myapplication2.ults.MyConfig
import org.json.JSONArray


class MyRequestArray (private val context: Context,
                      method: Int,
                      url: String?,
                      jsonRequest: JSONArray?,
                      listener: Response.Listener<JSONArray>?,
                      errorListener: Response.ErrorListener?) : JsonArrayRequest(method, MyConfig.SERVER_NAME + "/api" + url, jsonRequest, listener, errorListener) {
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