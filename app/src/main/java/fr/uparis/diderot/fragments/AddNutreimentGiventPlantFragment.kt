package fr.uparis.diderot.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import fr.uparis.diderot.AppApplication
import fr.uparis.diderot.AppDisplayGivenPlant
import fr.uparis.diderot.AppViewModel
import fr.uparis.diderot.AppViewModelFactory
import fr.uparis.diderot.data.entity.Nutriment
import fr.uparis.diderot.databinding.FragmentAddNutrimentBinding
import java.lang.NumberFormatException
import java.time.LocalDate


class AddNutreimentGiventPlantFragment(): Fragment() {

    private var _binding: FragmentAddNutrimentBinding? = null
    private  val binding get() = _binding!!

    var listener: AddNutrimentFragmentListener?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddNutrimentBinding.inflate(inflater, container, false)


        val viewModel: AppViewModel by requireActivity().viewModels {
            AppViewModelFactory((activity?.application as AppApplication).repository)
        }

        binding.buttonSubmit.setOnClickListener{
            binding.apply {
                if(NumberTimes.text.isNotEmpty()&& PeriodNumberTimes.text.isNotEmpty()){
                    try {
                        var date = LocalDate.now()
                        var decalage =  (PeriodNumberTimes.text.toString().toInt() / NumberTimes.text.toString().toInt()).toInt().toLong()
                        var nextNutrimentDate = date.plusDays(decalage)

                        viewModel.inserNutrimentPLant(
                            Nutriment(AppDisplayGivenPlant.Id_plantPartage,
                                NumberTimes.text.toString().toInt(),
                                PeriodNumberTimes.text.toString().toInt(),
                                LocalDate.now(),
                                nextNutrimentDate
                            ))

                        Toast.makeText(
                            activity,
                            "Entregistrement reussi ",
                            Toast.LENGTH_LONG
                        ).show()
                        videContenuEditText()
                        listener?.onClickNutrimentButton()
                    }catch (e:NumberFormatException){
                        Toast.makeText(
                            activity,
                            "Merci de respecte le format du champs",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }else  Toast.makeText(activity, "Merci de saisir tout le champs", Toast.LENGTH_LONG).show()
            }
        }




        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun videContenuEditText(){
        binding.NumberTimes.setText("")
        binding.PeriodNumberTimes.setText("")

    }

    interface AddNutrimentFragmentListener{
        fun onClickNutrimentButton()
    }



}