package com.hogent.android.network.dtos.requests

import com.hogent.android.data.entities.ContactDetails
import com.hogent.android.data.entities.Course

class CustomerEdit (
    var firstName: String,
    var name: String,
    var phoneNumber: String,
    var email: String,
    var opleiding: Course?,
    var bedrijf: String?,
    var contactPersoon: ContactDetails?,
    var reserveContactpersoon: ContactDetails?
    )