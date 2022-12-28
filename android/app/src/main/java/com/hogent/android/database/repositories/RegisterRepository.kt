package com.hogent.android.database.repositories

import com.hogent.android.database.daos.CustomerDao
import com.hogent.android.database.entities.Customer


class RegisterRepository(private val dao: CustomerDao) {
    val klanten = dao.getAllUsers()

    suspend fun insert(klant: Customer){
        return dao.insert(klant)
    }

}