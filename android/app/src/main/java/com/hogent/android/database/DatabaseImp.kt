package com.hogent.android.database

import android.content.Context
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

@Database(entities = [VirtualMachine::class, Customer::class, Contract::class, Project::class ], version = 1, exportSchema = false)
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

                        val customer1 = Customer(
                            "Doe",
                            "John",
                            "0497815223",
                            "john.doe@hotmail.com",
                            "Password#69",
                            "De la where"
                        )
                        val customer2 = Customer(
                            "Billy",
                            "Willy",
                            "0497815224",
                            "billy.willy@hotmail.com",
                            "Password#69",
                            "De la where"
                        )
                        val customer3 = Customer(
                            "Doe",
                            "John",
                            "0497815223",
                            "john.doe@hotmail.com",
                            "Password#69",
                            "De la where",
                        )
                        val customer4 = Customer(
                            "Jacky",
                            "Wacky",
                            "0497815225",
                            "jacky.wacky@hotmail.com",
                            "Password#69",
                            "De la where"
                        )

                        listOf(customer1, customer2, customer3, customer4).forEach {
                            Timber.d(String.format("Inserting customer: %s", it.toString()))
                            customerDao.insert(it)
                        }


                        val project1 = Project("Project A", 1)
                        val project2 = Project("Project B",  1)
                        val project3 = Project("Project C", 1)
                        val project4 = Project("Project D",  2)
                        val project5 = Project("Project E",  3)

                        listOf(project1, project2, project3, project4, project5).forEach {
                            Timber.d(String.format("Inserting project: %s", it.toString()))
                            projectDao.insert(it)
                        }


                        val contract1 = Contract(
                            LocalDate.of(2022, 12, 11),
                            LocalDate.of(2023, 2, 1),
                            true
                        );
                        val contract2 = Contract(
                            LocalDate.of(2022, 10, 10),
                            LocalDate.of(2023, 12, 20),
                            true
                        );
                        val contract3 = Contract(
                            LocalDate.of(2022, 11, 11),
                            LocalDate.of(2023, 3, 3),
                            true
                        )
                        val contract4 = Contract(
                            LocalDate.of(2022, 11, 15),
                            LocalDate.of(2023, 3, 1),
                            true
                        )
                        val contract5 = Contract(
                            LocalDate.of(2022, 10, 11),
                            LocalDate.of(2023, 4, 1),
                            true
                        )
                        val contract6 = Contract(
                            LocalDate.of(2022, 11, 1),
                            LocalDate.of(2023, 8, 11),
                            true
                        )
                        val contract7 = Contract(
                            LocalDate.of(2022, 3, 11),
                            LocalDate.of(2023, 5, 11),
                            true
                        )
                        val contract8 = Contract(
                            LocalDate.of(2021, 5, 11),
                            LocalDate.of(2023, 11, 11),
                            true
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
                            "Willie's VM",
                            Connection(
                                "MOC2-FQDN",
                                "25.236.117.11",
                                "MOC-USER1",
                                "DW2]]YmiPrvz34-dh5]g"
                            ),
                            VirtualMachineStatus.RUNNING,
                            OperatingSystem.LINUX_KALI,
                            HardWare(32000, 50000, 3),
                            2,
                            VirtualMachineModus.IAAS,
                            1,
                            Backup(BackupType.MAANDELIJKS, LocalDate.of(2022, 12, 1))
                        )

                        val vm2 = VirtualMachine(
                            "Mami's VM",
                            Connection(
                                "MOC2-FQDN",
                                "25.236.117.12",
                                "MOC-USE2",
                                "DW2]]YmiPrvz34-dh5]g"
                            ),
                            VirtualMachineStatus.TERMINATED,
                            OperatingSystem.WINDOWS_2016,
                            HardWare(32000, 50000, 3),
                            2,
                            VirtualMachineModus.PAAS,
                            2,
                            Backup(BackupType.DAGELIJKS, LocalDate.of(2022, 12, 11))
                        )

                        val vm3 = VirtualMachine(
                            "Papi's VM",
                            Connection(
                                "MOC6-FQDN",
                                "25.236.117.13",
                                "MOC-USER3",
                                "DW2]]YmiPrvz34-dh5]g"
                            ),
                            VirtualMachineStatus.GEREED,
                            OperatingSystem.LINUX_KALI,
                            HardWare(32000, 50000, 3),
                            5,
                            VirtualMachineModus.PAAS,
                            3,
                            Backup(BackupType.MAANDELIJKS, LocalDate.of(2022, 12, 11))
                        )
                        val vm4 = VirtualMachine(
                            "Hackerman's VM",
                            Connection(
                                "MOC5-FQDN",
                                "25.236.117.14",
                                "MOC-USER4",
                                "DW2]]YmiPrvz34-dh5]g"
                            ),
                            VirtualMachineStatus.GEREED,
                            OperatingSystem.LINUX_KALI,
                            HardWare(32000, 50000, 3),
                            1,
                            VirtualMachineModus.PAAS,
                            4,
                            Backup(BackupType.DAGELIJKS, LocalDate.of(2022, 12, 1))
                        )
                        val vm5 = VirtualMachine(
                            "Mr Robot's VM",
                            Connection(
                                "MOC5-FQDN",
                                "25.236.117.15",
                                "MOC-USER5",
                                "DW2]]YmiPrvz34-dh5]g"
                            ),
                            VirtualMachineStatus.RUNNING,
                            OperatingSystem.LINUX_KALI,
                            HardWare(32000, 50000, 3),
                            4,
                            VirtualMachineModus.PAAS,
                            8,
                            Backup(BackupType.DAGELIJKS, LocalDate.of(2022, 12, 1))
                        )


                        val vm6 = VirtualMachine(
                            "Sam's VM",
                            Connection(
                                "MOC3-FQDN",
                                "25.236.117.16",
                                "MOC-USER6",
                                "DW2]]YmiPrvz34-dh5]g"
                            ),
                            VirtualMachineStatus.RUNNING,
                            OperatingSystem.LINUX_KALI,
                            HardWare(32000, 50000, 3),
                            3,
                            VirtualMachineModus.IAAS,
                            5,
                            Backup(BackupType.MAANDELIJKS, LocalDate.of(2022, 12, 1))
                        )

                        val vm7 = VirtualMachine(
                            "Sue's VM",
                            Connection(
                                "MOC4-FQDN",
                                "25.236.117.17",
                                "MOC-USER7",
                                "DW2]]YmiPrvz34-dh5]g"
                            ),
                            VirtualMachineStatus.TERMINATED,
                            OperatingSystem.WINDOWS_2016,
                            HardWare(32000, 50000, 3),
                            4,
                            VirtualMachineModus.PAAS,
                            6,
                            Backup(BackupType.DAGELIJKS, LocalDate.of(2022, 12, 11))
                        )

                        val vm8 = VirtualMachine(
                            "Tom's VM",
                            Connection(
                                "MOC5-FQDN",
                                "25.236.117.18",
                                "MOC-USER8",
                                "DW2]]YmiPrvz34-dh5]g"
                            ),
                            VirtualMachineStatus.GEREED,
                            OperatingSystem.LINUX_KALI,
                            HardWare(32000, 50000, 3),
                            5,
                            VirtualMachineModus.PAAS,
                            7,
                            Backup(BackupType.MAANDELIJKS, LocalDate.of(2022, 12, 11))
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
