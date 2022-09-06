package br.applabbs.ricettario.ui.home

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.applabbs.ricettario.R
import br.applabbs.ricettario.domain.local.models.Receita
import com.bumptech.glide.RequestManager

class ReceitasAdapter(
    private val data: List<Receita> = emptyList(),
    private val mGlide: RequestManager,
    private val itemListener: (Receita) -> Unit,
    private val itemLongListener: (Receita) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val viewModel: View = inflater.inflate(R.layout.item_my_receitas, parent, false)
        return DefaultVH(viewModel)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        processDefault(holder, position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private inner class DefaultVH(itemView: View) : RecyclerView.ViewHolder(itemView){
        var photo: ImageView = itemView.findViewById(R.id.imgPrincipal)
        var titulo: TextView = itemView.findViewById(R.id.txtTitulo)
        var estrelas: ImageView = itemView.findViewById(R.id.imgStars)
        //var signo: TextView = itemView.findViewById(R.id.txt_signo)
        //var obs: TextView = itemView.findViewById(R.id.txt_obs)

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

        mGlide.load(item.img)
            .placeholder(R.drawable.img_receita)
            .into(defaultVH.photo)

        if(item.isFavorito){
            mGlide.load(R.drawable.ic_star)
                .placeholder(R.drawable.ic_star)
                .into(defaultVH.estrelas)
        } else{
            mGlide.load(R.drawable.ic_star_off)
                .placeholder(R.drawable.ic_star_off)
                .into(defaultVH.estrelas)
        }

        defaultVH.titulo.text = item.titulo

    }
}