package com.example.mf1.adapters

import android.view.LayoutInflater
import android.view.View
import  android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mf1.R
import com.example.mf1.models.EmpModel


class EmpAdapter (private val empList: ArrayList<EmpModel>): RecyclerView.Adapter<EmpAdapter.ViewHolder>(){

    private lateinit var mListener: onItemsClickListener

        interface onItemsClickListener{
        fun onItemsClick(position: Int)
    }
    fun setonItemsClickListener(clickListener: onItemsClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_list, parent, false)
        return ViewHolder(itemView, mListener)
    }
    override fun onBindViewHolder(holder: EmpAdapter.ViewHolder, position: Int) {
        val currentEmp = empList[position]
        holder.tvEmpName.text = currentEmp.empType
    }

    override fun getItemCount(): Int {
      return empList.size
    }
    class ViewHolder (itemView: View, ClickListener: onItemsClickListener) : RecyclerView.ViewHolder(itemView){
        val  tvEmpName: TextView = itemView.findViewById(R.id.tvEmpName)

        init {
            itemView.setOnClickListener{
                ClickListener.onItemsClick(adapterPosition)
            }
        }


    }


}