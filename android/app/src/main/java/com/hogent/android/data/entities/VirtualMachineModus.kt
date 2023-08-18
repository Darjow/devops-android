package com.hogent.android.data.entities

enum class VirtualMachineModus {
    WAITING_APPROVEMENT,       // No connection || No server
    READY,                     // has connection && server
    RUNNING,
    PAUSED,
    STOPPED;

    fun to_string(): String {
        return when (this) {
            WAITING_APPROVEMENT -> "Wachtend op goedkeuring"
            READY -> "Idle"
            RUNNING -> "Online"
            PAUSED -> "Gepauzeerd"
            STOPPED -> "Gestopt"
            else -> throw IllegalArgumentException("Unknown vm modus received");
        }
    }
}

