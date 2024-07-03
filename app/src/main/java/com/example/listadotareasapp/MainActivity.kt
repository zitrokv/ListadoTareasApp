package com.example.listadotareasapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listadotareasapp.data.Tarea
import com.example.listadotareasapp.data.TareaDAO
import com.example.listadotareasapp.databinding.ActivityMainBinding
import com.example.listadotareasapp.utils.TareaAdapter
import java.lang.reflect.Array.getBoolean
import java.lang.reflect.Array.getInt

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
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
            ).show()},
            { taskDAO.delete(tareaList[it])
                Toast.makeText(
                    this,
                    "Click en ${tareaList[it].nombre}",
                    Toast.LENGTH_LONG
                ).show()
                loadData()
            },

            {  tareaList[it].realizada = !tareaList[it].realizada
                taskDAO.update(tareaList[it])
              loadData()
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
        //binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        //binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)


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
}