package com.hogent.android.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.hogent.android.data.entities.Connection

@Dao
interface ConnectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun create(contract: Connection): Long
}
