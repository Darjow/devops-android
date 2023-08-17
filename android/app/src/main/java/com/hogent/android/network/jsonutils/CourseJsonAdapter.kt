package com.hogent.android.network.jsonutils;

import com.hogent.android.data.entities.Course;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

class CourseJsonAdapter{

    @FromJson
    fun fromJson(value: Int): Course? {
        return when (value) {
            1 -> Course.TOEGEPASTE_INFORMATICA
            2 -> Course.AGRO_EN_BIOTECHNOLOGIE
            3 -> Course.BIOMEDISCHE_LABORATORIUMTECHNOLOGIE
            4 -> Course.CHEMIE
            5 -> Course.DIGITAL_DESIGN_AND_DEVELOPMENT
            6 -> Course.ELEKTROMECHANICA
        else -> Course.NONE
        }
    }

    @ToJson
    fun toJson(course: Course): Int {
        return course.ordinal
    }
}
