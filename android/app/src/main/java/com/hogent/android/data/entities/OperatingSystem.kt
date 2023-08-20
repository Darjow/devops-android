package com.hogent.android.data.entities
import androidx.core.text.isDigitsOnly

enum class OperatingSystem {
    WINDOWS_10,
    WINDOWS_SERVER2019,
    KALI_LINUX,
    UBUNTU_22_04,
    FEDORA_36,
    FEDORA_35;

    fun to_string(): String {
        val strings = this.name.split("_")
        var output = ""

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
        return output
    }
}
