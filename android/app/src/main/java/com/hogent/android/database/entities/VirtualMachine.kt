package com.hogent.android.database.entities
import androidx.core.text.isDigitsOnly
import androidx.room.*
import com.squareup.moshi.JsonClass
import org.json.JSONObject
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class VirtualMachine(
    val name : String,
    val connection : Connection? = null,
    val status : VirtualMachineStatus = VirtualMachineStatus.AANGEVRAAGD,
    val operatingSystem: OperatingSystem = OperatingSystem.NONE,
    val hardware: HardWare,
    val projectId : Long,
    val mode : VirtualMachineModus = VirtualMachineModus.NONE,
    val contractId : Long,
    val backup : Backup,
    val id: Long = 0,

)

enum class VirtualMachineModus {
    NONE,
    PAAS,
    IAAS;

    override fun toString(): String {
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

    override fun toString(): String {
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

        override fun toString(): String {
            return this.name.lowercase().replaceFirstChar { e -> e.uppercase() }
        }
    }

@JsonClass(generateAdapter = true)
    data class Backup(
        val type: BackupType?,
        val date: LocalDate?,
    )

    enum class BackupType() {
        DAGELIJKS,
        WEKELIJKS,
        MAANDELIJKS;

        override fun toString(): String {
            return this.name.lowercase().replaceFirstChar { e -> e.uppercase() }
        }
    }


    data class Connection(
        val fqdn: String,
        val ipAdres: String,
        val username: String,
        val password: String,
    )






