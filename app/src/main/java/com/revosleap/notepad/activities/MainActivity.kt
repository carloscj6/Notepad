package com.revosleap.notepad.activities

import android.app.AlertDialog
import android.content.*
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.revosleap.notepad.Application
import com.revosleap.notepad.R
import com.revosleap.notepad.interfaces.ViewHolderItemClicked
import com.revosleap.notepad.recyclerview.NoteAdapter
import com.revosleap.notepad.recyclerview.Notes
import io.objectbox.Box

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(),ViewHolderItemClicked {

    val noteBox: Box<Notes> = Application.boxStore!!.boxFor(Notes::class.java)
    var noteList: MutableList<Notes> = ArrayList()
    var noteAdapter: NoteAdapter? = null
    var isEditingTime: Boolean= false
    var currentNote: Notes = Notes()
    var editingPosition: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        noteList= noteBox.all
        noteAdapter= NoteAdapter(noteList,this)
        setRecyclerView()
            fab.setColorFilter(Color.WHITE,PorterDuff.Mode.SRC_IN)
                fab.setOnClickListener { view ->
                    performDbTransactions()
                }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClicked(note: Notes, position: Int) {
        showDialog(note,position)
    }
    fun performDbTransactions(){
        val text:String = inputEditext.text.toString()
        if (!text.isEmpty()){
            if (!isEditingTime){
                val time: Long= System.currentTimeMillis()
                val notes= Notes().apply {
                    note = text
                    date = time
                    editDate=time
                }
                noteBox.put(notes)
                noteAdapter?.addItem(noteList.size,notes)

            }else{
                currentNote.apply {
                    note =text
                    editDate= System.currentTimeMillis()

                }
                noteBox.put(currentNote)
                noteAdapter?.editItem(editingPosition)
                isEditingTime= false
            }

            Snackbar.make(findViewById(android.R.id.content), "Success", Snackbar.LENGTH_LONG)
                .setAction("Refresh", null).show()
            inputEditext.setText("")

        }else Toast.makeText(this,"Invalid input",Toast.LENGTH_LONG).show()
    }
    fun showDialog(note: Notes,position: Int){
        val options = arrayOf("Delete","Edit","Copy","Share")

       val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)

        builder.apply {
            setTitle("Actions")
            setItems(options) { dialog, which ->
                setDialogClicks(position,note,which)

            }
        }
        val alertDialog: AlertDialog=builder.create()
        alertDialog.show()

    }
    private fun setDialogClicks(position: Int, note: Notes,arrayPosition:Int){

        when (arrayPosition){
            0->{
                noteBox.remove(note.id)
                noteAdapter?.removeItem(position)
            }
            1->{
                isEditingTime= true
                editingPosition=position
                inputEditext.setText(note.note)
                currentNote=note

            }
            2->{ val clipBoardManager: ClipboardManager= getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData: ClipData= ClipData.newPlainText("Note",note.note)
                clipBoardManager.primaryClip=clipData
                Toast.makeText(this@MainActivity,"Note Copied!",Toast.LENGTH_SHORT).show()
            }
            3->{
                val shareIntent: Intent = Intent()
                shareIntent.apply {
                    action=Intent.ACTION_SEND
                    type="text/plain"
                    putExtra(Intent.EXTRA_TEXT,note.note)

                }
                startActivity(shareIntent)


            }
        }
    }

    fun setRecyclerView(){
        recyclerview.apply {
            adapter= noteAdapter
            layoutManager=LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
        }

    }
}
