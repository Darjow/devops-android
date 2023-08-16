package com.hogent.android.data.entities
import androidx.core.text.isDigitsOnly

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
