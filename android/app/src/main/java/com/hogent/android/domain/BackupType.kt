package com.hogent.android.domain

enum class BackupType() {
    GEEN,
    CUSTOM,
    DAILY,
    WEEKLY,
    MONTHLY;

    fun to_string(): String {
        return this.name.lowercase().replaceFirstChar { e -> e.uppercase() }
    }
}
