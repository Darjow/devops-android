package com.hogent.android.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hogent.android.data.daos.BackupDao
import com.hogent.android.data.daos.ConnectionDao
import com.hogent.android.data.daos.ContactDetailsDao
import com.hogent.android.data.daos.ContractDao
import com.hogent.android.data.daos.CustomerDao
import com.hogent.android.data.daos.ProjectDao
import com.hogent.android.data.daos.VirtualMachineDao
import com.hogent.android.data.database.Converters.BackupConverter
import com.hogent.android.data.database.Converters.LocalDateConverter
import com.hogent.android.data.entities.Backup
import com.hogent.android.data.entities.Connection
import com.hogent.android.data.entities.ContactDetails
import com.hogent.android.data.entities.Contract
import com.hogent.android.data.entities.Project
import com.hogent.android.data.entities.User
import com.hogent.android.data.entities.VirtualMachine

@Database(
    entities = [
        Backup::class,
        Connection::class,
        ContactDetails::class,
        VirtualMachine::class,
        User::class,
        Contract::class,
        Project::class
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(value = [BackupConverter::class, LocalDateConverter::class])
abstract class RoomDB() : RoomDatabase() {

    abstract val backupDao: BackupDao
    abstract val contactDetailsDao: ContactDetailsDao
    abstract val contractDao: ContractDao
    abstract val customerDao: CustomerDao
    abstract val projectDao: ProjectDao
    abstract val virtualMachineDao: VirtualMachineDao
    abstract val connectionDao: ConnectionDao

    companion object {
        @Volatile
        private var INSTANCE: RoomDB? = null

        fun getInstance(context: Context): RoomDB =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context): RoomDB =
            Room.databaseBuilder(
                context.applicationContext,
                RoomDB::class.java,
                "android-devOps"
            )
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
    }
}
