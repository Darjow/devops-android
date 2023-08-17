package com.hogent.android.data.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VirtualMachine(
    val name : String,
    val connection : Connection? = null,
    val status : VirtualMachineStatus = VirtualMachineStatus.AANGEVRAAGD,
    @Json(name = "operatingsystem")
    val operatingSystem: OperatingSystem = OperatingSystem.WINDOWS_10,
    val hardware: HardWare,
    val projectId : Int,
    val mode : VirtualMachineModus = VirtualMachineModus.WAITING_APPROVEMENT,
    val contractId : Int,
    val backup : Backup,
    val id: Int = 0,
)

enum class VirtualMachineModus {
    WAITING_APPROVEMENT,       // No connection || No server
    READY,                     // has connection && server
    RUNNING,
    PAUSED,
    STOPPED;
    fun to_string(): String {
        return when (this) {
            WAITING_APPROVEMENT -> "Wachten op goedkeuring"
            READY -> "Idle"
            RUNNING -> "Online"
            PAUSED -> "Gepauzeerd"
            STOPPED -> "Gestopt"
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

