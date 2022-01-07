package fr.uparis.diderot.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fr.uparis.diderot.data.entity.Nutriment
import fr.uparis.diderot.databinding.ListItemNutrimentPlantBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class Adapter_Affichage_Nutriment_Plant(val listener: OnItemClickListener) :
    ListAdapter<Nutriment, Adapter_Affichage_Nutriment_Plant.VH>(
        InfoComparator()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ListItemNutrimentPlantBinding
            .inflate(
                LayoutInflater
                    .from(parent.context), parent, false
            )

        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val current = getItem(position)
        holder.NumberTimes(current.number_Times)
        holder.PerioNumberTimes(current.period_Number_Times)
        holder.DateDernierNutriment(current.last_Nutriment)
        holder.DateNextNutriment(current.next_Nutriment)
        holder.IdPNutriment(current.id_nutriment)


    }

    inner class VH(val binding: ListItemNutrimentPlantBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
            Log.i("MainActivity1", "onItemClick: rentre dans onItemCclick")
        }

        var id_nutriment: Int? = null
        var dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
        fun NumberTimes(num: Int?) {
            binding.numberTimes.text = num.toString()
        }

        fun PerioNumberTimes(num: Int?) {
            binding.PeriodNumberTimes.text = num.toString()
        }

        fun DateDernierNutriment(date: Date?) {
            binding.lastNutriment.text = dateFormat.format(date)
        }

        fun DateNextNutriment(date: Date?) {
            binding.NextNutriment.text = dateFormat.format(date)
        }

        fun IdPNutriment(id: Int?) {
            id_nutriment = id
            binding.idNutriment.text = id.toString()
        }


        override fun onClick(v: View?) {

            val position: Int = adapterPosition
            id_nutriment?.let { listener.onItemClick(position, it) }


        }
    }

    class InfoComparator : DiffUtil.ItemCallback<Nutriment>() {
        override fun areItemsTheSame(oldItem: Nutriment, newItem: Nutriment): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Nutriment, newItem: Nutriment): Boolean {
            return oldItem.id_nutriment == newItem.id_nutriment
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, id_plant: Int)
    }
}