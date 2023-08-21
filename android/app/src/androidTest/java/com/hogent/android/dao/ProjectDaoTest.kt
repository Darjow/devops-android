package com.hogent.android.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.hogent.android.data.daos.ProjectDao
import com.hogent.android.data.database.RoomDB
import com.hogent.android.data.entities.ContactDetails
import com.hogent.android.data.entities.Project
import com.hogent.android.data.entities.User
import java.io.IOException
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ProjectDaoTest {
    private lateinit var projectDao: ProjectDao
    private lateinit var db: RoomDB

    @Before
    fun createDb() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            RoomDB::class.java
        ).build()
        projectDao = db.projectDao

        seedTestSuite()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun test_create_correctParameters_success() = runBlocking {
        val id = projectDao.createProject(
            Project(
                "A Project",
                1
            )
        )
        Assert.assertTrue(id > 1L)
    }

    @Test(expected = Exception::class)
    fun test_create_foreignKeyConstraint_fail() = runBlocking {
        val id = projectDao.createProject(
            Project(
                "A Project",
                10
            )
        )
    }

    @Test
    fun test_getAll_success() = runBlocking {
        val projects = projectDao.getAllByCustomerId(1L)

        Assert.assertTrue(projects.isNotEmpty())
        Assert.assertTrue(projects.size == 1)
        Assert.assertTrue(projects[0].customerId == 1)
    }

    @Test
    fun test_getById_unknownId_returns_emptyList_success() = runBlocking {
        val projectRecords = projectDao.getById(5L)

        Assert.assertTrue(projectRecords.isEmpty())
    }

    private suspend fun seedTestSuite() {
        val con = db.contactDetailsDao.create(
            ContactDetails(
                "0497815773",
                "some@hotmail.com",
                "Billo",
                "Ollib",
                1
            )
        )
        val cus = db.customerDao.create(
            User(
                "Billy",
                "Willy",
                "0497815737",
                "darbar@hotmail.com",
                "Password.1",
                "X",
                null,
                con
            )
        )

        val projects = listOf {
            Project("Eins", 1, 1)
            Project("Zwei", 1, 2)
            Project("Polizei", 1, 3)
        }

        projects.forEach {
            projectDao.createProject(it.invoke())
        }
    }
}
