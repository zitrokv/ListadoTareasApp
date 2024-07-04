package com.example.listadotareasapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import com.example.listadotareasapp.R
import com.example.listadotareasapp.data.Tarea
import com.example.listadotareasapp.data.TareaDAO
import com.example.listadotareasapp.databinding.ActivityDetalleBinding

class DetalleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetalleBinding

    private lateinit var taskDAO: TareaDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskDAO = TareaDAO(this)

        binding.saveButton.setOnClickListener {
            val taskName = binding.nameEditText.text.toString()
            val task = Tarea(-1, taskName, false)
            taskDAO.insert(task)
            Toast.makeText(this, "Tarea guardad correctamente", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}