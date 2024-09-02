package com.rmutto.midterm

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rmutto.midterm.insert.HomeInsert_1
import com.rmutto.midterm.select.HomeSelect_1

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.hide()
        //For an synchronous task
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val addButton = findViewById<Button>(R.id.Add_Button)
        val getButton = findViewById<Button>(R.id.Get_Button)

        addButton.setOnClickListener {
            val intent = Intent(this, HomeInsert_1::class.java)
            startActivity(intent)
        }
        getButton.setOnClickListener {
            val intent = Intent(this, HomeSelect_1::class.java)
            startActivity(intent)
        }

    }
}