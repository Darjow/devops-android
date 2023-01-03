package com.hogent.android.database.entities

import androidx.room.*
import com.hogent.android.network.NullSafe


@NullSafe
@Entity(tableName = "customer_table")
data class Customer(
    val lastName: String,
    val firstName: String,
    val phoneNumber: String,
    val email: String,
    val password: String,
    var bedrijf_opleiding: String = Course.NONE.toString(),

    @Embedded
    var contactPs1: ContactDetails1? = null,
    @Embedded
    var contactPs2: ContactDetails2? = null,
    @PrimaryKey(autoGenerate = true) var id: Long = 0

)


data class ContactDetails1(
    var contact1_phone: String,
    var contact1_email: String,
    var contact1_firstname : String,
    var contact1_lastname: String
)
data class ContactDetails2(
    var contact2_phone: String,
    var contact2_email: String,
    var contact2_firstname : String,
    var contact2_lastname: String
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



