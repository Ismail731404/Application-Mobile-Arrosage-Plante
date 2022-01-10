package fr.uparis.diderot.data.dao

import androidx.room.*
import fr.uparis.diderot.data.entity.*
import kotlinx.coroutines.flow.Flow

/**
 *
 */
@Dao
interface Watering_Nutriment_PlantDao {


    @Query("SELECT * from watering_plant")
    fun getWateringPlant(): Flow<List<Watering_Plant>>

    @Query("SELECT * from Watering_Plant Info Where nom_commun like :nom || '%' or nom_latin like :nom || '%'")
    fun loadPartialPlant(nom : String): Flow<List<Watering_Plant>>

    @Query("SELECT * FROM Watering_Plant Where id_plant = :id_plant")
    fun getPlantdonne(id_plant: Int): Flow<Watering_Plant>

    @Query("SELECT * FROM Arronsage_En_Saison where id_plant_reference = :id_plant_reference")
    fun loadListArronsageEnSaisonForGivenPLant(id_plant_reference:Int):List<Arronsage_En_saison>

    @Query("SELECT * FROM Arronsage_En_Saison where id_plant_reference = :id_plant_reference")
    fun loadListArronsageEnSaisonForGivenPLantId(id_plant_reference:Int):Flow<List<Arronsage_En_saison>>

    @Query("SELECT * FROM Nutriment where id_plant_reference = :id_plant_reference")
    fun loadListNUtrimentForGivenPLant(id_plant_reference:Int):Flow<List<Nutriment>>

    @Query("Delete from Nutriment WHERE id_nutriment = :id")
    suspend fun deleteNutriment(id:Int)

    @Query("Delete from Arronsage_En_Saison WHERE id_saison = :id")
    suspend fun deleteArronsageSaison(id:Int)

    @Query("Delete from Watering_Plant WHERE id_plant = :id")
    suspend fun deleteWaterintPlant(id:Int)

    @Query("Delete from Nutriment WHERE id_plant_reference = :id")
    suspend fun deleteNutrimentSelonIdReference(id:Int)

    @Query("Delete from Arronsage_En_Saison WHERE id_plant_reference = :id")
    suspend fun deleteArronsageSaisonSelonRefence(id:Int)

   @Update
   suspend fun updateWateringPlant(watering: Watering_Plant)

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