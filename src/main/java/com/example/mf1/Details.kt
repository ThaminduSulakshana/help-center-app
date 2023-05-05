package com.example.mf1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.mf1.models.EmpModel
import com.google.firebase.database.FirebaseDatabase

class Details : AppCompatActivity() {

    private lateinit var TemID: TextView
    private lateinit var TempType: TextView
    private lateinit var TempName: TextView
    private lateinit var TempEmail: TextView
    private lateinit var TempPhone: TextView
    private lateinit var TempReason: TextView
    private lateinit var btngoUpdate: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        TemID = findViewById(R.id.TemID)
        TempType = findViewById(R.id.TempType)
        TempName = findViewById(R.id.TempName)
        TempEmail = findViewById(R.id.TempEmail)
        TempPhone = findViewById(R.id.TempPhone)
        TempReason = findViewById(R.id.TempReason)
        btngoUpdate = findViewById(R.id.btngoUpdate)
        btnDelete = findViewById(R.id.btnDelete) // add this line to initialize btnDelete
        setValuesToViews()

        btngoUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("emID").toString(),
                intent.getStringExtra("empName").toString()

            )
        }
        btnDelete.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Delete Details")
                .setMessage("Are you sure you want to delete ?")
                .setPositiveButton("Yes") { _, _ ->
                    deleteRecord(intent.getStringExtra("emID").toString())
                }
                .setNegativeButton("No", null)
                .show()
        }
        val btnBack = findViewById<Button>(R.id.btnBack);

        btnBack.setOnClickListener() {
            val intent = Intent(this, ShowDetails::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun setValuesToViews() {

        //(emId, empType, empName, empEmail, empPhone, empReason)
        TemID.text = intent.getStringExtra("emID")
        TempType.text = intent.getStringExtra("empType")
        TempName.text = intent.getStringExtra("empName")
        TempEmail.text = intent.getStringExtra("empEmail")
        TempPhone.text = intent.getStringExtra("empPhone")
        TempReason.text = intent.getStringExtra("empReason")
    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("sulayako").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, ShowDetails::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUpdateDialog(
        emID: String,
        empName: String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.activity_update_details, null)

        mDialog.setView(mDialogView)

        val upType = mDialogView.findViewById<EditText>(R.id.upType)
        val upName = mDialogView.findViewById<EditText>(R.id.upName)
        val upEmail = mDialogView.findViewById<EditText>(R.id.upEmail)
        val upPhone = mDialogView.findViewById<EditText>(R.id.upPhone)
        val upReason = mDialogView.findViewById<EditText>(R.id.upReason)
        val btnUpdate = mDialogView.findViewById<Button>(R.id.btnUpdate)


        upType.setText(intent.getStringExtra("empType").toString())
        upName.setText(intent.getStringExtra("empName").toString())
        upEmail.setText(intent.getStringExtra("empEmail").toString())
        upPhone.setText(intent.getStringExtra("empPhone").toString())
        upReason.setText(intent.getStringExtra("empReason").toString())

        mDialog.setTitle("Updating session $empName")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdate.setOnClickListener{
            updateEmpData(
                emID,
                upType.text.toString(),
                upName.text.toString(),
                upEmail.text.toString(),
                upPhone.text.toString(),
                upReason.text.toString()
            )
            Toast.makeText(applicationContext, "Updated", Toast.LENGTH_LONG).show()

            // setting updated data to text view
            TempType.text = upType.text.toString()
            TempName.text = upName.text.toString()
            TempEmail.text = upEmail.text.toString()
            TempPhone.text = upPhone.text.toString()
            TempReason.text = upReason.text.toString()

            alertDialog.dismiss()
        }

    }

    private fun updateEmpData(
        id:String,
        type:String,
        name:String,
        email:String,
        phone:String,
        reason:String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("sulayako").child(id)
        val empInfo = EmpModel(id, type, name, email, phone, reason)
        dbRef.setValue(empInfo)
    }
    private fun saveData(
        type:String,
        name:String,
        email:String,
        phone:String,
        reason:String
    ) {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("sulayako")
        val id = myRef.push().key
        val empInfo = EmpModel(id.toString(), type, name, email, phone, reason)

        myRef.child(id.toString()).setValue(empInfo)
            .addOnSuccessListener {
                Toast.makeText(this, "Saved successfully!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ShowDetails::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener{
                Toast.makeText(this, "Error saving data", Toast.LENGTH_SHORT).show()
            }
    }


}
