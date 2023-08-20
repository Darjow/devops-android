package com.hogent.android.data.repositories

import com.hogent.android.data.daos.ContactDetailsDao
import com.hogent.android.data.daos.CustomerDao
import com.hogent.android.data.entities.ContactDetails
import com.hogent.android.data.entities.User
import com.hogent.android.network.dtos.requests.CustomerEdit
import com.hogent.android.network.dtos.responses.EditedCustomer
import com.hogent.android.network.services.CustomerApi.customerApi
import com.hogent.android.util.TimberUtils

class CustomerRepository(private val dao: CustomerDao, private val contactDao: ContactDetailsDao){

    suspend fun updateCustomer(id: Int, cust: CustomerEdit): EditedCustomer? {
        val response = customerApi.updateCustomer(id, cust)
        val customer = dao.getById(id.toLong())
        TimberUtils.logRequest(response)

        if (!response.isSuccessful) {
            return null
        }
        if(customer != null){
            var c1Id = customer.c1_id
            var c2Id = customer.c2_id

            if(customer.c1_id == null && cust.contactPersoon != null) {
                c1Id =contactDao.create(
                    ContactDetails(
                        cust.contactPersoon!!.phoneNumber,
                        cust.contactPersoon!!.email,
                        cust.contactPersoon!!.firstName,
                        cust.contactPersoon!!.lastName
                    )
                )
            }
            if(customer.c2_id == null && cust.reserveContactpersoon != null) {
                c2Id =contactDao.create(
                    ContactDetails(
                        cust.reserveContactpersoon!!.phoneNumber,
                        cust.reserveContactpersoon!!.email,
                        cust.reserveContactpersoon!!.firstName,
                        cust.reserveContactpersoon!!.lastName
                    )
                )
            }
            dao.updateCustomer(
                User(
                    cust.name,
                    cust.firstName,
                    cust.phoneNumber,
                    cust.email,
                    customer.password,
                    customer.bedrijfsnaam,
                    cust.opleiding,
                    c1Id,
                    c2Id,
                    customer.id
                )
            )
        }

        return response.body()
    }
}
