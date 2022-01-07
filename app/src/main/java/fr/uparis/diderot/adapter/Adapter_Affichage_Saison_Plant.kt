package fr.uparis.diderot.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fr.uparis.diderot.data.entity.Arronsage_En_saison
import fr.uparis.diderot.databinding.ListItemSaisonPlantBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class Adapter_Affichage_Saison_Plant(val listener: OnItemClickListener) :
    ListAdapter<Arronsage_En_saison, Adapter_Affichage_Saison_Plant.VH>(
        InfoComparator()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ListItemSaisonPlantBinding
            .inflate(
                LayoutInflater
                    .from(parent.context), parent, false
            )

        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val current = getItem(position)
        holder.IdEnsaison(current.id_saison)
        holder.FirthMonth(current.first_Month)
        holder.LastMonth(current.last_Month)
        holder.NumberTimes(current.number_Times)
        holder.PeriodNumberTimes(current.period_Number_Times)

    }

    inner class VH(val binding: ListItemSaisonPlantBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
            Log.i("MainActivity1", "onItemClick: rentre dans onItemCclick")
        }

        var idEn_saison: Int? = null
        var dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
        fun FirthMonth(month: Date?) {
            binding.FirthMonth.text = dateFormat.format(month)
        }

        fun LastMonth(month: Date?) {
            binding.lastMOnth.text = dateFormat.format(month)
        }

        fun NumberTimes(num: Int?) {
            binding.numberTimes.text = num.toString()
        }

        fun PeriodNumberTimes(num: Int?) {
            binding.PeriodNumberTimes.text = num.toString()
        }

        fun IdEnsaison(id: Int?) {
            idEn_saison = id
            binding.idSaison.text = id.toString()
        }


        override fun onClick(v: View?) {

            val position: Int = adapterPosition
            listener.onItemClick(position, idEn_saison!!)


        }
    }

    class InfoComparator : DiffUtil.ItemCallback<Arronsage_En_saison>() {
        override fun areItemsTheSame(
            oldItem: Arronsage_En_saison,
            newItem: Arronsage_En_saison
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: Arronsage_En_saison,
            newItem: Arronsage_En_saison
        ): Boolean {
            return oldItem.id_saison == newItem.id_saison
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, id_plant: Int)
    }
}