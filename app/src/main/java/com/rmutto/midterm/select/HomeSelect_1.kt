package com.rmutto.midterm.select

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rmutto.midterm.R
import com.rmutto.midterm.insert.HomeInsert_1

class HomeSelect_1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_select_1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.homeSelectMain)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.hide()
        //For an synchronous task
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val searchButton = findViewById<Button>(R.id.Search_Button)
        val homeIDEdittext = findViewById<EditText>(R.id.HomeID_edittext)

        searchButton.setOnClickListener {
            if (homeIDEdittext.text.toString().isEmpty()){
                homeIDEdittext.error = "กรุณากรอกข้อมูล"
                return@setOnClickListener
            }
            val intent = Intent(this, HomeSelect_2::class.java)
            intent.putExtra("homeID",homeIDEdittext.text.toString())
            intent.putExtra("statusActivity","1")
            startActivity(intent)
        }
    }
}