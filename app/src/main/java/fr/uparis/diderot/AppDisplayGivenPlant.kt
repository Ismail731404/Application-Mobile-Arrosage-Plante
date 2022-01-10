package fr.uparis.diderot


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentTransaction
import fr.uparis.diderot.databinding.ActivityAppDisplayGivenPlantBinding
import fr.uparis.diderot.dialog.ConfirmeDialog
import fr.uparis.diderot.fragments.AddNutreimentGiventPlantFragment
import fr.uparis.diderot.fragments.AddSaisonGivenPlantFragment
import fr.uparis.diderot.fragments.DisplayGivenPlantFragment


class AppDisplayGivenPlant : AppCompatActivity(),
    AddNutreimentGiventPlantFragment.AddNutrimentFragmentListener,
    AddSaisonGivenPlantFragment.AddSaisonFragmentListener,
    DisplayGivenPlantFragment.DisplayGivenPlantFragmentListener {

    private lateinit var binding: ActivityAppDisplayGivenPlantBinding
    private val viewModel: AppViewModel by viewModels {
        AppViewModelFactory((application as AppApplication).repository)
    }

    private lateinit var fragmentDisplayGivenPlant: DisplayGivenPlantFragment
    private lateinit var addnutreimentfragment: AddNutreimentGiventPlantFragment
    private lateinit var addSaisonFragment: AddSaisonGivenPlantFragment
    private var Id_plant: Int = 0
    private var context: Context = this
    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                Toast.makeText(this, "AppDisplayGivenPlantActivity", Toast.LENGTH_LONG)
                    .show()
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppDisplayGivenPlantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //ActionBar
        addActionSearch()
        //Id_plant
        Id_plant = intent.getStringExtra("id_plant")!!.toInt()
        AppDisplayGivenPlant.Id_plantPartage = Id_plant
        var color = ResourcesCompat.getColor(resources, R.color.colorItem, null)
        AppDisplayGivenPlant.colorItem = color
        intent.getStringExtra("id_plant")?.let { viewModel.getPlantDonne(it.toInt()) }
        viewModel.loadNutrimentSaisonArronsageForGivenPLantId(Id_plant)


        //Instanciation de Fragment

        fragmentDisplayGivenPlant = DisplayGivenPlantFragment()
        fragmentDisplayGivenPlant.listener = this


        addnutreimentfragment = AddNutreimentGiventPlantFragment()
        addnutreimentfragment.listener = this

        addSaisonFragment = AddSaisonGivenPlantFragment()
        addSaisonFragment.listener = this

        //Initialise
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragmentDisplayGivenPlant)
            .commit()


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.display_activity_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.AjouteSaison -> {

                val Haut = LinearLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.FILL_PARENT,
                    0.75f
                )
                binding.containerBas.layoutParams = Haut
                val bas = LinearLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.FILL_PARENT,
                    0.25f
                )
                binding.container.layoutParams = bas
                supportFragmentManager.beginTransaction()
                    .replace(R.id.containerBas, addSaisonFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
                true
            }
            R.id.AjouteNutriment -> {

                val Haut = LinearLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.FILL_PARENT,
                    0.75f
                )
                binding.containerBas.layoutParams = Haut
                val bas = LinearLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.FILL_PARENT,
                    0.25f
                )
                binding.container.layoutParams = bas

                supportFragmentManager.beginTransaction()
                    .replace(R.id.containerBas, addnutreimentfragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
                true
            }
            R.id.Modifier -> {
                var intent = Intent(this, AppModifierPantActivity::class.java)
                intent.putExtra("id_plant", Id_plant.toString())
                resultLauncher.launch(intent)
                true
            }
            R.id.Supprimer -> {
                val dialog = ConfirmeDialog()


                dialog.listener = object : ConfirmeDialog.ConfirmeDialogListener {
                    override fun onDialogNegativeClick() {
                        Toast.makeText(context, "Supression Annuler", Toast.LENGTH_LONG).show()
                    }

                    override fun onDialogPOsitiveClick() {

                        //Suppression des tout les nutriment et arronsage du plant
                        viewModel.deleteArronsageSaisonSElonReference(Id_plant)
                        viewModel.deleteNutrimentSelonReference(Id_plant)
                        //Suppression du plant
                        viewModel.deleteWateringPlant(Id_plant)
                        //Supprimer Fragment
                        supportFragmentManager.beginTransaction()
                            .remove(fragmentDisplayGivenPlant)
                            .commit()
                        finish()

                    }
                }

                dialog.show(supportFragmentManager, "ConfirmdeletePlant")
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.beginTransaction()
            .remove(fragmentDisplayGivenPlant)


    }

    override fun onClickNutrimentButton() {

        val Haut = LinearLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.FILL_PARENT,
            1f
        )
        binding.containerBas.layoutParams = Haut
        val bas = LinearLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.FILL_PARENT,
            0f
        )
        binding.container.layoutParams = bas
        supportFragmentManager.beginTransaction()
            .remove(addnutreimentfragment)
            .commit()
    }

    override fun onClickSaisonButton() {
        val Haut = LinearLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.FILL_PARENT,
            1f
        )
        binding.containerBas.layoutParams = Haut
        val bas = LinearLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.FILL_PARENT,
            0f
        )
        binding.container.layoutParams = bas
        supportFragmentManager.beginTransaction()
            .remove(addSaisonFragment)
            .commit()

    }


    override fun onClickButtonSupprimerNutriment(id: Int) {
        viewModel.deleteNutriment(id)
    }

    override fun onClickButtonSupprimerSaison(id: Int) {
        viewModel.deleteArronsageSaison(id)
    }

    companion object {
        var Id_plantPartage: Int = 0
        var colorItem: Int = 0

    }

    fun addActionSearch() {
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)


    }



}