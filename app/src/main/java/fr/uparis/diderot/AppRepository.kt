package fr.uparis.diderot

import android.util.Log
import androidx.annotation.WorkerThread
import fr.uparis.diderot.data.dao.Watering_Nutriment_PlantDao
import fr.uparis.diderot.data.entity.Arronsage_En_saison
import fr.uparis.diderot.data.entity.Nutriment
import fr.uparis.diderot.data.entity.Watering_Plant
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

/**
 *
 */
class AppRepository(private val wateringPlant: Watering_Nutriment_PlantDao) {

    val allWatering_Plant: Flow<List<Watering_Plant>> = wateringPlant.getWateringPlant()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertWaterPlant(watering: Watering_Plant) {
        wateringPlant.insert_watering_plant(watering)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertNutriment(nutriment: Nutriment) {
        wateringPlant.insert_nutriment(nutriment)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertArronsageSaison(arronsageSaison: Arronsage_En_saison) {
        wateringPlant.insert_arronsage_saison(arronsageSaison)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateWateringPlant(plant: Watering_Plant) {
        wateringPlant.updateWateringPlant(plant)
    }

    fun getPartialPLants(nom: String): Flow<List<Watering_Plant>> {
        return wateringPlant.loadPartialPlant(nom)
    }

    fun getPlantDonne(id_plant: Int): Flow<Watering_Plant> {
        return wateringPlant.getPlantdonne(id_plant)
    }


    fun loadListArronsageEnSaisonForGivenPLantId(id_reference: Int): Flow<List<Arronsage_En_saison>> {
        return wateringPlant.loadListArronsageEnSaisonForGivenPLantId(id_reference)
    }

    fun loadListNUtrimentForGivenPLant(id_reference: Int): Flow<List<Nutriment>> {
        return wateringPlant.loadListNUtrimentForGivenPLant(id_reference)
    }

    suspend fun deleteNutriment(id: Int) {
        wateringPlant.deleteNutriment(id)
    }

    suspend fun deleteArronsageSaison(id: Int) {
        wateringPlant.deleteArronsageSaison(id)
    }

    suspend fun deleteNutrimentSelonReference(id: Int) {
        wateringPlant.deleteNutrimentSelonIdReference(id)
    }

    suspend fun deleteArronsageSaisonSelonReference(id: Int) {
        wateringPlant.deleteArronsageSaisonSelonRefence(id)
    }

    suspend fun deleteWateingPlant(id: Int) {
        wateringPlant.deleteWaterintPlant(id)
    }



    fun getWateringPlant(CurrentDate : LocalDate) : Flow<List<Watering_Plant>>{
        Log.e("getWatering","bien rentré")
        return wateringPlant.getWateringPlant(CurrentDate)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateDate(plant: Watering_Plant){
        wateringPlant.updateDate(plant)
    }


}