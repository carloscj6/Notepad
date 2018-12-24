package com.revosleap.notepad.recyclerview

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
class Notes {
    @Id
    var id: Long = 0
    var note: String? = null
    var date: Long = 0
    var editDate: Long=0
}
