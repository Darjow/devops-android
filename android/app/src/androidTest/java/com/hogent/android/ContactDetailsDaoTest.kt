package com.hogent.android

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.hogent.android.data.daos.ContactDetailsDao
import com.hogent.android.data.daos.CustomerDao
import com.hogent.android.data.database.RoomDB
import com.hogent.android.data.entities.ContactDetails
import com.hogent.android.data.entities.User
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

class ContactDetailsDaoTest {

    private lateinit var contactDao: ContactDetailsDao
    private lateinit var db: RoomDB

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, RoomDB::class.java
        ).build()
        contactDao = db.contactDetailsDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun testCreateCorrectCredentials() = runBlocking {
        val id = contactDao.create(
            ContactDetails(
                "0497815773",
                "felkfj@hotmail.com",
                "First name",
                "Some Name",
            )
        )

        Assert.assertFalse(id == null)

    }
    @Test
    fun testCreateWrongCredentials() = runBlocking {
        val id = contactDao.create(
            ContactDetails(
                "0497815773",
                "felkfj@hotmail.com",
                "",
                "",
                50
            )
        )

        Assert.assertTrue(id.toInt() == 50)

    }
    //when caching we specify ID on items. And not use autonumbering
    @Test
    fun testCreateWithId() = runBlocking {
        val id = contactDao.create(
            ContactDetails(
                "0497815773",
                "felkfj@hotmail.com",
                "First name",
                "Some Name",
                50
            )
        )

        Assert.assertTrue(id.toInt() == 50)

    }
}

