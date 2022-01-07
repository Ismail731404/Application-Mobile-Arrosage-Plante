package fr.uparis.diderot

import androidx.lifecycle.*
import fr.uparis.diderot.data.entity.Watering_Plant
import kotlinx.coroutines.launch

class AppViewModel(private val appRepository: AppRepository):ViewModel(){



    val allWatering_Plant: LiveData<List<Watering_Plant>> = appRepository.allWatering_Plant.asLiveData()
    var loadlistPartialPLant : LiveData<List<Watering_Plant>>? = null
    var getPlantDonne : LiveData<Watering_Plant>? = null
    fun insertPlantWatering(plant: Watering_Plant) = viewModelScope.launch {
        appRepository.insertWaterPlant(plant)
    }

    fun loadPartialPlant(nom:String) {
        loadlistPartialPLant = appRepository.getPartialPLants(nom).asLiveData()
    }
    fun getPlantDonne(id_plant:Int){
        getPlantDonne = appRepository.getPlantDonne(id_plant).asLiveData()
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
