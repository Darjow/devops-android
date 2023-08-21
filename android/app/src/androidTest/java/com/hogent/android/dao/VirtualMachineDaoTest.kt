package com.hogent.android.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.hogent.android.data.daos.BackupDao
import com.hogent.android.data.daos.ConnectionDao
import com.hogent.android.data.daos.VirtualMachineDao
import com.hogent.android.data.database.RoomDB
import com.hogent.android.data.entities.*
import com.hogent.android.domain.HardWare
import com.hogent.android.domain.OperatingSystem
import com.hogent.android.domain.VirtualMachineModus
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.time.LocalDate

class VirtualMachineDaoTest {

    private lateinit var vmDao: VirtualMachineDao
    private lateinit var db: RoomDB

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, RoomDB::class.java
        ).build()
        vmDao = db.virtualMachineDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun test_create_correctParameters_success() = runBlocking {
        val vm = createVM()


        Assert.assertTrue(vm.id >= 1L)
        Assert.assertTrue(vm.projectId >= 1L)
        Assert.assertTrue(vm.contractId!! >= 1L)
        Assert.assertTrue(vm.connectionId == null)
    }

    @Test
    fun test_update_correctParameters_success() = runBlocking {
        val vm = createVM()
        val hardWare = HardWare(50000,500000,20)

        vm.hardWare = hardWare
        vm.mode = VirtualMachineModus.RUNNING
        vmDao.update(vm)
        val response = vmDao.getById(vm.id)

        Assert.assertTrue(response.memory == hardWare.memory)
        Assert.assertTrue(response.storage == hardWare.storage)
        Assert.assertTrue(response.amount_vCPU == hardWare.amount_vCPU)
        Assert.assertTrue(response.mode == VirtualMachineModus.RUNNING)

    }

    @Test
    fun test_getById_existing_success() = runBlocking {
        val vm = createVM()

        val response = vmDao.getById(vm.id)

        Assert.assertEquals(vm.id, response.id)
        Assert.assertEquals(vm.backUpId, response.backupId)
    }

    private suspend fun createVM(): VirtualMachine {
        val contId = db.contactDetailsDao.create(
            ContactDetails(
                "0497815773",
                "some@hotmail.com",
                "Billo",
                "Ollib",
            )
        )
        val custId =  db.customerDao.create(
            User(
                "Billy",
                "Willy",
                "0497815737",
                "darbar@hotmail.com",
                "Password.1",
                "X",
                null,
                contId
            )
        )

        val backupId = db.backupDao.create(
            Backup(BackupType.WEEKLY, LocalDate.now().minusDays(5)))

        val projectId = db.projectDao.createProject(Project("proje", custId))

        val vm = VirtualMachine(
            "testVM",
            OperatingSystem.FEDORA_35,
            VirtualMachineModus.WAITING_APPROVEMENT,
            HardWare(5000,15000,2),
            backupId,
             null,
            null,
            projectId,
        )

        val vmId = db.virtualMachineDao.createVM(vm)

        val contractId = db.contractDao.create(
            Contract(LocalDate.now(), LocalDate.now().plusDays(90), vmId, custId))

        vm.contractId = contractId
        vmDao.update(vm)
        vm.id = vmId

        return vm

    }
}
