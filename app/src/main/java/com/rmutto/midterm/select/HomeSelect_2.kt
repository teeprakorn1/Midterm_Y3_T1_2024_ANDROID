package com.rmutto.midterm.select

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rmutto.midterm.HomeActivity
import com.rmutto.midterm.R
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import com.bumptech.glide.Glide
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File

class HomeSelect_2 : AppCompatActivity() {

    private var imageName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_select_2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val statusActivity = intent.getStringExtra("statusActivity")
        if (statusActivity != "1") {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        val homeID = intent.getStringExtra("homeID")
        if (statusActivity == null) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        supportActionBar?.hide()
        //For an synchronous task
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val imageViewFile = findViewById<ImageView>(R.id.imageViewFile)
        val backButton = findViewById<Button>(R.id.Back_Button)
        val homeSizeShow = findViewById<TextView>(R.id.HomeSize_Show)
        val homePriceShow = findViewById<TextView>(R.id.HomePrice_Show)
        val homeConditionShow = findViewById<TextView>(R.id.HomeCondition_Show)
        val homeTypeShow = findViewById<TextView>(R.id.HomeType_Show)
        val homeYearBuiltShow = findViewById<TextView>(R.id.HomeYearBuilt_Show)
        val homeParkingSpaceShow = findViewById<TextView>(R.id.HomeParkingSpace_Show)
        val homeAddressShow = findViewById<TextView>(R.id.HomeAddress_Show)
        val homeIDShow = findViewById<TextView>(R.id.HomeID_Show)
        val homeBathroomShow = findViewById<TextView>(R.id.HomeBathroom_Show)

        var url =
            getString(R.string.url_server) + getString(R.string.get_url_id) + homeID.toString()
        val okHttpClient = OkHttpClient()
        var request: Request = Request.Builder()
            .url(url)
            .get()
            .build()
        var response = okHttpClient.newCall(request).execute()
        if (response.isSuccessful) {
            var obj = JSONObject(response.body!!.string())
            var status = obj["status"].toString()
            if (status == "true") {
                homeSizeShow.text = obj["Home_Size"].toString()
                homePriceShow.text = obj["Home_Price"].toString()
                homeConditionShow.text = obj["Home_Condition"].toString()
                homeTypeShow.text = obj["Home_Type"].toString()
                homeYearBuiltShow.text = obj["Home_YearBuilt"].toString()
                homeParkingSpaceShow.text = obj["Home_ParkingSpace"].toString()
                homeAddressShow.text = obj["Home_Address"].toString()
                homeIDShow.text = obj["Home_ID"].toString()
                homeBathroomShow.text = obj["Home_Bathroom"].toString()
                imageName = obj["Home_ImageURL"].toString()
            } else {
                val message = obj["message"].toString()
                Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                intent = Intent(this, HomeSelect_1::class.java)
                startActivity(intent)
                finish()
            }

            if (imageName != null) {
                url = getString(R.string.url_server) + imageName.toString()
                // Load image using Glide
                Glide.with(this)
                    .load(url)
                    .into(imageViewFile)
            }

            backButton.setOnClickListener {
                intent = Intent(this, HomeSelect_1::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}