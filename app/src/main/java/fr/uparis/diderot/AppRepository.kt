package fr.uparis.diderot

import androidx.annotation.WorkerThread
import fr.uparis.diderot.data.dao.Watering_Nutriment_PlantDao
import fr.uparis.diderot.data.entity.Arronsage_En_saison
import fr.uparis.diderot.data.entity.Nutriment
import fr.uparis.diderot.data.entity.Watering_Plant
import kotlinx.coroutines.flow.Flow

/**
 *
 */
class AppRepository(private val wateringPlant: Watering_Nutriment_PlantDao)
{

    val allWatering_Plant : Flow<List<Watering_Plant>> = wateringPlant.getWateringPlant()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertWaterPlant(watering: Watering_Plant){
        wateringPlant.insert_watering_plant(watering)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertNutriment(nutriment: Nutriment){
        wateringPlant.insert_nutriment(nutriment)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertArronsageSaison(arronsageSaison: Arronsage_En_saison){
        wateringPlant.insert_arronsage_saison(arronsageSaison)
    }


    fun getPartialPLants(nom: String) : Flow<List<Watering_Plant>>{
        return wateringPlant.loadPartialPlant(nom)
    }

    fun getPlantDonne(id_plant:Int): Flow<Watering_Plant>{
         return wateringPlant.getPlantdonne(id_plant)
    }
}