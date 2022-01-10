package fr.uparis.diderot.adapter


import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fr.uparis.diderot.data.entity.Watering_Plant
import fr.uparis.diderot.databinding.ItemWateringPlantTodayBinding
import java.time.LocalDate

class Adapter_Affichage_Watering_Plant_toDay(val listener: OnItemClickListener) :
    ListAdapter<Watering_Plant, Adapter_Affichage_Watering_Plant_toDay.VH>(InfoComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemWateringPlantTodayBinding
            .inflate(
                LayoutInflater
                    .from(parent.context), parent, false
            )

        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val current = getItem(position)
        holder.NamePlant(current.nom_commun, current.nom_latin)
        holder.DateDernierAronsage(current.last_Watering)
        holder.DateNextWatering(current.next_Watering)
        holder.IdPlant(current.id_plant)
        holder.Image(current.localUri)
        holder.NumberTimes(current.number_Times)
        holder.PeriodNumberTimes(current.period_Number_Times)
    }


    inner class VH(val binding: ItemWateringPlantTodayBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.DatePicker.setOnClickListener(this)
        }

        var id_plant: Int? = null
        var nom_commun: String? = null
        var nom_latin: String? = null
        var image: Uri? = null
        var numberTimes: Int? = null
        var periodNumberTimes: Int? = null
        var lastWatering: LocalDate? = null

        fun NamePlant(nameCommun: String?, nameLatin: String?) {
            binding.plantName.text = nameCommun + " " + nameLatin
            nom_commun = nameCommun
            nom_latin = nameLatin
        }

        fun DateDernierAronsage(date: LocalDate?) {
            binding.lastWatering.text = date.toString()
            lastWatering = date
        }

        fun DateNextWatering(date: LocalDate?) {
            binding.nextWatering.text = date.toString()
        }

        fun Image(uri: Uri?) {
            binding.imageView.setImageURI(uri)
            image = uri
        }

        fun IdPlant(id: Int?) {
            id_plant = id
        }

        fun NumberTimes(Number: Int) {
            numberTimes = Number
        }

        fun PeriodNumberTimes(Number: Int) {
            periodNumberTimes = Number
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            listener.onItemClick(
                position,
                id_plant!!,
                nom_commun!!,
                nom_latin!!,
                image!!,
                lastWatering!!,
                numberTimes!!,
                periodNumberTimes!!
            )
        }

    }

    class InfoComparator : DiffUtil.ItemCallback<Watering_Plant>() {
        override fun areItemsTheSame(oldItem: Watering_Plant, newItem: Watering_Plant): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Watering_Plant, newItem: Watering_Plant): Boolean {
            return oldItem.id_plant == newItem.id_plant
        }
    }

    interface OnItemClickListener {
        fun onItemClick(
            position: Int,
            id_plant: Int,
            nom_commun: String,
            nom_latin: String,
            Image: Uri,
            lastWatering: LocalDate,
            numberTimes: Int,
            periodNumber: Int
        )
    }
}
