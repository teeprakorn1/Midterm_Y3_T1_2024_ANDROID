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
import com.rmutto.midterm.R
import org.json.JSONObject

class HomeInsert_2 : AppCompatActivity() {

    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>

    private val homeSize = intent.getStringExtra("homeSize")
    private val homeBedroom = intent.getStringExtra("homeBedroom")
    private val homePrice = intent.getStringExtra("homePrice")
    private val homeCondition = intent.getStringExtra("homeCondition")
    private val homeType = intent.getStringExtra("homeType")
    private val homeYearBuilt = intent.getStringExtra("homeYearBuilt")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_insert_2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.hide()

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
                val imageUri: Uri? = data?.data
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
            val url = getString(R.string.url_server) + getString(R.string.insert_url)
            val okHttpClient = OkHttpClient()
            val formBody: RequestBody = FormBody.Builder()
                .add("Home_Size", homeSize.toString())
                .add("Home_Bedroom", homeBedroom.toString())
                .add("Home_Bathroom", homeBathroomEdittext.toString())
                .add("Home_Price", homePrice.toString())
                .add("Home_Condition", homeCondition.toString())
                .add("Home_Type", homeType.toString())
                .add("Home_YearBuilt", homeYearBuilt.toString())
                .add("Home_ParkingSpace", homeParkingSpaceEdittext.toString())
                .add("Home_Address", homeAddressEdittext.toString())
                .build()
            val request: Request = Request.Builder()
                .url(url)
                .post(formBody)
                .build()
            val response = okHttpClient.newCall(request).execute()
            if(response.isSuccessful) {
                val  obj = JSONObject(response.body!!.string())
                val status = obj["status"].toString()
                if (status == "true") {
                    val message = obj["message"].toString()
                    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }else if (status == "false") {
                    val message = obj["message"].toString()
                    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
            }else{
                Toast.makeText(applicationContext, "เกิดข้อผิดพลาดในการเชื่อมต่อ", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
        }
    }
}
