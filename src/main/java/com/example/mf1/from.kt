package com.example.mf1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.mf1.models.EmpModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class from : AppCompatActivity() {

    private lateinit var selection_type: Spinner
    private lateinit var editTextPersonName: EditText
    private lateinit var editTextEmailAddress: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var editTextReason: EditText
    private lateinit var checkBoxAgree: CheckBox
    private lateinit var btnSubmit: Button

    private lateinit var dbReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_from)

        selection_type = findViewById(R.id.selection_type)
        editTextPersonName = findViewById(R.id.editTextPersonName)
        editTextEmailAddress = findViewById(R.id.editTextEmailAddress)
        editTextPhone = findViewById(R.id.editTextPhone)
        editTextReason = findViewById(R.id.editTextReason)
        checkBoxAgree = findViewById(R.id.checkBoxAgree)
        btnSubmit = findViewById(R.id.btnSubmit)

        dbReference = FirebaseDatabase.getInstance().getReference("sulayako")

        // btn submit click listener
        btnSubmit.setOnClickListener {
            submitData()
        }

        // btn back click listener
        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // btn show click listener
        val btnShow = findViewById<Button>(R.id.btnShow)
        btnShow.setOnClickListener {
            val intent = Intent(this, ShowDetails::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun submitData(){

        // getting values
        val empType = selection_type.selectedItem.toString()
        val empName = editTextPersonName.text.toString()
        val empEmail = editTextEmailAddress.text.toString()
        val empPhone = editTextPhone.text.toString()
        val empReason = editTextReason.text.toString()
        val agreeChecked = checkBoxAgree.isChecked

        if (empType.isEmpty() || empName.isEmpty() || empEmail.isEmpty() || empPhone.isEmpty() || empReason.isEmpty()) {
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_LONG).show()
            return
        }

        if (!agreeChecked) {
            Toast.makeText(this, "Please agree to the terms and Conditions", Toast.LENGTH_LONG).show()
            return
        }

        val emId = dbReference.push().key!!

        val sulayko = EmpModel(emId, empType, empName, empEmail, empPhone, empReason)

        dbReference.child(emId).setValue(sulayko)
            .addOnCompleteListener {
                Toast.makeText(this, "submitted successfully" , Toast.LENGTH_LONG).show()

                editTextPersonName.text.clear()
                editTextEmailAddress.text.clear()
                editTextPhone.text.clear()
                editTextReason.text.clear()
                selection_type.setSelection(0)
                checkBoxAgree.isChecked = false

            }.addOnFailureListener { error ->
                Toast.makeText(this, "Error ${error.message}" , Toast.LENGTH_LONG).show()
            }

    }
}
