package com.jutt.citiessearchmvvm.extensions

import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText

var TextInputEditText.value
    get() = this.text.toString()
    set(value) {
        this.setText(value)
    }

var EditText.value
    get() = this.text.toString()
    set(value) {
        this.setText(value)
    }