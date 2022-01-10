package fr.uparis.diderot.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import fr.uparis.diderot.AppApplication
import fr.uparis.diderot.AppViewModel
import fr.uparis.diderot.AppViewModelFactory
import fr.uparis.diderot.adapter.Adapter_Affichage_Nutriment_Plant
import fr.uparis.diderot.adapter.Adapter_Affichage_Plant
import fr.uparis.diderot.adapter.Adapter_Affichage_Saison_Plant

import fr.uparis.diderot.databinding.FragmentDiplayGivenPlantBinding

class DisplayGivenPlantFragment():Fragment() {

    private var _binding: FragmentDiplayGivenPlantBinding? = null
    private  val binding get() = _binding!!
    private lateinit var  adapterAffichageNutrimentPlant : Adapter_Affichage_Nutriment_Plant
    private lateinit var  adapterAfficherSaison : Adapter_Affichage_Saison_Plant
    var listener:DisplayGivenPlantFragmentListener?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: AppViewModel by requireActivity().viewModels {
            AppViewModelFactory((activity?.application as AppApplication).repository)
        }

        viewModel.getPlantDonne?.observe(this,{plant ->
            binding.apply {
                imageView.setImageURI(plant.localUri)
                plantName.text = plant.nom_commun+" "+plant.nom_latin
                lastWatering.text = plant.last_Watering.toString()
                nextWatering.text = plant.next_Watering.toString()
            }

        })

        // Obervation Plant Watering
        viewModel.loadListNUtrimentForGivenPLant?.observe(this){ wateringPlant ->
            wateringPlant.let{adapterAffichageNutrimentPlant.submitList(it)}

        }

        activity?.let {
            viewModel.loadListArronsageEnSaisonForGivenPLant?.observe(it){ wateringPlant ->
                wateringPlant.let{adapterAfficherSaison.submitList(it)}
            }
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDiplayGivenPlantBinding.inflate(inflater, container, false)


        val viewModel: AppViewModel by requireActivity().viewModels {
            AppViewModelFactory((activity?.application as AppApplication).repository)
        }

        //Recycle
        adapterAffichageNutrimentPlant = Adapter_Affichage_Nutriment_Plant()
        adapterAffichageNutrimentPlant.listener = object :Adapter_Affichage_Nutriment_Plant.OnItemClickListener{
            override fun onItemClick(position: Int, id_nutriment: Int) {
                listener?.onClickButtonSupprimerNutriment(id_nutriment)
            }

        }
        adapterAfficherSaison = Adapter_Affichage_Saison_Plant()
        adapterAfficherSaison.listener = object :Adapter_Affichage_Saison_Plant.OnItemClickListener{
            override fun onItemClick(position: Int, id_saison: Int) {
                listener?.onClickButtonSupprimerSaison(id_saison)
            }

        }

        binding.ArronsageSaisonList.adapter = adapterAfficherSaison
        binding.NutrimentList.adapter = adapterAffichageNutrimentPlant



        Log.i("ContructionNutriment", "Contruction Butriment")
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface DisplayGivenPlantFragmentListener{
        fun onClickButtonSupprimerNutriment(id:Int)
        fun onClickButtonSupprimerSaison(id:Int)
    }

}