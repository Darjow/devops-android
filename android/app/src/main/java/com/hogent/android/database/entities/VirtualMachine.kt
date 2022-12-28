package com.hogent.android.database.entities
import androidx.core.text.isDigitsOnly
import androidx.room.*
import org.json.JSONObject
import java.time.LocalDate


@Entity(tableName = "virtualmachine_table",
    foreignKeys = [ForeignKey(
        entity = Contract::class,
        childColumns = ["contractId"],
        parentColumns = ["id"]
    )])
data class VirtualMachine(
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0L,
    val name : String = "",
    val connection : Connection? = null ,
    val status : VirtualMachineStatus = VirtualMachineStatus.AANGEVRAAGD,
    val operatingSystem: OperatingSystem = OperatingSystem.NONE,
    val hardware: HardWare = HardWare(0,0,0),
    val project_id : Long = 0L,
    val mode : VirtualMachineModus = VirtualMachineModus.NONE,
    val contractId : Long = 0L,
    val backup : Backup = Backup(null, null),

)

enum class VirtualMachineModus {
    NONE,
    PAAS,
    SAAS;

    override fun toString(): String {
        return when (this.name) {
            "NONE" -> "Geen"
            "PAAS" -> "PaaS"
            "SAAS" -> "SaaS"
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

    /*override fun toString(): String {
        var strings = this.name.split("_").toMutableList()
        for((i, string) in strings.withIndex()){
            strings[i]= string.lowercase()
            strings[i] = string.replaceFirstChar { it.uppercase() }
        }
        return strings.joinToString(" ")
    }*/
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
        fun fromConnection(connection: Connection): String {
            return JSONObject().apply {
                put("fqdn", connection.fqdn)
                put("ipAdres", connection.ipAdres)
                put("username", connection.username)
                put("password", connection.password)
            }.toString();
        }

        @TypeConverter
        fun toConnection(json: String): Connection {
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

            return Backup(
                BackupType.valueOf(backupType.uppercase()),
                LocalDate.parse(backup.get("backupDate").toString())
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



