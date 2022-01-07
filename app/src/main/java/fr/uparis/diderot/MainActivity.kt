package fr.uparis.diderot

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import fr.uparis.diderot.databinding.ActivityMainBinding
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.LifecycleOwner
import fr.uparis.diderot.adapter.Adapter_Affichage_Plant
import fr.uparis.diderot.databinding.SearchBinding


class MainActivity : AppCompatActivity(), Adapter_Affichage_Plant.OnItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingSearch : SearchBinding
    private lateinit var  adapterAffichagePlant : Adapter_Affichage_Plant
    private var context : LifecycleOwner = this
    private lateinit var actionSearch : EditText
    private val viewModel: AppViewModel by viewModels {
        AppViewModelFactory((application as AppApplication).repository)
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            Toast.makeText(this,"Vous venez de revenir Activity principal", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        bindingSearch = SearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ajouter  Rearcher  icon
          addActionSearch()

        //Recycle
         adapterAffichagePlant = Adapter_Affichage_Plant(this)
         binding.PlantList.adapter = adapterAffichagePlant

        // Obervation Plant Watering
        viewModel.allWatering_Plant.observe(this){ wateringPlant ->
            wateringPlant.let{adapterAffichagePlant.submitList(it)}
        }

        //Recherche Query
          actionSearch = findViewById(R.id.search_query)
          actionsearch()
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
          return when (item.itemId){
              R.id.AjoutePlant->{
                  resultLauncher.launch(Intent(this,AddPlantActivity::class.java))
                  true
              }
              else -> super.onOptionsItemSelected(item)
          }
    }


    private fun actionsearch(){
        actionSearch.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                Log.i("editableliste ", s.toString())
                viewModel.loadPartialPlant(s.toString())
                viewModel.loadlistPartialPLant?.observe( context ,{plants ->
                    plants.let { adapterAffichagePlant.submitList(it) }
                })
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    fun addActionSearch(){
        val actionBar: ActionBar? = supportActionBar
       // actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowCustomEnabled(true)
        actionBar?.setDisplayShowTitleEnabled(false)
        actionBar?.setIcon(R.drawable.ic_baseline_search_24)
        //actionBar?.setBackgroundDrawable(ColorDrawable(Color.G))

        val inflator = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v: View = inflator.inflate(R.layout.search, null)
        actionBar?.customView = v
    }

    override fun onItemClick(position: Int, id_plant: Int) {

        Toast.makeText(this,"Item de $id_plant are clicker ",Toast.LENGTH_SHORT).show()
        var intent = Intent(this,AppDisplayGivenPlant::class.java)
        intent.putExtra("id_plant",id_plant.toString())
        resultLauncher.launch(intent)

    }


}