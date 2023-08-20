package com.hogent.android.util

import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.Spinner
import java.time.LocalDate

fun View.closeKeyboardOnTouch() {
    setOnTouchListener { v, event ->
        if (event.actionMasked and
            event.action != MotionEvent.ACTION_MOVE and
            MotionEvent.ACTION_SCROLL
        ) {
            val inputMethodManager =
                v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
            true
        }
        false
    }
}

fun ViewGroup.clearForm() {
    for (i in 0 until this.childCount) {
        when (val child = this.getChildAt(i)) {
            is Spinner -> child.setSelection(0) // geen waarde voor spinner
            is EditText -> child.setText("")
            is SeekBar -> child.progress = 0
            is DatePicker -> child.updateDate(
                LocalDate.now().year,
                LocalDate.now().monthValue,
                LocalDate.now().dayOfMonth
            )
            is RadioGroup -> child.clearCheck()
            is ViewGroup -> child.clearForm() // recursief om de edit en spinners te clearen
        }
    }
}
