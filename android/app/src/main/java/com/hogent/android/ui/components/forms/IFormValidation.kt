package com.hogent.android.ui.components.forms

interface IFormValidation {

    public fun isValid(): Boolean
    public fun getError(): String?
}
