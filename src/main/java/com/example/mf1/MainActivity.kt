package com.example.mf1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firebase : DatabaseReference = FirebaseDatabase.getInstance().getReference()

        val btnContact = findViewById<Button>(R.id.btnContact);

        btnContact.setOnClickListener() {
            val intenta = Intent(this, from::class.java)
            startActivity(intenta)
            finish()
        }

        val btnFaq = findViewById<Button>(R.id.btnFaq);

        btnFaq.setOnClickListener() {
            val intenta = Intent(this, faq::class.java)
            startActivity(intenta)
            finish()
        }
    }
}