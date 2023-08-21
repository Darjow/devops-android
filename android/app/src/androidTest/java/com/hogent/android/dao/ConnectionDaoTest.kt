package com.hogent.android.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.hogent.android.data.daos.ConnectionDao
import com.hogent.android.data.database.RoomDB
import com.hogent.android.data.entities.Connection
import java.io.IOException
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ConnectionDaoTest {

    private lateinit var connDao: ConnectionDao
    private lateinit var db: RoomDB

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            RoomDB::class.java
        ).build()
        connDao = db.connectionDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun test_create_correctParameters_success() = runBlocking {
        val id = connDao.create(
            Connection(
                20,
                "be8.fef5f43d8gg3e4lkjlkrht5e54zh5t.com.",
                "168.172.150.4",
                "admin",
                "\$2a\$11\$B2ytf/G.ApplUD97cDbguOlBOJ1ZOOj8E1p.61wvrNhbLvq96Fn2.",
                "Password.1"
            )
        )

        Assert.assertTrue(id == 20L)
    }
}
