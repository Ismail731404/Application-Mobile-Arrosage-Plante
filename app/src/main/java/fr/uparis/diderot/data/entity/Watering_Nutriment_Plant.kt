package fr.uparis.diderot.data.entity


import android.net.Uri
import androidx.room.*
import java.time.LocalDate


/**
 * 
 */
@Entity(tableName = "Watering_Plant")
data class Watering_Plant(@PrimaryKey(autoGenerate = true) var id_plant: Int?,
                          var nom_commun: String? = null,
                          var nom_latin: String? = null,
                          var number_Times: Int,
                          var period_Number_Times: Int,
                          var localUri: Uri? =null,
                          var last_Watering: LocalDate? = null,
                          var next_Watering: LocalDate? = null
){
    @Ignore
    constructor(
        nom_commun: String? = null,
         nom_latin: String? = null,
         number_Times: Int,
         period_Number_Times: Int,
         localUri : Uri? =null,
         last_Watering: LocalDate? = null,
         next_Watering: LocalDate? = null
    ) : this (null, nom_commun, nom_latin,number_Times,period_Number_Times,localUri,last_Watering,next_Watering)
}

/**
 *
 */
@Entity(tableName = "Nutriment")
data class Nutriment(@PrimaryKey(autoGenerate = true) var id_nutriment: Int,
                     var id_plant_reference:Int,
                     var number_Times: Int? = null,
                     var period_Number_Times: Int? = null,
                     var last_Nutriment: LocalDate? = null,
                     var next_Nutriment: LocalDate? = null
)



/**
 *
 */
@Entity(tableName = "Arronsage_En_Saison")
data class Arronsage_En_saison(@PrimaryKey(autoGenerate = true) var id_saison: Int? = null,
                               var id_plant_reference: Int,
                               var first_Month: LocalDate? = null,
                               var last_Month: LocalDate? = null,
                               var number_Times: Int? = null,
                               var period_Number_Times: Int? = null)




//One to Many Relationship DB
/**
 *
 */
data class Relation_Watering_Saison(
    @Embedded val watering_plant : Watering_Plant,
    @Relation(
        parentColumn = "id_plant",
        entityColumn = "id_plant_reference"
    )
    var arronsage_En_saison:  List<Arronsage_En_saison>

)

//One to One
/**
 *
 */
data class Watering_Plant_And_Nutriment(
    @Embedded val watering_plant : Watering_Plant,
    @Relation(
        parentColumn = "id_plant",
        entityColumn = "id_plant_reference"
    )
    var nutriment: Nutriment
)



//Conversion TYPE DATE
class Converters {
    @TypeConverter
    fun fromTimestamp(value: String?): LocalDate? {
        return LocalDate.parse(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): String? {
        return date?.toString()
    }

    @TypeConverter
    fun fromString(value: String?): Uri? {
        return Uri.parse(value)
    }

    @TypeConverter
    fun uriToString(uri: Uri?): String? {
        return  uri?.toString()
    }
}
