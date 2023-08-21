package com.hogent.android.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.hogent.android.data.daos.BackupDao
import com.hogent.android.data.database.RoomDB
import com.hogent.android.data.entities.Backup
import com.hogent.android.data.entities.BackupType
import java.io.IOException
import java.time.LocalDate
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class BackupDaoTest {

    private lateinit var backupDao: BackupDao
    private lateinit var db: RoomDB

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            RoomDB::class.java
        ).build()
        backupDao = db.backupDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun test_create_correctParameters_success() = runBlocking {
        val id = backupDao.create(
            Backup(
                BackupType.DAILY,
                LocalDate.now().minusDays(1),
                50
            )
        )
        Assert.assertTrue(id == 50L)
    }
}
