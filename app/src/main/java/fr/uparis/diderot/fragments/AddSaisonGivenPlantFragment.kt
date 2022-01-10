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
import fr.uparis.diderot.data.entity.Arronsage_En_saison
import fr.uparis.diderot.databinding.FragmentAddSaisonBinding
import java.lang.NumberFormatException
import java.text.DateFormat
import java.time.DateTimeException
import java.time.LocalDate
import kotlin.concurrent.thread

class AddSaisonGivenPlantFragment(): Fragment() {

    private var _binding: FragmentAddSaisonBinding? = null
    private  val binding get() = _binding!!
    private var listArronsage_En_saison : List<Arronsage_En_saison>?=null
    public  var listener: AddSaisonFragmentListener?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddSaisonBinding.inflate(inflater, container, false)


        val viewModel: AppViewModel by requireActivity().viewModels {
            AppViewModelFactory((activity?.application as AppApplication).repository)
        }

        binding.buttonSubmit.setOnClickListener{
            binding.apply {
                if(FirthMonth.text.isNotEmpty() && lastMOnth.text.isNotEmpty() && NumberTimes.text.isNotEmpty()
                    && PeriodNumberTimes.text.isNotEmpty()){

                    try {
                        val  FirthMonthConverti = LocalDate.parse(FirthMonth.text.toString())
                        val  lastMOnthConverti = LocalDate.parse(lastMOnth.text.toString())
                        if(FirthMonthConverti.isBefore(lastMOnthConverti)){

                            viewModel.insertSaisonRassonage(
                                Arronsage_En_saison(
                                   AppDisplayGivenPlant.Id_plantPartage,
                                    FirthMonthConverti,
                                    lastMOnthConverti,
                                    NumberTimes.text.toString().toInt(),
                                    PeriodNumberTimes.text.toString().toInt()
                                ))

                                Toast.makeText(
                                    activity,
                                    "Entregistrement reussi ",
                                    Toast.LENGTH_LONG
                                ).show()
                            videContenuEditText()
                            listener?.onClickSaisonButton()

                        }else  Toast.makeText(
                            activity,
                            "Impossible FirthMonth("+FirthMonthConverti.toString()+") is after lastMonth("+lastMOnthConverti.toString()+")",
                            Toast.LENGTH_LONG
                        ).show()


                    }catch (e : NumberFormatException){
                        Toast.makeText(
                            activity,
                            "Merci de respecte le format du champs",
                            Toast.LENGTH_LONG
                        ).show()
                    }catch (e: DateTimeException){
                        Toast.makeText(
                            activity,
                            "Merci de respecte le format du champs date",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }else Toast.makeText(activity, "Merci de saisir tout le champs", Toast.LENGTH_LONG).show()

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
        binding.FirthMonth.setText("")
        binding.lastMOnth.setText("")

    }
    interface AddSaisonFragmentListener{
        fun onClickSaisonButton()
    }



}