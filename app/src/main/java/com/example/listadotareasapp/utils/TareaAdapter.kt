package com.example.listadotareasapp.utils

import android.graphics.Color
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listadotareasapp.data.Tarea
import com.example.listadotareasapp.databinding.ItemTareaBinding

class TareaAdapter(private var dataSet: List<Tarea> = emptyList(), private val onItemClickListener: (Int) -> Unit) : RecyclerView.Adapter<TareaViewHolder>(){

    private var highlightText: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TareaViewHolder {
        val binding = ItemTareaBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        //return SuperheroViewHolder(ItemSuperheroBinding.bind(parent.rootView))
        return TareaViewHolder(binding)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: TareaViewHolder, position: Int) {
        holder.render(dataSet[position])
        holder.itemView.setOnClickListener{
            onItemClickListener(position)
            //onItemClickListener(holder.adapterPosition) // es la posicion más real, se calcula dinamicamente!!
        }

        if (highlightText != null) {
            holder.highlight(highlightText!!)
        }

        //aqui declaramos el evento de click onItemClickListener
        //holder.itemView.setOnClickListener {onItemClickListener(position)}

    }

    /*fun updateData(dataSet: List<SuperheroResponse>, highlight: String? ){
        this.highlightText = highlight
        this.dataSet = dataSet
        notifyDataSetChanged()
    }*/


}

class TareaViewHolder(private val binding: ItemTareaBinding) : RecyclerView.ViewHolder(binding.root)
{
    fun render(tarea: Tarea){
        binding.nameTextView.text = tarea.nombre
    }

    // Subraya el texto que coincide con la busqueda
    fun highlight(text: String) {
        try {
            val highlighted = binding.nameTextView.text.toString().highlight(text)
            //textView.text = highlighted
        } catch (e: Exception) { }
    }
    val imageUrl = "https://www.example.com/imagen.jpg"


    //ejemplo de extensión para el metodo String, que hace resaltar el texto buscado
    fun String.highlight(text: String) : SpannableString {
        val str = SpannableString(this)
        val startIndex = str.indexOf(text, 0, true)
        str.setSpan(BackgroundColorSpan(Color.rgb(244,144,255)), startIndex, startIndex + text.length, 0)
        return str
    }

}