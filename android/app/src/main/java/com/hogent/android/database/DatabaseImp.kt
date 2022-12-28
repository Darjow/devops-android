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
import java.time.LocalDate

@Database(entities = [VirtualMachine::class, Customer::class, Contract::class, Project::class ], version = 12, exportSchema = false)
@TypeConverters(CourseConverter::class, HardwareConverter::class, BackupConverter::class, ConnectionConverter::class, LocalDateConverter::class /*OperatingSystemConverter::class*/)
abstract class DatabaseImp() : RoomDatabase() {

    abstract val customerDao: CustomerDao
    abstract val virtualMachineDao: VirtualMachineDao
    abstract val projectDao : ProjectDao
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

                        customerDao.insert(
                            Customer(
                                1,
                                "Doe",
                                "John",
                                "0497815223",
                                "john.doe@hotmail.com",
                                "Password#69",
                                null,
                                null,
                                "De la where",
                            )
                        )
                        customerDao.insert(
                            Customer(
                                2,
                                "Billy",
                                "Willy",
                                "0497815224",
                                "billy.willy@hotmail.com",
                                "Password#69",
                                null,
                                null,
                                "De la where",
                            )
                        )
                        customerDao.insert(
                            Customer(
                                3,
                                "Jacky",
                                "Wacky",
                                "0497815225",
                                "jacky.wacky@hotmail.com",
                                "Password#69",
                                null,
                                null,
                                "De la where",
                            )
                        )
                        projectDao.insert(
                            Project(1, "Project A", 1L)
                        )
                        projectDao.insert(
                            Project(2, "Project B", 1L)
                        )
                        projectDao.insert(
                            Project(3, "Project C", 1L)
                        )
                        projectDao.insert(
                            Project(4, "Project D", 2L)
                        )
                        projectDao.insert(
                            Project(5, "Project E", 3L)
                        )
                        contractDao.insert(
                            Contract(
                                1,
                                LocalDate.of(2022, 12,11),
                                LocalDate.of(2023, 2,1),

                            )
                        )
                        contractDao.insert(
                            Contract(
                                2,
                                LocalDate.of(2022, 10,10),
                                LocalDate.of(2023, 12,20),
                            )
                        )
                        contractDao.insert(
                            Contract(
                                3,
                                LocalDate.of(2022, 11,11),
                                LocalDate.of(2023,3,3)
                            )
                        )
                        contractDao.insert(
                            Contract(
                                4,
                                LocalDate.of(2022, 11,15),
                                LocalDate.of(2023, 3,1),
                            )
                        )
                        contractDao.insert(
                            Contract(
                                5,
                                LocalDate.of(2022, 10,11),
                                LocalDate.of(2023, 4,1),
                            )
                        )
                        contractDao.insert(
                            Contract(
                                6,
                                LocalDate.of(2022, 11,1),
                                LocalDate.of(2023, 8,11),
                            )
                        )
                        contractDao.insert(
                            Contract(
                                7,
                                LocalDate.of(2022, 3,11),
                                LocalDate.of(2023, 5,11),
                            )
                        )
                        contractDao.insert(
                            Contract(
                                8,
                                LocalDate.of(2021, 5,11),
                                LocalDate.of(2023, 11,11),
                            )
                        )


