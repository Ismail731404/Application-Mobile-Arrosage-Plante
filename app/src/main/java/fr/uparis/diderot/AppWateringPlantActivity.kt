package fr.uparis.diderot

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import fr.uparis.diderot.adapter.Adapter_Affichage_Watering_Plant_toDay
import fr.uparis.diderot.data.entity.Watering_Plant
import fr.uparis.diderot.databinding.ActivityAppWateringPlantBinding
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class AppWateringPlantActivity : AppCompatActivity(),
    Adapter_Affichage_Watering_Plant_toDay.OnItemClickListener {


    private lateinit var binding: ActivityAppWateringPlantBinding
    private lateinit var adapterAffichageWateringPlant: Adapter_Affichage_Watering_Plant_toDay

    var cal = Calendar.getInstance()

    private val viewModel: AppViewModel by viewModels {
        AppViewModelFactory((application as AppApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppWateringPlantBinding.inflate(layoutInflater)
        setContentView(binding.root)


        Log.e("WateringPlantActivity", "passer")


        adapterAffichageWateringPlant = Adapter_Affichage_Watering_Plant_toDay(this)
        binding.wateringPlant.adapter = adapterAffichageWateringPlant


        val currentDay = LocalDate.now()
        Log.e("Date", "$currentDay")
        viewModel.getWateringPlant(currentDay)

        Log.e("WateringPlantActivity", "passer")

        viewModel.wateringPlantByDate?.observe(this) { plants ->
            plants.let { adapterAffichageWateringPlant.submitList(it) }
        }
    }

    override fun onItemClick(
        position: Int,
        id_plant: Int,
        nom_commun: String,
        nom_latin: String,
        Image: Uri,
        lastWatering: LocalDate,
        numberTimes: Int,
        periodNumber: Int
    ) {

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                UpdateDateWatering(
                    id_plant,
                    nom_commun,
                    nom_latin,
                    Image,
                    lastWatering,
                    numberTimes,
                    periodNumber
                )
            }
        }

        DatePickerDialog(
            this@AppWateringPlantActivity,
            dateSetListener,
            // mets à jour DatePickerDialog pour pointer sur la date d'aujourd'hui
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun UpdateDateWatering(
        id_plant: Int, nom_commun: String,
        nom_latin: String,
        Image: Uri,
        lastWatering: LocalDate,
        numberTimes: Int,
        periodNumber: Int
    ) {
        val localDate: LocalDate =
            LocalDateTime.ofInstant(cal.toInstant(), cal.timeZone.toZoneId())
                .toLocalDate()
        Log.e("LocalDate", "$localDate")

        viewModel.updateDate(
            Watering_Plant(
                id_plant,
                nom_commun,
                nom_latin,
                numberTimes,
                periodNumber, Image, lastWatering, localDate
            )
        )

    }


}