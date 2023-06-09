package com.example.myapplication2.UI

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import com.example.myapplication2.ults.MyRequest
import com.example.myapplication2.R
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject
import java.util.*


class Register : AppCompatActivity() {
    var age :Int = 0
    private lateinit var BOD:TextInputEditText
    private lateinit var registerBtn:Button
    private lateinit var backBtn:ImageView
    private lateinit var fname:TextInputEditText
    private lateinit var lname:TextInputEditText
    private lateinit var phoneNum:TextInputEditText
    private lateinit var addressInputEditText:TextInputEditText
    private lateinit var assn:TextInputEditText
    private lateinit var passwordInputEditText:TextInputEditText
    private lateinit var emailInput:TextInputEditText




/////////////
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        BOD=findViewById(R.id.BOD)
        registerBtn=findViewById(R.id.registerBtn)
        backBtn=findViewById(R.id.backBtn)
        fname=findViewById(R.id.fullname)
        phoneNum=findViewById(R.id.phoneNum)
        addressInputEditText=findViewById(R.id.addressInputEditText)
        assn=findViewById(R.id.carerEmail)
        passwordInputEditText=findViewById(R.id.passwordInputEditText)
        emailInput=findViewById(R.id.emailInput)



        /////////////////
        BOD.setOnClickListener {
            calenderShow()
        }

        registerBtn.setOnClickListener { RegisterData() }

        backBtn.setOnClickListener {onBackPressed()}
    }


    private fun calenderShow() {

        val calendar= Calendar.getInstance()
        var today = Calendar.getInstance()

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener
            { view, year, monthOfYear, dayOfMonth ->

                if(monthOfYear + 1 >= 10) {

                    BOD.setText("" + (monthOfYear + 1) + "/" + dayOfMonth + "/" + year)

                }else{
                    BOD.setText("0" + (monthOfYear + 1) + "/" + dayOfMonth + "/" + year)
                }

                age = today.get(Calendar.YEAR) - year

            }, year, month, day)

        if (today.get(Calendar.DAY_OF_YEAR) < calendar.get(Calendar.DAY_OF_YEAR))
        {
            age--
        }

        if(age < 15){
            BOD.text?.clear()
            Toast.makeText(this,"Age should be greater than 15", Toast.LENGTH_LONG).show()
        }

        //currentTimeMillis
        datePickerDialog.show()

    }

    private fun RegisterData(){


        if(validateDate()) {

            // data we send in the request: Email and password
            val params = JSONObject()

            params.put("patient_first_name", fname.text.toString())
            params.put("patienet_address", addressInputEditText.text.toString())
            params.put("patient_email", emailInput.text.toString())
            params.put("patient_SSN", assn.text.toString())
            params.put("patient_phone", phoneNum.text.toString())
            params.put("patient_date_of_birth",BOD.text.toString())
            params.put("patient_password", passwordInputEditText.text.toString())


            Log.d("mytag", "${BOD.text.toString()}")

            // send request
            val queue = Volley.newRequestQueue(this)
            val request = MyRequest(
                this,
                Request.Method.POST,
                "/register",
                params,
                { response ->

                    Log.d("mytag", "response = $response")

                    if(response.getString("msg") == "successful registeration") {
                        // goto Login activity
                        val intent = Intent(this, Login::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this,"this email or social security number" +
                                " is already registered",
                            Toast.LENGTH_LONG).show()
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

    private fun validateDate(): Boolean {
        var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"


        if((assn.text.toString().isNotEmpty() && assn.text.toString().length == 14 ) &&
            (phoneNum.text.toString().isNotEmpty() && phoneNum.text.toString().length == 11) &&
            BOD.text.toString().isNotEmpty()
            && addressInputEditText.text.toString().isNotEmpty() && age > 15&& fname.text.toString().isNotEmpty()
            && (emailInput.text.toString().trim().matches(emailPattern.toRegex()) &&
                    emailInput.text.toString().trim().isNotEmpty()) &&
            (passwordInputEditText.text.toString().isNotEmpty() &&
                    passwordInputEditText.text.toString().length > 7)) {
            return true
        } else {

            if (!emailInput.text.toString().trim().matches(emailPattern.toRegex()))
                Toast.makeText(this, "Enter a valid Email", Toast.LENGTH_LONG).show()
            else if (emailInput.text.toString().trim().isEmpty())
                Toast.makeText(this, "Enter Email", Toast.LENGTH_LONG).show()

            if (fname.text.toString().isEmpty())
                Toast.makeText(this, "Enter your First Name", Toast.LENGTH_LONG).show()

            if(passwordInputEditText.text.toString().isEmpty())
                Toast.makeText(this, "Enter your Password", Toast.LENGTH_LONG).show()
            else if (passwordInputEditText.text.toString().length < 8)
                Toast.makeText(this, "Enter valid Password = 8", Toast.LENGTH_LONG).show()
            if (assn.text.toString().isEmpty())
                Toast.makeText(this, "Enter your SSN", Toast.LENGTH_LONG).show()
            else if (assn.text.toString().length != 14)
                Toast.makeText(this, "Enter your Valid SSN (14 Number)", Toast.LENGTH_LONG).show()


            if (phoneNum.text.toString().isEmpty())
                Toast.makeText(this, "Enter your Phone", Toast.LENGTH_LONG).show()
            else if(phoneNum.text.toString().length != 11)
                Toast.makeText(this, "Enter a valid Phone", Toast.LENGTH_LONG).show()

            if(BOD.text.toString().isEmpty())
                Toast.makeText(this, "Enter your Birth of Date", Toast.LENGTH_LONG).show()

            if (addressInputEditText.text.toString().isEmpty())
                Toast.makeText(this, "Enter your Address", Toast.LENGTH_LONG).show()

            if (age <= 15) {
                BOD.text?.clear()
                Toast.makeText(this, "$age Age should be greater than 15", Toast.LENGTH_LONG).show()
            }

            return false
        }
    }
}