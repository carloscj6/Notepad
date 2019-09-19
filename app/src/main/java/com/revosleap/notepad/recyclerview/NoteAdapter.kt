package com.revosleap.notepad.recyclerview

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.revosleap.notepad.R
import com.revosleap.notepad.interfaces.ViewHolderItemClicked

class NoteAdapter(var noteList: MutableList<Notes>,viewHolderItemClicked: ViewHolderItemClicked)
    : androidx.recyclerview.widget.RecyclerView.Adapter<ViewHolder>() {
     var itemClicked: ViewHolderItemClicked= viewHolderItemClicked
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_viewholder,p0,false))
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
       val notes: Notes= noteList.get(p1)
        p0.bind(notes)
        p0.itemView.setOnLongClickListener { view ->itemClicked.onItemClicked(notes,p1)
        true}

    }
    public fun removeItem(position:Int){
        noteList.removeAt(position)
        notifyItemRemoved(position)
    }
    public fun addItem(position: Int,notes: Notes){
        noteList.add(position,notes)
    }
    fun editItem(position: Int){
        notifyItemChanged(position)
    }
}