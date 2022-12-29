package com.hogent.android.database.entities

import androidx.room.*


@Entity(tableName = "customer_table")
data class Customer(
    @PrimaryKey(autoGenerate = true) val id: Long? = 0L,
    val lastName: String,
    val firstName: String,
    val phoneNumber: String,
    val email: String,
    val password: String,

    @Embedded
    var contactPs1: ContactDetails1? = null,
    @Embedded
    var contactPs2: ContactDetails2? = null,

    var bedrijf_opleiding: String = Course.NONE.toString()
)


data class ContactDetails1(
    val contact1_phone: String,
    var contact1_email: String,
    var contact1_firstname : String,
    val contact1_lastname: String
)
data class ContactDetails2(
    val contact2_phone: String,
    val contact2_email: String,
    val contact2_firstname : String,
    val contact2_lastname: String
)


enum class Course{
    NONE,
    TOEGEPASTE_INFORMATICA,
    AGRO_EN_BIOTECHNOLOGIE,
    BIOMEDISCHE_LABORATORIUMTECHNOLOGIE,
    CHEMIE,
    DIGITAL_DESIGN_AND_DEVELOPMENT,
    ELEKTROMECHANICA,
}


class CourseConverter{
    @TypeConverter
    public fun parseExtraProperty(obj: Course?): String{
        return if(obj == null){
            "";
        } else if(obj.toString().length == 1){
            obj.toString().uppercase()
        }else{
            val course =  obj.toString().lowercase();
            String.format("%s%s", course[0].uppercase(), course.substring(1))
        }
    }

}



