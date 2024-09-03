package com.rmutto.midterm.insert

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rmutto.midterm.HomeActivity
import com.rmutto.midterm.R

class HomeInsert_1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_insert_1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.homeInsertMain)) { v, insets ->
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

        supportActionBar?.hide()
        //For an synchronous task
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val nextButton = findViewById<Button>(R.id.Next_Button)
        val homeSizeEdittext = findViewById<EditText>(R.id.HomeSize_edittext)
        val homeBedroomEdittext = findViewById<EditText>(R.id.HomeBedroom_edittext)
        val homePriceEdittext = findViewById<EditText>(R.id.HomePrice_edittext)
        val homeConditionEdittext = findViewById<EditText>(R.id.HomeCondition_edittext)
        val homeTypeEdittext = findViewById<EditText>(R.id.HomeType_edittext)
        val homeYearBuiltEdittext = findViewById<EditText>(R.id.HomeYearBuilt_edittext)

        nextButton.setOnClickListener {

            if (homeSizeEdittext.text.toString().isEmpty()){
                homeSizeEdittext.error = "กรุณากรอกข้อมูล"
                return@setOnClickListener
            }else if(homeBedroomEdittext.text.toString().isEmpty()){
                homeBedroomEdittext.error = "กรุณากรอกข้อมูล"
                return@setOnClickListener
            }else if(homePriceEdittext.text.toString().isEmpty()){
                homePriceEdittext.error = "กรุณากรอกข้อมูล"
                return@setOnClickListener
            }else if(homeConditionEdittext.text.toString().isEmpty()){
                homeConditionEdittext.error = "กรุณากรอกข้อมูล"
                return@setOnClickListener
            }else if(homeTypeEdittext.text.toString().isEmpty()){
                homeTypeEdittext.error = "กรุณากรอกข้อมูล"
                return@setOnClickListener
            }else if(homeYearBuiltEdittext.text.toString().isEmpty()){
                homeYearBuiltEdittext.error = "กรุณากรอกข้อมูล"
                return@setOnClickListener
            }

            val intent = Intent(this, HomeInsert_2::class.java)
            intent.putExtra("homeSize",homeSizeEdittext.text.toString())
            intent.putExtra("homeBedroom",homeBedroomEdittext.text.toString())
            intent.putExtra("homePrice",homePriceEdittext.text.toString())
            intent.putExtra("homeCondition",homeConditionEdittext.text.toString())
            intent.putExtra("homeType",homeTypeEdittext.text.toString())
            intent.putExtra("homeYearBuilt",homeYearBuiltEdittext.text.toString())
            intent.putExtra("statusActivity","1")
            startActivity(intent)
            finish()
        }
    }
}