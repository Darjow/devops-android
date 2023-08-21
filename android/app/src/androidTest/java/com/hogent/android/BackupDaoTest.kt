package com.hogent.android

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.hogent.android.data.daos.BackupDao
import com.hogent.android.data.daos.ContactDetailsDao
import com.hogent.android.data.database.RoomDB
import com.hogent.android.data.entities.Backup
import com.hogent.android.data.entities.BackupType
import com.hogent.android.data.entities.ContactDetails
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.time.LocalDate
import java.time.temporal.TemporalAmount

class BackupDaoTest {
    private lateinit var backupDao: BackupDao
    private lateinit var db: RoomDB

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, RoomDB::class.java
        ).build()
        backupDao = db.backupDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun testCreateCorrectCredentials() = runBlocking {
        val id = backupDao.create(
            Backup(
                BackupType.DAILY,
                LocalDate.now().minusDays(1),
                50
            )
        )
        Assert.assertTrue(id.toInt() == 50)
    }

    @Test(expected = Exception::class)
    fun testCreateWrongCredentials() = runBlocking {
        val id = backupDao.create(
            Backup(
                null as BackupType,
                LocalDate.now().minusDays(1)
            )
        )
    }
}