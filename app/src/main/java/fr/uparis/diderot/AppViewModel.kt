package fr.uparis.diderot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import fr.uparis.diderot.data.entity.Watering_Plant
import kotlinx.coroutines.launch

class AppViewModel(private val appRepository: AppRepository):ViewModel(){



    fun insertPlantWatering(plant: Watering_Plant) = viewModelScope.launch {
        appRepository.insertWaterPlant(plant)
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
