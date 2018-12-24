package com.revosleap.notepad.interfaces

import com.revosleap.notepad.recyclerview.Notes

interface ViewHolderItemClicked {
    fun onItemClicked(note:Notes,position: Int) {

    }
}