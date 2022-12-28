package com.hogent.android.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hogent.android.database.entities.Contract

@Dao
interface ContractDao {
    @Insert
    fun insert(contract: Contract): Long

    @Update
    fun update(contract: Contract)

    @Query("SELECT * FROM contract_table WHERE id = :key")
    suspend fun get(key: Long): Contract?
}