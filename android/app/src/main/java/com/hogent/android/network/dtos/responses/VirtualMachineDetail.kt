package com.hogent.android.network.dtos.responses

import com.hogent.android.data.entities.Backup
import com.hogent.android.data.entities.Connection
import com.hogent.android.data.entities.Contract
import com.hogent.android.domain.BackupType
import com.hogent.android.domain.HardWare
import com.hogent.android.domain.OperatingSystem
import com.hogent.android.domain.VirtualMachineModus
import com.squareup.moshi.JsonClass
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class VirtualMachineDetail(
    val id: Int,
    val name: String,
    val mode: VirtualMachineModus,
    val hardware: HardWare,
    val operatingSystem: OperatingSystem,
    val backUp: Backup,
    val contract: Contract?,
    val vmConnection: Connection? = null,
    val fysiekeServer: FysiekeServerIndex? = null

)

data class VirtualMachineDetailDao(
    val id: Long,
    val name: String,
    val mode: VirtualMachineModus,
    val memory: Int,
    val storage: Int,
    val amount_vCPU: Int,
    val operatingSystem: OperatingSystem,
    val contractId: Long?,
    val contractStart: LocalDate?,
    val contractEnd: LocalDate?,
    val contractVmId: Long?,
    val contractCustomerId: Long?,
    val type: BackupType?,
    val lastBackup: LocalDate?,
    val backupId: Long?,
    val conId: Long?,
    val fqdn: String?,
    val hostname: String?,
    val username: String?,
    val password: String?
)
