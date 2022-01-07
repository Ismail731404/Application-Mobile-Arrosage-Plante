package fr.uparis.diderot.adapter


import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fr.uparis.diderot.data.entity.Watering_Plant
import fr.uparis.diderot.databinding.ListItemWateringplantBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class Adapter_Affichage_Plant(val listener: OnItemClickListener) : ListAdapter<Watering_Plant, Adapter_Affichage_Plant.VH>(
    InfoComparator()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ListItemWateringplantBinding
            .inflate(LayoutInflater
                .from(parent.context),parent, false)

        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val current = getItem(position)
         holder.NamePlant(current.nom_commun,current.nom_latin)
         holder.DateDernierAronsage(current.last_Watering)
         holder.DateNextWatering(current.next_Watering)
         holder.IdPlant(current.id_plant)
         holder.Image(current.localUri)

    }

    inner class VH(val binding: ListItemWateringplantBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{

        init {
            itemView.setOnClickListener(this)
            Log.i("MainActivity1", "onItemClick: rentre dans onItemCclick")
        }

        var id_plant : Int? = null
        var dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
        fun NamePlant(nameCommun: String?,nameLatin:String?) { binding.plantName.text = nameCommun+" "+nameLatin}
        fun DateDernierAronsage(date :Date?){binding.lastWatering.text = dateFormat.format(date) }
        fun DateNextWatering(date: Date?){binding.nextWatering.text = dateFormat.format(date)}
        fun Image(uri : Uri?){binding.imageView.setImageURI(uri)}
        fun IdPlant(id : Int?){id_plant= id}


        override fun onClick(v: View?) {

            val position:Int = adapterPosition
            listener.onItemClick(position, id_plant!!)


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

    interface OnItemClickListener{
        fun onItemClick(position: Int,id_plant:Int)
    }
}