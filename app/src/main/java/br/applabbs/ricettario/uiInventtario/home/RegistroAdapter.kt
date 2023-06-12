package br.applabbs.ricettario.uiInventtario.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.applabbs.ricettario.R
import br.applabbs.ricettario.domain.local.models.Registro


class RegistroAdapter(
    private val data: List<Registro>,
    private val itemListener: (Registro) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewModel: View = inflater.inflate(R.layout.item_registro, parent, false)
        return DefaultVH(viewModel)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        processDefault(holder, position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private inner class DefaultVH(itemView: View) : RecyclerView.ViewHolder(itemView){
        var date : TextView = itemView.findViewById(R.id.txtDate)
        var details : TextView = itemView.findViewById(R.id.txtDetalhes)
        var img: ImageView = itemView.findViewById(R.id.imgRegistro)

        init{
            itemView.setOnClickListener {
                val position = adapterPosition
                val item = data[position]
                itemListener.invoke(item)
            }

//            itemView.setOnLongClickListener {
//                val position = adapterPosition
//                val item = data[position]
//                return itemLongListener.invoke(item)
//            }
        }
    }

    private fun processDefault(holder: RecyclerView.ViewHolder, position: Int){
        val item = data[position]
        val defaultVH = holder as DefaultVH

        defaultVH.date.text = item.dateRegister
        defaultVH.details.text = item.productName
        defaultVH.img.visibility = if(item.hasImage == true) View.VISIBLE else View.GONE
    }

}