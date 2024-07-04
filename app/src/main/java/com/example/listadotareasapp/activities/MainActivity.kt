package com.example.listadotareasapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listadotareasapp.data.Tarea
import com.example.listadotareasapp.data.TareaDAO
import com.example.listadotareasapp.databinding.ActivityMainBinding
import com.example.listadotareasapp.utils.TareaAdapter

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TareaAdapter
    private lateinit var taskDAO: TareaDAO
    lateinit var tareaList: List<Tarea>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskDAO = TareaDAO(this)

        /*taskDAO.insert(Tarea(-1, "Comprar leche", false))
        taskDAO.insert(Tarea(-1, "Ir al banco", false))
        taskDAO.insert(Tarea(-1, "Entrenar", false))
        taskDAO.insert(Tarea(-1, "Raspar pescado", false))
        taskDAO.insert(Tarea(-1, "Hacer aplicaciones", false))
        taskDAO.insert(Tarea(-1, "Abono del tren", false))
        taskDAO.insert(Tarea(-1, "Organizar entierro", false))
*/
        /* Log.i("DATABASE", task.toString())

        task.realizada = true

        taskDAO.update(task)

        Log.i("DATABASE", task.toString())

        task = taskDAO.find(task.id)!!

        Log.i("DATABASE", task.toString())

        taskDAO.delete(task)

        val taskList = taskDAO.findAll()

        Log.i("DATABASE", taskList.toString())*/

        adapter = TareaAdapter(
            emptyList(), {
                Toast.makeText(
                    this,
                    "Click en ${tareaList[it].nombre}",
                    Toast.LENGTH_LONG
                ).show()
            },
            {
                taskDAO.delete(tareaList[it])
                Toast.makeText(
                    this,
                    "Click en ${tareaList[it].nombre}",
                    Toast.LENGTH_LONG
                ).show()
                loadData()
            },

            {
                tareaList[it].realizada = !tareaList[it].realizada
                taskDAO.update(tareaList[it])
                loadData()
            },
            {
                mostrarDialog()
            }
        )


        /*adapter = TareaAdapter(tareaList) {
            Toast.makeText(
                this,
                "Click en ${tareaList[it].nombre}",
                Toast.LENGTH_LONG
            ).show()
        }*/

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 1)
        //binding.recyclerView.layoutManager = LinearLayoutManager(this)
        //binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.addTaskButton.setOnClickListener {
            val intent = Intent(this, DetalleActivity::class.java)
            startActivity(intent)
        }

        //mostrarDialog()
        //loadData()
    }

    override fun onResume() {
        super.onResume()

        loadData()
    }

    private fun loadData() {
        tareaList = taskDAO.findAll()

        adapter.updateData(tareaList)
    }

    fun mostrarDialog() {
    val builder = AlertDialog.Builder(this)
    builder.setTitle("Androidly Alert")
    builder.setMessage("We have a message")
//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

    builder.setPositiveButton(android.R.string.yes)
    {
        dialog, which ->
        Toast.makeText(
            applicationContext,
            android.R.string.yes, Toast.LENGTH_SHORT
        ).show()
    }

    builder.setNegativeButton(android.R.string.no)
    {
        dialog, which ->
        Toast.makeText(
            applicationContext,
            android.R.string.no, Toast.LENGTH_SHORT
        ).show()
    }

    builder.setNeutralButton("Maybe")
    {
        dialog, which ->
        Toast.makeText(
            applicationContext,
            "Maybe", Toast.LENGTH_SHORT
        ).show()
    }
    builder.show()
}
}