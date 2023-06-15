package br.applabbs.ricettario.uiInventtario.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import br.applabbs.ricettario.R
import br.applabbs.ricettario.domain.local.models.Receita
import br.applabbs.ricettario.domain.local.models.Registro
import com.bumptech.glide.RequestManager


class RegistroAdapter(
    private val data: List<Registro>,
    private val mGlide: RequestManager,
    private val itemListener: (Registro) -> Unit,
    private val itemLongListener: (Registro) -> Unit
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

            itemView.setOnLongClickListener {
                val position = adapterPosition
                val item = data[position]
                itemLongListener(item)
                return@setOnLongClickListener true
            }
        }
    }

    private fun processDefault(holder: RecyclerView.ViewHolder, position: Int){
        val item = data[position]
        val defaultVH = holder as DefaultVH

        mGlide.load(item.imageAddress)
            .placeholder(R.drawable.ic_fotos)
            .into(defaultVH.img)

        defaultVH.date.text = item.dateRegister
        defaultVH.details.text = item.productName + " (" + item.productBrand + ") "+ "\nValid: ${item.productVality}"
        defaultVH.img.visibility = if(item.hasImage == true) View.VISIBLE else View.GONE



    }

    fun atualizar(){
        notifyDataSetChanged()
    }

}