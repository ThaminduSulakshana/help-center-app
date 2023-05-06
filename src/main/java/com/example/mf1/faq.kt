package com.example.mf1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class faq : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)

        val btnBack = findViewById<Button>(R.id.btnBack);

        btnBack.setOnClickListener() {
            val intenta = Intent(this, MainActivity::class.java)
            startActivity(intenta)
            finish()
        }
    }
}