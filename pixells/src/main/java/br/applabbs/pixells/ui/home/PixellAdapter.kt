package br.applabbs.pixells.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.applabbs.pixells.R
import com.bumptech.glide.RequestManager

class PixellAdapter(
    private val data: List<Pixell>,
    private val mGlide: RequestManager,
    private val itemListener: (Pixell) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewModel: View = inflater.inflate(R.layout.item_pixell, parent, false)
        return DefaultVH(viewModel)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        processDefault(holder, position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private inner class DefaultVH(itemView: View): RecyclerView.ViewHolder(itemView){
        var photo: ImageView = itemView.findViewById(R.id.imgPixell)
        var text: TextView = itemView.findViewById(R.id.txtPixell)

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

        mGlide.load(item.urlImg)
            .placeholder(R.drawable.ic_android)
            .override(10,10)
            .centerCrop()
            .into(defaultVH.photo)

        defaultVH.text.text = item.text
        defaultVH.text.textSize = 8F
        //defaultVH.text.visibility = GONE
    }

}