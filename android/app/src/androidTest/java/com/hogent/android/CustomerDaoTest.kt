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
import org.junit.*

import java.io.IOException


class CustomerDaoTest {
    private lateinit var userDao: CustomerDao
    private lateinit var contactDao: ContactDetailsDao
    private lateinit var db: RoomDB

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, RoomDB::class.java
        ).build()
        userDao = db.customerDao
        contactDao = db.contactDetailsDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun testCreateNoContacts() = runBlocking {
        val id = userDao.create(
            User(
                "Some Name",
                "First name",
                "0497815773",
                "felkfj@hotmail.com",
                "random.P1",
                null,
                null,
                null,
                null,
            )
        )
        val user = userDao.getById(id)

        Assert.assertTrue(user != null)

    }

    @Test
    fun testCreateWithContacts() = runBlocking {
        val contactId = contactDao.create(
            ContactDetails(
                "0497815772",
                "lkj@hotmail.com",
                "lolol",
                "lilili"
            )
        )
        val id = userDao.create(
            User(
                "Some Name",
                "First name",
                "0497815773",
                "felkfj@hotmail.com",
                "random.P1",
                null,
                null,
                contactId,
                null,
            )
        )

        val user = userDao.getById(id)

        Assert.assertTrue(user != null)
        Assert.assertTrue(user.c1_id == contactId)
        Assert.assertTrue(user.c1_phone != null)

    }

    @Test
    fun testGetByIdNoCustomer() = runBlocking {
        val user = userDao.getById(20)
        Assert.assertTrue(user == null)
    }

    @Test(expected = Exception::class)
    fun testUpdateUserIncorrectCredentials() = runBlocking {
        val user = User(
            "Some Name",
            "First name",
            "0497815773",
            "felkfj@hotmail.com",
            "random.P1",
            null,
            null,
            null,
            null
        )
        val id = userDao.create(user)


        userDao.updateCustomer(User(
            "Some Name",
            "First name",
            "",
            "",
            "random.P1",
            null,
            null,
            5,
            null,
            id,
        ))
    }

    @Test(expected = Exception::class)
    fun testUpdateUserCorrectCredentials() = runBlocking {
        val user = User(
            "Some Name",
            "First name",
            "0497815773",
            "felkfj@hotmail.com",
            "random.P1",
            null,
            null,
            null,
            null
        )
        val id = userDao.create(user)


        userDao.updateCustomer(User(
            "New Name",
            "New First name",
            "0497444773",
            "felkfj@hotmail.com",
            "random.P15555",
            null,
            null,
            5,
            null,
            id,
        ))
    }
}
