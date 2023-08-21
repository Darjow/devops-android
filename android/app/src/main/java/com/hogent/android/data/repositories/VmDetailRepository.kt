package com.hogent.android.data.repositories

import com.hogent.android.data.daos.BackupDao
import com.hogent.android.data.daos.ConnectionDao
import com.hogent.android.data.daos.VirtualMachineDao
import com.hogent.android.data.entities.Backup
import com.hogent.android.domain.HardWare
import com.hogent.android.network.dtos.responses.VirtualMachineDetail
import com.hogent.android.network.services.VirtualMachineApi.vmApi
import com.hogent.android.util.TimberUtils

class VmDetailRepository(
    private val vmDao: VirtualMachineDao,
    private val backUpDao: BackupDao,
    private val connectionDao: ConnectionDao,
    private val vm_id: Int
) {

    suspend fun getVMById(): VirtualMachineDetail? {
        val response = vmApi.getById(vm_id)
        val cached = vmDao.getById(vm_id.toLong())
        TimberUtils.logRequest(response)

        if (!response.isSuccessful) {
            if (cached == null) {
                return null
            }
            return null
        }

        return if (cached != null) {
            vmDao.getByIdFromCache(cached)
        } else {
            setByIdInCache(response.body()!!)
        }
    }
    private suspend fun setByIdInCache(responseBody: VirtualMachineDetail): VirtualMachineDetail {
        if (responseBody?.vmConnection != null) {
            connectionDao.create(responseBody.vmConnection)
        }
        val backup = Backup(
            responseBody!!.backUp.type,
            responseBody.backUp.lastBackup,
            responseBody.backUp.id
        )

        backUpDao.create(backup)

        return VirtualMachineDetail(
            responseBody!!.id,
            responseBody.name,
            responseBody.mode,
            HardWare(
                responseBody.hardware.memory,
                responseBody.hardware.storage,
                responseBody.hardware.amount_vCPU
            ),
            responseBody.operatingSystem,
            backup,
            responseBody.contract,
            responseBody.vmConnection
        )
    }
}
