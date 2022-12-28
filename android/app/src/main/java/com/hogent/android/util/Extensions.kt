package com.hogent.android.util

import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager


    fun View.closeKeyboardOnTouch() {
        setOnTouchListener { v, event ->
            if(event.actionMasked and event.action != MotionEvent.ACTION_MOVE and  MotionEvent.ACTION_SCROLL) {
                    val inputMethodManager =
                        v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
                    true
            }
            false
        }
    }
