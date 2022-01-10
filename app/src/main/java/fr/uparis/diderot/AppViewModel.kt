package fr.uparis.diderot

import androidx.lifecycle.*
import fr.uparis.diderot.data.entity.Arronsage_En_saison
import fr.uparis.diderot.data.entity.Nutriment
import fr.uparis.diderot.data.entity.Watering_Plant
import kotlinx.coroutines.launch

class AppViewModel(private val appRepository: AppRepository) : ViewModel() {


    val allWatering_Plant: LiveData<List<Watering_Plant>> =
        appRepository.allWatering_Plant.asLiveData()
    var loadlistPartialPLant: LiveData<List<Watering_Plant>>? = null
    var getPlantDonne: LiveData<Watering_Plant>? = null
    var loadListArronsageEnSaisonForGivenPLant: LiveData<List<Arronsage_En_saison>>? = null
    var loadListNUtrimentForGivenPLant: LiveData<List<Nutriment>>? = null

    fun insertPlantWatering(plant: Watering_Plant) = viewModelScope.launch {
        appRepository.insertWaterPlant(plant)
    }

    fun inserNutrimentPLant(plant: Nutriment) = viewModelScope.launch {
        appRepository.insertNutriment(plant)
    }

    fun insertSaisonRassonage(plant: Arronsage_En_saison) = viewModelScope.launch {
        appRepository.insertArronsageSaison(plant)
    }
    fun updateWateringPlant(plant: Watering_Plant) = viewModelScope.launch {
        appRepository.updateWateringPlant(plant)
    }



    fun loadPartialPlant(nom: String) {
        loadlistPartialPLant = appRepository.getPartialPLants(nom).asLiveData()
    }

    fun getPlantDonne(id_plant: Int) {
        getPlantDonne = appRepository.getPlantDonne(id_plant).asLiveData()
    }

    fun loadNutrimentSaisonArronsageForGivenPLantId(id_plant: Int) {
        loadListArronsageEnSaisonForGivenPLant =
            appRepository.loadListArronsageEnSaisonForGivenPLantId(id_plant).asLiveData()
        loadListNUtrimentForGivenPLant =
            appRepository.loadListNUtrimentForGivenPLant(id_plant).asLiveData()
    }

    fun deleteNutriment(id: Int) = viewModelScope.launch {
        appRepository.deleteNutriment(id)
    }

    fun deleteArronsageSaison(id: Int) = viewModelScope.launch {
        appRepository.deleteArronsageSaison(id)
    }

    fun deleteNutrimentSelonReference(id: Int) = viewModelScope.launch {
        appRepository.deleteNutrimentSelonReference(id)
    }

    fun deleteArronsageSaisonSElonReference(id: Int) = viewModelScope.launch {
        appRepository.deleteArronsageSaisonSelonReference(id)
    }

    fun deleteWateringPlant(id: Int) = viewModelScope.launch {
        appRepository.deleteWateingPlant(id)
    }
}


class AppViewModelFactory(private val appRepository: AppRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppViewModel(appRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
