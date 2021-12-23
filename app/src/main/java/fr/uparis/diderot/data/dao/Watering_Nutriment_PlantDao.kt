package fr.uparis.diderot.data.dao

import androidx.room.*
import fr.uparis.diderot.data.entity.*

/**
 *
 */
@Dao
interface Watering_Nutriment_PlantDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert_watering_plant(watering: Watering_Plant)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert_arronsage_saison(arronsage_saison: Arronsage_En_saison)


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert_nutriment(nutriment: Nutriment)

    @Transaction
    @Query("SELECT * FROM Watering_Plant WHERE id_plant = :id_plant")
    fun getWatering_Saison(id_plant: Int): List<Relation_Watering_Saison>

    @Transaction
    @Query("Select * from Watering_Plant")
    fun getWateringAndNutriment_Plant(): List<Watering_Plant_And_Nutriment>
}