package com.example.listadotareasapp.utils

import android.graphics.Color
import android.support.v4.os.IResultReceiver._Parcel
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.listadotareasapp.data.Tarea
import com.example.listadotareasapp.databinding.ItemTareaBinding
import com.google.android.material.checkbox.MaterialCheckBox.OnCheckedStateChangedListener

class TareaAdapter(private var dataSet: List<Tarea> = emptyList(),
                   private val onItemClickListener: (Int) -> Unit,
                   private val onDeleteClickListener: (Int) -> Unit,
                   private val onCheckBoxChangedListener: (Int) -> Unit,
                    private val onEditClickListener: (Int) -> Unit ) : RecyclerView.Adapter<TareaViewHolder>(){

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
            //onItemClickListener(position)
            onItemClickListener(holder.adapterPosition) // es la posicion más real, se calcula dinamicamente!!
        }
        holder.binding.deleteImageButton.setOnClickListener(){
            onDeleteClickListener(holder.adapterPosition)
        }


        holder.binding.realizadoCheckBox.setOnCheckedChangeListener {chk , _ ->
        if (chk.isPressed)
            onCheckBoxChangedListener(holder.adapterPosition)

        }

        holder.binding.editImageButton.setOnClickListener{
            onEditClickListener(holder.adapterPosition)
        }


    }

    fun updateData(dataSet: List<Tarea> ) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    /*fun updateData(dataSet: List<SuperheroResponse>, highlight: String? ){
        this.highlightText = highlight
        this.dataSet = dataSet
        notifyDataSetChanged()
    }*/


}

class TareaViewHolder(val binding: ItemTareaBinding) : RecyclerView.ViewHolder(binding.root)
{
    fun render(tarea: Tarea){
        binding.nameTextView.text = tarea.nombre
        binding.realizadoCheckBox.isChecked = tarea.realizada
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