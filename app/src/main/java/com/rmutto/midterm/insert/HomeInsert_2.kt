package com.rmutto.midterm.insert

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import androidx.core.view.WindowInsetsCompat
import com.github.dhaval2404.imagepicker.ImagePicker
import com.rmutto.midterm.HomeActivity
import com.rmutto.midterm.R
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File

class HomeInsert_2 : AppCompatActivity() {

    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_insert_2)
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

        supportActionBar?.hide()

        val homeSize = intent.getStringExtra("homeSize")
        val homeBedroom = intent.getStringExtra("homeBedroom")
        val homePrice = intent.getStringExtra("homePrice")
        val homeCondition = intent.getStringExtra("homeCondition")
        val homeType = intent.getStringExtra("homeType")
        val homeYearBuilt = intent.getStringExtra("homeYearBuilt")

        val imageViewFile = findViewById<ImageView>(R.id.imageViewFile)
        val homeParkingSpaceEdittext = findViewById<EditText>(R.id.HomeParkingSpace_edittext)
        val homeAddressEdittext = findViewById<EditText>(R.id.HomeAddress_edittext)
        val homeBathroomEdittext = findViewById<EditText>(R.id.HomeBathroom_edittext)
        val uploadButton = findViewById<Button>(R.id.upload_Button)
        val insertButton = findViewById<Button>(R.id.Insert_Button)

        // Initialize the ActivityResultLauncher for image picking
        imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                imageUri = data?.data
                imageUri?.let {
                    imageViewFile.setImageURI(it)
                    // Handle the image URI here if needed (e.g., uploading to a server)
                }
            }
        }

        uploadButton.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .createIntent { intent ->
                    imagePickerLauncher.launch(intent)
                }
        }

        insertButton.setOnClickListener {
            if (homeParkingSpaceEdittext.text.toString().isEmpty()){
                homeParkingSpaceEdittext.error = "กรุณากรอกข้อมูล"
                return@setOnClickListener
            }else if(homeAddressEdittext.text.toString().isEmpty()){
                homeAddressEdittext.error = "กรุณากรอกข้อมูล"
                return@setOnClickListener
            }else if(homeBathroomEdittext.text.toString().isEmpty()){
                homeBathroomEdittext.error = "กรุณากรอกข้อมูล"
                return@setOnClickListener
            }

            val url = getString(R.string.url_server) + getString(R.string.insert_url)
            val okHttpClient = OkHttpClient()

            val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("Home_Size", homeSize.orEmpty())
                .addFormDataPart("Home_Bedroom", homeBedroom.orEmpty())
                .addFormDataPart("Home_Bathroom", homeBathroomEdittext.text.toString())
                .addFormDataPart("Home_Price", homePrice.orEmpty())
                .addFormDataPart("Home_Condition", homeCondition.orEmpty())
                .addFormDataPart("Home_Type", homeType.orEmpty())
                .addFormDataPart("Home_YearBuilt", homeYearBuilt.orEmpty())
                .addFormDataPart("Home_ParkingSpace", homeParkingSpaceEdittext.text.toString())
                .addFormDataPart("Home_Address", homeAddressEdittext.text.toString())

            // Add image if present
            imageUri?.let {
                val file = File(it.path!!)
                val requestFile = file.asRequestBody("image/jpeg".toMediaType())
                builder.addFormDataPart("Home_Image", file.name, requestFile)
            }

            val requestBody = builder.build()

            val request: Request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()

            val response = okHttpClient.newCall(request).execute()
            if (response.isSuccessful) {
                val obj = JSONObject(response.body!!.string())
                val status = obj["status"].toString()
                if (status=="true"){
                    val message = obj["message"].toString()
                    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                    intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    val message = obj["message"].toString()
                    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
            } else {
                Toast.makeText(applicationContext, "เกิดข้อผิดพลาดในการเชื่อมต่อ", Toast.LENGTH_LONG).show()
            }
        }
    }
}
