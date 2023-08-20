package com.hogent.android.network.jsonutils

import com.hogent.android.data.entities.Course
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class CourseJsonAdapter {

    @FromJson
    fun fromJson(value: Int): Course? {
        return when (value) {
            1 -> Course.AGRO_EN_BIOTECHNOLOGIE
            2 -> Course.BIOMEDISCHE_LABORATORIUMTECHNOLOGIE
            3 -> Course.CHEMIE
            4 -> Course.DIGITAL_DESIGN_AND_DEVELOPMENT
            5 -> Course.ELEKTROMECHANICA
            6 -> Course.TOEGEPASTE_INFORMATICA
            else -> Course.NONE
        }
    }

    @ToJson
    fun toJson(course: Course): Int {
        return course.ordinal
    }
}
