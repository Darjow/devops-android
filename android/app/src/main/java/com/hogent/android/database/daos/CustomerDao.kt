package com.hogent.android.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hogent.android.database.entities.Customer

@Dao
interface CustomerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(klant: Customer)

    @Update
    fun update(klant: Customer)

    @Query("SELECT * FROM customer_table WHERE id = :key")
    fun get(key: Long): LiveData<Customer?>

    @Query("SELECT * FROM customer_table where email = (:email) and password = (:password)")
    fun login(email: String, password: String): Customer?

    @Query("SELECT * FROM customer_table ORDER BY id desc")
    fun getAllUsers(): LiveData<Array<Customer>>
    
}



