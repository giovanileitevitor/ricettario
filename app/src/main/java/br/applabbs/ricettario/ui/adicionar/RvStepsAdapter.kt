package br.applabbs.ricettario.ui.adicionar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.applabbs.ricettario.R
import br.applabbs.ricettario.domain.local.models.Step

class RvStepsAdapter(
    private val data: List<Step>,
    private val itemListener: (Step) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewModel: View = inflater.inflate(R.layout.item_step, parent, false)
        return DefaultVH(viewModel)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        processDefault(holder, position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private inner class DefaultVH(itemView: View): RecyclerView.ViewHolder(itemView){
        var id: TextView = itemView.findViewById(R.id.txtIdStep)
        var info: TextView = itemView.findViewById(R.id.txtStep)

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

        defaultVH.id.text = position.toString() + " ->"
        defaultVH.info.text = item.info
    }

}