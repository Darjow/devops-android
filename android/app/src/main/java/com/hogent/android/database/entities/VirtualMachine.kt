package com.hogent.android.database.entities
import androidx.core.text.isDigitsOnly
import androidx.room.*
import com.hogent.android.network.NullSafe
import org.json.JSONObject
import java.time.LocalDate


@NullSafe
@Entity(tableName = "virtualmachine_table",
    foreignKeys = [
        ForeignKey(entity = Contract::class, childColumns = ["contractId"], parentColumns = ["id"]),
        ForeignKey(entity = Project::class, childColumns = ["projectId"], parentColumns = ["id"],
        )])
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

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

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


    class ConnectionConverter {
        @TypeConverter
        fun fromConnection(connection: Connection?): String {
            if(connection == null){
                return "None";
            }
            return JSONObject().apply {
                put("fqdn", connection.fqdn)
                put("ipAdres", connection.ipAdres)
                put("username", connection.username)
                put("password", connection.password)
            }.toString();
        }

        @TypeConverter
        fun toConnection(json: String): Connection? {
            if(json == "None"){
                return null
            }
            val connection = JSONObject(json)
            return Connection(
                connection.get("fqdn") as String,
                connection.get("ipAdres") as String,
                connection.get("username") as String,
                connection.get("password") as String
            )
        }
    }

    class BackupConverter {
        @TypeConverter
        fun fromBackup(backup: Backup): String {
            return JSONObject().apply {
                put("type", backup.type)
                put("backupDate", backup.date)
            }.toString();
        }

        @TypeConverter
        fun toBackup(json: String): Backup {
            val backup = JSONObject(json)
            val backupType = backup.get("type").toString()
            val backupDate = backup.opt("backupDate")?.toString()


            return Backup(
                BackupType.valueOf(backupType.uppercase()),
                backupDate?.let{ LocalDate.parse(it)}
            )
        }
    }

    class HardwareConverter {
        @TypeConverter
        fun fromHardware(hardware: HardWare): String {
            return JSONObject().apply {
                put("memory", hardware.memory)
                put("storage", hardware.storage)
                put("cpu", hardware.cpu)
            }.toString();
        }

        @TypeConverter
        fun toHardware(json: String): HardWare {
            val hardware = JSONObject(json)
            return HardWare(
                hardware.get("memory") as Int,
                hardware.get("storage") as Int,
                hardware.get("cpu") as Int
            )
        }

    }



