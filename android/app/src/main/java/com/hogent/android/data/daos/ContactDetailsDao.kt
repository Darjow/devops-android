package com.hogent.android.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.hogent.android.data.entities.ContactDetails

@Dao
interface ContactDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun create(contact: ContactDetails): Long
}
