package com.example.taskapp

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class taskViewHolder(view:View):ViewHolder(view) {

    val textViewTitle:TextView = view.findViewById(R.id.textViewTitle)
    val updatebtn:ImageView = view.findViewById(R.id.updatebtn)
    val deletebtn:ImageView = view.findViewById(R.id.deletebtn)
    val textViewDeadline:TextView = view.findViewById(R.id.textViewDeadline)




}