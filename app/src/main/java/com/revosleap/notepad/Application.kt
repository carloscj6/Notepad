package com.revosleap.notepad


import com.revosleap.notepad.recyclerview.MyObjectBox

import io.objectbox.BoxStore

class Application : android.app.Application() {
    override fun onCreate() {
        super.onCreate()
        boxStore = MyObjectBox.builder().androidContext(this).build()
    }

    companion object {

        var boxStore: BoxStore? = null
            private set
    }
}
