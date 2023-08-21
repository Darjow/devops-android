package com.hogent.android.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.hogent.android.data.entities.Backup
import com.hogent.android.data.entities.Connection
import com.hogent.android.data.entities.Contract
import com.hogent.android.data.entities.VirtualMachine
import com.hogent.android.domain.HardWare
import com.hogent.android.network.dtos.responses.VirtualMachineDetail
import com.hogent.android.network.dtos.responses.VirtualMachineDetailDao

@Dao
interface VirtualMachineDao {

    @Query(
        value = "SELECT V.id,V.name,V.mode,V.amount_vCPU,V.storage,V.memory, V.operatingSystem, " +
            "C.id as 'contractId', C.startDate as 'contractStart', C.endDate as 'contractEnd' ," +
            "C.vMId as 'contractVmId', C.customerId as 'contractCustomerId', " +
            "B.type, B.lastBackup, B.id as 'backupId', " +
            "con.id as 'conId',con.fqdn,con.hostname,con.username,con.passwordHash,con.password " +
            "FROM VirtualMachines V " +
            "LEFT JOIN VMContracts C ON V.contractId = c.id " +
            "LEFT JOIN BackUps B ON V.backUpId = B.id " +
            "LEFT JOIN Connections con ON V.connectionId = con.id " +
            "WHERE v.id = :key"
    )
    suspend fun getById(key: Long): VirtualMachineDetailDao

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createVM(vm: VirtualMachine)

    @Update
    suspend fun update(vm: VirtualMachine)


    @Transaction
    fun getByIdFromCache(cached: VirtualMachineDetailDao): VirtualMachineDetail {
        var connection : Connection? = null
        if(cached.conId != null){
            connection = Connection(
                cached.conId,
                cached.fqdn,
                cached.hostname,
                cached.username,
                cached.passwordHash,
                cached.password)
        }
        return VirtualMachineDetail(
            cached.id.toInt(),
            cached.name,
            cached.mode,
            HardWare(cached.memory, cached.storage, cached.amount_vCPU),
            cached.operatingSystem,
            Backup(cached.type!!, cached.lastBackup),
            Contract(
                cached.contractStart,
                cached.contractEnd,
                cached.contractVmId!!,
                cached.contractCustomerId!!,
                cached.contractId!!
            ),
            connection
        )
    }

}
