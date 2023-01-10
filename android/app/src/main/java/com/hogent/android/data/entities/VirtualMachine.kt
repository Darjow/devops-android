package com.hogent.android.data.entities
import androidx.core.text.isDigitsOnly
import com.squareup.moshi.JsonClass
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class VirtualMachine(
    val name : String,
    val connection : Connection? = null,
    val status : VirtualMachineStatus = VirtualMachineStatus.AANGEVRAAGD,
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


data class HardWare(
    val memory: Int,
    val storage: Int,
    val cpu: Int
)

enum class OperatingSystem {
    NONE,
    WINDOWS_2012,
    WINDOWS_2016,
    WINDOWS_2019,
    LINUX_UBUNTU,
    LINUX_KALI,
    RASPBERRY_PI;

    fun to_string(): String {
        val strings = this.name.split("_")
        var output = "";

        for ((i, string) in strings.withIndex()) {

            if (i != 0) {
                output += " "
            }
            if (string.isDigitsOnly()) {
                output += string
            } else if (!string[0].isDigit()) {
                output += string.lowercase().replaceRange(0..0, string[0].uppercase())
            } else {
                output += string.lowercase()
            }
        }
        return output;
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

@JsonClass(generateAdapter = true)
    data class Backup(
        val type: BackupType?,
        val date: LocalDate?,
    )

    enum class BackupType() {
        GEEN,
        DAGELIJKS,
        WEKELIJKS,
        MAANDELIJKS;

        fun to_string(): String {
            return this.name.lowercase().replaceFirstChar { e -> e.uppercase() }
        }
    }


    data class Connection(
        val fqdn: String,
        val ipAdres: String,
        val username: String,
        val password: String,
    )