                        virtualMachineDao.insert(
                            VirtualMachine(
                                1,
                                "Willie's VM",
                                Connection("MOC2-FQDN", "25.236.117.11", "MOC-USER1", "DW2]]YmiPrvz34-dh5]g"),
                                VirtualMachineStatus.RUNNING,
                                OperatingSystem.LINUX_KALI,
                                HardWare(32000, 50000, 3),
                                2,
                                VirtualMachineModus.SAAS,
                                1,
                                Backup(BackupType.MAANDELIJKS, LocalDate.of(2022,12,1))
                            )
                        )
                        virtualMachineDao.insert(
                            VirtualMachine(
                                2,
                                "Mami's VM",
                                Connection("MOC2-FQDN", "25.236.117.12", "MOC-USE2", "DW2]]YmiPrvz34-dh5]g"),
                                VirtualMachineStatus.TERMINATED,
                                OperatingSystem.WINDOWS_2016,
                                HardWare(32000, 50000, 3),
                                2,
                                VirtualMachineModus.PAAS,
                                2,
                                Backup(BackupType.DAGELIJKS, LocalDate.of(2022,12,11))
                            )
                        )
                        virtualMachineDao.insert(
                            VirtualMachine(
                                3,
                                "Papi's VM",
                                Connection("MOC6-FQDN", "25.236.117.13", "MOC-USER3", "DW2]]YmiPrvz34-dh5]g"),
                                VirtualMachineStatus.GEREED,
                                OperatingSystem.LINUX_KALI,
                                HardWare(32000, 50000, 3),
                                5,
                                VirtualMachineModus.PAAS,
                                3,
                                Backup(BackupType.MAANDELIJKS, LocalDate.of(2022,12,11))
                            )
                        )
                        virtualMachineDao.insert(
                            VirtualMachine(
                                4,
                                "Hackerman's VM",
                                Connection("MOC5-FQDN", "25.236.117.14", "MOC-USER4", "DW2]]YmiPrvz34-dh5]g"),
                                VirtualMachineStatus.GEREED,
                                OperatingSystem.LINUX_KALI,
                                HardWare(32000, 50000, 3),
                                1,
                                VirtualMachineModus.PAAS,
                                4,
                                Backup(BackupType.DAGELIJKS, LocalDate.of(2022,12,1))
                            )
                        )
                        virtualMachineDao.insert(
                            VirtualMachine(
                                5,
                                "Mr Robot's VM",
                                Connection("MOC5-FQDN", "25.236.117.15", "MOC-USER5", "DW2]]YmiPrvz34-dh5]g"),
                                VirtualMachineStatus.RUNNING,
                                OperatingSystem.LINUX_KALI,
                                HardWare(32000, 50000, 3),
                                4,
                                VirtualMachineModus.PAAS,
                                5,
                                Backup(BackupType.DAGELIJKS, LocalDate.of(2022,12,27))
                            )
                        )
                        virtualMachineDao.insert(
                            VirtualMachine(
                                6,
                                "Willie Wonka's VM",
                                Connection("MOC6-FQDN", "25.236.117.16", "MOC-USER6", "DW2]]YmiPrvz34-dh5]g"),
                                VirtualMachineStatus.GEREED,
                                OperatingSystem.LINUX_UBUNTU,
                                HardWare(32000, 50000, 3),
                                5,
                                VirtualMachineModus.PAAS,
                                6,
                                Backup(BackupType.MAANDELIJKS, LocalDate.of(2022,12,11))
                            )
                        )
                        virtualMachineDao.insert(
                                VirtualMachine(
                                    7,
                                    "Dilly Billie's VM",
                                    Connection("MOC7-FQDN", "25.236.117.17", "MOC-USER7", "DW2]]YmiPrvz34-dh5]g"),
                                    VirtualMachineStatus.GEREED,
                                    OperatingSystem.LINUX_KALI,
                                    HardWare(32000, 50000, 3),
                                    4,
                                    VirtualMachineModus.PAAS,
                                    7,
                                    Backup(BackupType.MAANDELIJKS, LocalDate.of(2022,12,11))
                                ))
                        virtualMachineDao.insert(
                            VirtualMachine(
                                8,
                                "Wikky bickie's VM",
                                Connection("MOC7-FQDN", "25.236.117.30", "MOC-USER8", "DW2]]YmiPrvz34-dh5]g"),
                                VirtualMachineStatus.GEREED,
                                OperatingSystem.LINUX_KALI,
                                HardWare(32000, 50000, 3),
                                3,
                                VirtualMachineModus.PAAS,
                                8,
                                Backup(BackupType.MAANDELIJKS, LocalDate.of(2022,12,1))
                            ))
                    }
                }
            }
        }
    }
}

