package com.example.mf1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mf1.adapters.EmpAdapter
import com.example.mf1.models.EmpModel
import com.google.firebase.database.*


class ShowDetails : AppCompatActivity() {

    private lateinit var empRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var empList: ArrayList<EmpModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_details)

        empRecyclerView = findViewById(R.id.rvEmp)
        empRecyclerView.layoutManager = LinearLayoutManager(this)
        empRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)


        empList = arrayListOf<EmpModel>()


        getEmployeesData()
    }
    private fun getEmployeesData() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        supportActionBar?.hide()

        val btnBack = findViewById<Button>(R.id.btnBack);

        btnBack.setOnClickListener() {
            val intent = Intent(this, from::class.java)
            startActivity(intent)
            finish()
        }

        empRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE
        dbRef = FirebaseDatabase.getInstance().getReference("sulayako")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                empList.clear()
                if (snapshot.exists()) {
                    for (empSnap in snapshot.children) {
                        val empData = empSnap.getValue(EmpModel::class.java)
                        empData?.let { empList.add(it) }
                    }
                    val mAdapter = EmpAdapter(empList)
                    empRecyclerView.adapter = mAdapter

                    mAdapter.setonItemsClickListener(object : EmpAdapter.onItemsClickListener{
                        override fun onItemsClick(position: Int) {
                            val intent = Intent(this@ShowDetails, Details::class.java)

                            //put extras(emId, empType, empName, empEmail, empPhone, empReason)
                            intent.putExtra("emID", empList[position].emID)
                            intent.putExtra("empType", empList[position].empType)
                            intent.putExtra("empName", empList[position].empName)
                            intent.putExtra("empEmail", empList[position].empEmail)
                            intent.putExtra("empPhone", empList[position].empPhone)
                            intent.putExtra("empReason", empList[position].empReason)
                            startActivity(intent)
                        }

                    })

                    empRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                empRecyclerView.visibility = View.GONE
                tvLoadingData.visibility = View.VISIBLE
                tvLoadingData.text = "Error: ${error.message}"
            }
        })
    }
}
