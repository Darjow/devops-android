package com.hogent.android.ui.customer

import android.text.Editable
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hogent.android.data.entities.ContactDetails
import com.hogent.android.data.entities.Customer
import com.hogent.android.data.repositories.CustomerRepository
import com.hogent.android.network.dtos.requests.CustomerEdit
import com.hogent.android.ui.components.forms.ContactOne
import com.hogent.android.ui.components.forms.ContactTwo
import com.hogent.android.ui.components.forms.CustomerContactEditForm
import com.hogent.android.util.AuthenticationManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CustomerViewModel(private val repo: CustomerRepository) : ViewModel() {

    private val _form = MutableLiveData(CustomerContactEditForm())
    private val _klant = MutableLiveData<Customer>()

    val inEditMode = MutableLiveData(false)

    private val _errorToast = MutableLiveData<Boolean>()
    private val _successToast = MutableLiveData<Boolean>()
    private val _failsafeRedirect = MutableLiveData(false)

    val klant: LiveData<Customer>
        get() = _klant
    val success: LiveData<Boolean>
        get() = _successToast
    val error: LiveData<Boolean>
        get() = _errorToast
    val failsafeRedirect: LiveData<Boolean>
        get() = _failsafeRedirect

    fun setEmail(contactPerson: Int, text: Editable) {
        if (contactPerson == 1) {
            val contact = _form.value!!.contact1
            _form.postValue(
                CustomerContactEditForm(
                    ContactOne(
                        text.toString(),
                        contact.phone,
                        contact.fullName
                    ),
                    _form.value!!.contact2
                )
            )
        } else if (contactPerson == 2) {
            val contact = _form.value!!.contact2
            _form.postValue(
                CustomerContactEditForm(
                    _form.value!!.contact1,
                    ContactTwo(text.toString(), contact.phone, contact.fullName)
                )
            )
        }
    }

    fun setPhone(contactPerson: Int, text: Editable) {
        if (contactPerson == 1) {
            val contact = _form.value!!.contact1
            _form.postValue(
                CustomerContactEditForm(
                    ContactOne(
                        contact.email,
                        text.toString(),
                        contact.fullName
                    ),
                    _form.value!!.contact2
                )
            )
        } else if (contactPerson == 2) {
            val contact = _form.value!!.contact2
            _form.postValue(
                CustomerContactEditForm(
                    _form.value!!.contact1,
                    ContactTwo(contact.email, text.toString(), contact.fullName)
                )
            )
        }
    }

    fun setFullName(contactPerson: Int, text: Editable) {
        if (contactPerson == 1) {
            val contact = _form.value!!.contact1
            _form.postValue(
                CustomerContactEditForm(
                    ContactOne(
                        contact.email,
                        contact.phone,
                        text.toString()
                    ),
                    _form.value!!.contact2
                )
            )
        } else if (contactPerson == 2) {
            val contact = _form.value!!.contact2
            _form.postValue(
                CustomerContactEditForm(
                    _form.value!!.contact1,
                    ContactTwo(contact.email, contact.phone, text.toString())
                )
            )
        }
    }

    fun onEditButtonPressed() {
        inEditMode.postValue(true)
        val contactps1 = klant.value!!.contactPersoon
        val contactps2 = klant.value!!.reserveContactPersoon
        var contactOne = ContactOne("", "", "")
        var contactTwo = ContactTwo("", "", "")

        if (contactps1 != null) {
            contactOne = ContactOne(
                contactps1.email!!,
                contactps1.phoneNumber!!,
                contactps1.firstName + " " + contactps1.lastName
            )
        }
        if (contactps2 != null) {
            contactTwo = ContactTwo(
                contactps2.email!!,
                contactps2.phoneNumber!!,
                contactps2.firstName + " " + contactps2.lastName
            )
        }

        _form.postValue(CustomerContactEditForm(contactOne, contactTwo))
    }

    fun onCancelButtonPressed() {
        _form.postValue(CustomerContactEditForm())
        inEditMode.postValue(false)
    }

    fun onSubmitButtonPressed() {
        if (_form.value!!.isValid()) {
            inEditMode.postValue(false)
            _successToast.postValue(true)
            persistCustomer()
        } else {
            _errorToast.postValue(true)
        }
    }

    fun getVisibleId(): Int {
        return View.VISIBLE
    }

    fun getInvisibleId(): Int {
        return View.INVISIBLE
    }

    private fun persistCustomer() {
        val customer: Customer = klant!!.value!!.copy()
        val form = CustomerEdit(
            customer.firstName,
            customer.name,
            customer.phoneNumber,
            customer.email,
            customer.opleiding,
            customer.bedrijfsnaam,
            customer.contactPersoon,
            customer.reserveContactPersoon
        )

        val contactDetails1 = ContactDetails(
            _form.value!!.contact1.phone,
            _form.value!!.contact1.email,
            _form.value!!.contact1.fullName.split(" ")[0],
            _form.value!!.contact1.fullName.substringAfter(" ")
        )
        form.contactPersoon = contactDetails1
        customer.contactPersoon = contactDetails1

        if (_form.value!!.contact2.isValid()) {
            val contactDetails2 = ContactDetails(
                _form.value!!.contact2.phone,
                _form.value!!.contact2.email,
                _form.value!!.contact2.fullName.split(" ")[0],
                _form.value!!.contact2.fullName.substringAfter(" ")
            )
            form.reserveContactpersoon = contactDetails2
            customer.reserveContactPersoon = contactDetails2
        }
        viewModelScope.launch(Dispatchers.Main) {
            repo.updateCustomer(customer.id, form)
            _klant.postValue(customer)
        }
    }

    fun doneErrorToast() {
        _errorToast.postValue(false)
    }

    fun doneSuccessToast() {
        _successToast.postValue(false)
    }

    fun getError(): String? {
        return _form.value!!.getError()
    }

    fun doneNavigation() {
        _failsafeRedirect.postValue(false)
    }

    init {
        if (!AuthenticationManager.loggedIn()) {
            _failsafeRedirect.postValue(true)
        } else {
            _klant.postValue(AuthenticationManager.getCustomer())
        }
    }
}
