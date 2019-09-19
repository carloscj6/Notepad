package com.revosleap.notepad.recyclerview

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.revosleap.notepad.R
import java.text.SimpleDateFormat
import java.util.*

class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView){

    fun bind( notes: Notes){

        val textViewNote= itemView.findViewById<TextView>(R.id.textViewNote)
        val textViewDate= itemView.findViewById<TextView>(R.id.textViewDate)
        val textviewEdited=itemView.findViewById<TextView>(R.id.textViewDateEdited)

        textViewNote.setText(notes.note)
        val sdf= SimpleDateFormat("HH:mm", Locale.getDefault())
        textViewDate.setText(sdf.format(notes.date))
        if (notes.editDate > notes.date){
            textviewEdited.setText("Edited "+sdf.format(notes.editDate))
            textviewEdited.visibility=View.VISIBLE
        }


    }

}
