package com.hogent.android.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hogent.android.database.daos.ContractDao
import com.hogent.android.database.daos.CustomerDao
import com.hogent.android.database.daos.ProjectDao
import com.hogent.android.database.daos.VirtualMachineDao
import com.hogent.android.database.entities.*
import com.hogent.android.util.ioThread
import timber.log.Timber
import java.time.LocalDate

@Database(entities = [VirtualMachine::class, Customer::class, Contract::class, Project::class ], version = 21, exportSchema = false)
@TypeConverters(CourseConverter::class, HardwareConverter::class, BackupConverter::class, ConnectionConverter::class, LocalDateConverter::class)
abstract class DatabaseImp() : RoomDatabase() {

    abstract val customerDao: CustomerDao
    abstract val virtualMachineDao: VirtualMachineDao
    abstract val projectDao: ProjectDao
    abstract val contractDao: ContractDao

    companion object {
        @Volatile
        private var INSTANCE: DatabaseImp? = null

        fun getInstance(context: Context): DatabaseImp =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context): DatabaseImp =
            Room.databaseBuilder(
                context.applicationContext,
                DatabaseImp::class.java,
                "android-devOps"
            )
                .addCallback(seedDatabase(context))
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        private fun seedDatabase(context: Context): Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    ioThread {
                        val customerDao = getInstance(context).customerDao;
                        val projectDao = getInstance(context).projectDao
                        val virtualMachineDao = getInstance(context).virtualMachineDao
                        val contractDao = getInstance(context).contractDao

                        var customer1 = Customer()
                            customer1.firstName = "Doe"
                            customer1.lastName = "John"
                            customer1.phoneNumber = "0497815223"
                            customer1.email = "john.doe@hotmail.com"
                            customer1.password = "Password#69"
                            customer1.bedrijf_opleiding = "De la where"
                        customerDao.insert(customer1)

                        var customer2 = Customer()
                            customer2.firstName = "Billy"
                            customer2.lastName = "Willy"
                            customer2.phoneNumber = "0497815224"
                            customer2.email = "billy.willy@hotmail.com"
                            customer2.password = "Password#69"
                            customer2.bedrijf_opleiding = "De la where"

                        val customer3 = Customer(
                            firstName = "Dikke",
                            lastName = "Tapir",
                            phoneNumber = "0497815223",
                            email = "tapir.dik@hotmail.com",
                            password = "Password#69",
                            bedrijf_opleiding = "De la where",
                        )

                        val customer4 = Customer(
                            firstName = "Jacky",
                            lastName = "Wacky",
                            phoneNumber = "0497815225",
                            email = "jacky.wacky@hotmail.com",
                            password = "Password#69",
                        bedrijf_opleiding = "De la where"
                        )

                        listOf(customer3, customer4).forEach {
                            Log.d("CUSTOMER_insert", it.toString())
                            Timber.d(String.format("Inserting customer: %s", it.toString()))
                            customerDao.insert(it)
                        }


                        val project1 = Project(name = "Project A", customer_id = customer1.id!!)
                        val project2 = Project(name = "Project B", customer_id = customer1.id!!)
                        val project3 = Project(name = "Project C", customer_id = customer1.id!!)
                        val project4 = Project(name = "Project D", customer_id = customer2.id!!)
                        val project5 = Project(name = "Project E", customer_id = customer3.id!!)

                        listOf(project1, project2, project3, project4, project5).forEach {
                            Timber.d(String.format("Inserting project: %s", it.toString()))
                            projectDao.insert(it)
                        }


                        val contract1 = Contract(
                            startDate = LocalDate.of(2022, 12, 11),
                            endDate = LocalDate.of(2023, 2, 1),
                            active = true
                        );
                        val contract2 = Contract(
                            startDate = LocalDate.of(2022, 10, 10),
                            endDate = LocalDate.of(2023, 12, 20),
                            active = true
                        );
                        val contract3 = Contract(
                            startDate = LocalDate.of(2022, 11, 11),
                            endDate = LocalDate.of(2023, 3, 3),
                            active = true
                        )
                        val contract4 = Contract(
                            startDate = LocalDate.of(2022, 11, 15),
                            endDate = LocalDate.of(2023, 3, 1),
                            active = true
                        )
                        val contract5 = Contract(
                            startDate = LocalDate.of(2022, 10, 11),
                            endDate = LocalDate.of(2023, 4, 1),
                            active = true
                        )
                        val contract6 = Contract(
                            startDate = LocalDate.of(2022, 11, 1),
                            endDate = LocalDate.of(2023, 8, 11),
                            active = true
                        )
                        val contract7 = Contract(
                            startDate = LocalDate.of(2022, 3, 11),
                            endDate = LocalDate.of(2023, 5, 11),
                            active = true
                        )
                        val contract8 = Contract(
                            startDate = LocalDate.of(2021, 5, 11),
                            endDate = LocalDate.of(2023, 11, 11),
                            active = true
                        )

                        listOf(
                            contract1,
                            contract2,
                            contract3,
                            contract4,
                            contract5,
                            contract6,
                            contract7,
                            contract8
                        ).forEach {
                            Timber.d(String.format("Inserting contract: %s", it.toString()))
                            contractDao.insert(it)
                        }



                        val vm1 = VirtualMachine(
                            name = "Willie's VM",
                            connection = Connection(
                                "MOC2-FQDN",
                                "25.236.117.11",
                                "MOC-USER1",
                                "DW2]]YmiPrvz34-dh5]g"
                            ),
                            status = VirtualMachineStatus.RUNNING,
                            operatingSystem = OperatingSystem.LINUX_KALI,
                            hardware = HardWare(32000, 50000, 3),
                            projectId = project2.id!!,
                            mode = VirtualMachineModus.IAAS,
                            contractId = contract1.id!!,
                            backup = Backup(BackupType.MAANDELIJKS, LocalDate.of(2022, 12, 1))
                        )

                        val vm2 = VirtualMachine(
                            name = "Mami's VM",
                            connection = Connection(
                                "MOC2-FQDN",
                                "25.236.117.12",
                                "MOC-USE2",
                                "DW2]]YmiPrvz34-dh5]g"
                            ),
                            status = VirtualMachineStatus.TERMINATED,
                            operatingSystem = OperatingSystem.WINDOWS_2016,
                            hardware = HardWare(32000, 50000, 3),
                            projectId = project2.id!!,
                            mode = VirtualMachineModus.PAAS,
                            contractId = contract2.id!!,
                            backup = Backup(BackupType.DAGELIJKS, LocalDate.of(2022, 12, 11))
                        )

                        val vm3 = VirtualMachine(
                            name = "Papi's VM",
                            connection = Connection(
                                "MOC6-FQDN",
                                "25.236.117.13",
                                "MOC-USER3",
                                "DW2]]YmiPrvz34-dh5]g"
                            ),
                            status = VirtualMachineStatus.GEREED,
                            operatingSystem = OperatingSystem.LINUX_KALI,
                            hardware = HardWare(32000, 50000, 3),
                            projectId = project5.id!!,
                            mode = VirtualMachineModus.PAAS,
                            contractId = contract3.id!!,
                            backup = Backup(BackupType.MAANDELIJKS, LocalDate.of(2022, 12, 11))
                        )
                        val vm4 = VirtualMachine(
                            name = "Hackerman's VM",
                            connection = Connection(
                                "MOC5-FQDN",
                                "25.236.117.14",
                                "MOC-USER4",
                                "DW2]]YmiPrvz34-dh5]g"
                            ),
                            status = VirtualMachineStatus.GEREED,
                            operatingSystem = OperatingSystem.LINUX_KALI,
                            hardware = HardWare(32000, 50000, 3),
                            projectId = project1.id!!,
                            mode = VirtualMachineModus.PAAS,
                            contractId = contract4.id!!,
                            backup = Backup(BackupType.DAGELIJKS, LocalDate.of(2022, 12, 1))
                        )
                        val vm5 = VirtualMachine(
                            name = "Mr Robot's VM",
                            connection = Connection(
                                "MOC5-FQDN",
                                "25.236.117.15",
                                "MOC-USER5",
                                "DW2]]YmiPrvz34-dh5]g"
                            ),
                            status = VirtualMachineStatus.RUNNING,
                            operatingSystem = OperatingSystem.LINUX_KALI,
                            hardware = HardWare(32000, 50000, 3),
                            projectId = project4.id!!,
                            mode = VirtualMachineModus.PAAS,
                            contractId = contract5.id!!,
                            backup = Backup(BackupType.DAGELIJKS, LocalDate.of(2022, 12, 27))
                        )
                        val vm6 = VirtualMachine(
                            name = "Willie Wonka's VM",
                            connection = Connection(
                                "MOC6-FQDN",
                                "25.236.117.16",
                                "MOC-USER6",
                                "DW2]]YmiPrvz34-dh5]g"
                            ),
                            status = VirtualMachineStatus.GEREED,
                            operatingSystem = OperatingSystem.LINUX_UBUNTU,
                            hardware = HardWare(32000, 50000, 3),
                            projectId = project5.id!!,
                            mode = VirtualMachineModus.PAAS,
                            contractId = contract6.id!!,
                            backup = Backup(BackupType.DAGELIJKS, LocalDate.of(2022, 12, 28))
                        )
                        val vm7 = VirtualMachine(
                            name = "Elmo's VM",
                            connection = Connection(
                                "MOC2-FQDN",
                                "25.236.117.17",
                                "MOC-USER7",
                                "DW2]]YmiPrvz34-dh5]g"
                            ),
                            status = VirtualMachineStatus.RUNNING,
                            operatingSystem = OperatingSystem.LINUX_KALI,
                            hardware = HardWare(32000, 50000, 3),
                            projectId = project3.id!!,
                            mode = VirtualMachineModus.PAAS,
                            contractId = contract7.id!!,
                            backup = Backup(BackupType.DAGELIJKS, LocalDate.of(2022, 12, 29))
                        )
                        val vm8 = VirtualMachine(
                            name = "Sesame Street's VM",
                            connection = Connection(
                                "MOC2-FQDN",
                                "25.236.117.18",
                                "MOC-USER8",
                                "DW2]]YmiPrvz34-dh5]g"
                            ),
                            status = VirtualMachineStatus.GEREED,
                            operatingSystem = OperatingSystem.LINUX_UBUNTU,
                            hardware = HardWare(32000, 50000, 3),
                            projectId = project1.id!!,
                            mode = VirtualMachineModus.PAAS,
                            contractId = contract8.id!!,
                            backup = Backup(BackupType.DAGELIJKS, LocalDate.of(2022, 12, 30))
                        )
                        mutableListOf(vm1, vm2, vm3, vm4, vm5, vm6, vm7, vm8).forEach {
                            Timber.d(String.format("Inserting vm: %s", it.toString()))
                            virtualMachineDao.insert(it)
                        }
                    }
                }
            }
        }
    }
}
