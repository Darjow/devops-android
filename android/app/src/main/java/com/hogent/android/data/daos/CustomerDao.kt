package com.hogent.android.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hogent.android.data.entities.User
import com.hogent.android.network.dtos.responses.CustomerDaoResponse

@Dao
interface CustomerDao {

    @Update
    suspend fun updateCustomer(customer: User)

    @Query(
        value = "SELECT u.id, u.name, u.firstName, u.phoneNumber, u.email, u.bedrijfsnaam, " +
            "u.opleiding, u.password, con1.id as 'c1_id', con1.phoneNumber as 'c1_phone', " +
            "con1.email as 'c1_email'," + "con1.firstName as 'c1_firstName', " +
            "con1.lastName as 'c1_lastName', con2.id as 'c2_id', con2.phoneNumber as 'c2_phone', " +
            "con2.email as 'c2_email', con2.firstName as 'c2_firstName', " +
            "con2.lastName as 'c2_lastName'" +
            "FROM Users u " +
            "LEFT JOIN ContactDetails con1 on u.contactId = con1.id " +
            "LEFT JOIN ContactDetails con2 on u.extra_contactId = con2.id " +
            "WHERE u.id = :key"
    )
    suspend fun getById(key: Long): CustomerDaoResponse

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun create(customer: User): Long
}

