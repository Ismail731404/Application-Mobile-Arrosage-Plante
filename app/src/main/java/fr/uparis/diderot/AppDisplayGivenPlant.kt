package fr.uparis.diderot


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import fr.uparis.diderot.databinding.ActivityAppDisplayGivenPlantBinding
import java.text.DateFormat
import java.text.SimpleDateFormat

class AppDisplayGivenPlant : AppCompatActivity() {

    private lateinit var binding: ActivityAppDisplayGivenPlantBinding
    private val viewModel: AppViewModel by viewModels {
        AppViewModelFactory((application as AppApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppDisplayGivenPlantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //recuperation Plant
        var dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")

        intent.getStringExtra("id_plant")?.let { viewModel.getPlantDonne(it.toInt()) }
        Log.i("AppDisplay22", "onCreate: idplant ="+intent.getStringExtra("id_plant"))
        viewModel.getPlantDonne?.observe(this,{plant ->
             binding.apply {
                 imageView.setImageURI(plant.localUri)
                 plantName.text = plant.nom_commun+" "+plant.nom_latin
                 lastWatering.text = dateFormat.format(plant.last_Watering)
                 nextWatering.text = dateFormat.format(plant.next_Watering)
             }

        })



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.display_activity_main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.AjouteSaison -> {
              true
            }
            R.id.AjouteNutriment -> {
                true
            }
            R.id.Modifier ->{
                true
            }
            R.id.Supprimer ->{
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }

}