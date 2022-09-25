package br.applabbs.ricettario.ui.exibir

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import br.applabbs.ricettario.R
import br.applabbs.ricettario.domain.local.models.Foto
import com.bumptech.glide.RequestManager

class FotosAdapter(
    private val data: List<Foto>,
    private val mGlide: RequestManager,
    private val itemListener: (Foto) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewModel: View = inflater.inflate(R.layout.item_imagem_receita, parent, false)
        return DefaultVH(viewModel)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        processDefault(holder, position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private inner class DefaultVH(itemView: View): RecyclerView.ViewHolder(itemView){
        var photo: ImageView = itemView.findViewById(R.id.imgReceita)

        init{
            itemView.setOnClickListener {
                val position = adapterPosition
                val item = data[position]
                itemListener.invoke(item)
            }
        }
    }

    private fun processDefault(holder: RecyclerView.ViewHolder, position: Int){
        val item = data[position]
        val defaultVH = holder as DefaultVH

        mGlide.load(item.imgAddress)
            .placeholder(R.drawable.img_receita)
            .into(defaultVH.photo)
    }
}