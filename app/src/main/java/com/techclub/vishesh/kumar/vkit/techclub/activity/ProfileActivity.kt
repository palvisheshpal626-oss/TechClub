package com.techclub.vishesh.kumar.vkit.techclub.activity

import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.techclub.vishesh.kumar.vkit.techclub.R

class ProfileActivity : AppCompatActivity() {

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val db by lazy { FirebaseDatabase.getInstance().reference }

    private var imageUri: Uri? = null
    private lateinit var imgProfile: ImageView

    // ✅ SAFE IMAGE PICKER (NO CRASH)
    private val pickImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                imageUri = uri

                Glide.with(this)
                    .load(uri)
                    .into(imgProfile)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        imgProfile = findViewById(R.id.imgProfile)
        val etName = findViewById<EditText>(R.id.etName)
        val etBio = findViewById<EditText>(R.id.etBio)
        val btnSave = findViewById<Button>(R.id.btnSave)

        val user = auth.currentUser
        if (user == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val uid = user.uid

        // 📥 Load existing profile
        db.child("users").child(uid).get()
            .addOnSuccessListener { snapshot ->
                etName.setText(snapshot.child("name").getValue(String::class.java) ?: "")
                etBio.setText(snapshot.child("bio").getValue(String::class.java) ?: "")

                val photo = snapshot.child("photoUri").getValue(String::class.java)
                if (!photo.isNullOrEmpty()) {
                    Glide.with(this)
                        .load(photo)
                        .into(imgProfile)
                }
            }

        // 🖼 Pick image safely
        imgProfile.setOnClickListener {
            pickImage.launch("image/*")
        }

        // 💾 Save profile
        btnSave.setOnClickListener {

            val name = etName.text.toString().trim()
            val bio = etBio.text.toString().trim()

            if (name.isEmpty()) {
                etName.error = "Enter name"
                return@setOnClickListener
            }

            val map = HashMap<String, Any>()
            map["name"] = name
            map["bio"] = bio

            if (imageUri != null) {
                map["photoUri"] = imageUri.toString()
            }

            db.child("users").child(uid)
                .updateChildren(map)
                .addOnSuccessListener {
                    Toast.makeText(this, "Profile saved", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, it.message ?: "Error", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
