package com.hogent.android.data.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VirtualMachine(
    val name : String,
    val connection : Connection? = null,
    val status : VirtualMachineStatus = VirtualMachineStatus.AANGEVRAAGD,
    @Json(name = "operatingsystem")
    val operatingSystem: OperatingSystem = OperatingSystem.NONE,
    val hardware: HardWare,
    val projectId : Int,
    val mode : VirtualMachineModus = VirtualMachineModus.NONE,
    val contractId : Int,
    val backup : Backup,
    val id: Int = 0,
)

enum class VirtualMachineModus {
    NONE,
    PAAS,
    IAAS;

    fun to_string(): String {
        return when (this.name) {
            "NONE" -> "Geen"
            "PAAS" -> "PaaS"
            "IAAS" -> "IaaS"
            else -> throw IllegalArgumentException("Unknown vm modus received");
        }
    }
}

enum class VirtualMachineStatus {
    NONE,
    GEREED,
    RUNNING,
    TERMINATED,
    AANGEVRAAGD;

    fun to_string(): String {
        return this.name.lowercase().replaceFirstChar { e -> e.uppercase() }
    }
}

