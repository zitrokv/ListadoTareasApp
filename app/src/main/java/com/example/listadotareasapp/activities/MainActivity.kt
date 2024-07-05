package com.example.listadotareasapp.activities

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.widget.CalendarView
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.DialogTitle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listadotareasapp.R
import com.example.listadotareasapp.data.Tarea
import com.example.listadotareasapp.data.TareaDAO
import com.example.listadotareasapp.databinding.ActivityDetalleBinding
import com.example.listadotareasapp.databinding.ActivityMainBinding
import com.example.listadotareasapp.utils.TareaAdapter
import java.util.Calendar
import java.util.zip.Inflater

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
                /*Toast.makeText(
                    this,
                    "Click en ${tareaList[it].nombre}",
                    Toast.LENGTH_LONG
                ).show()*/
            },
            {
                taskDAO.delete(tareaList[it])
                /*Toast.makeText(
                    this,
                    "Click en ${tareaList[it].nombre}",
                    Toast.LENGTH_LONG
                ).show()*/
                loadData()
            },

            {
                tareaList[it].realizada = !tareaList[it].realizada
                taskDAO.update(tareaList[it])
                loadData()
            },
            {
                //showDialog()
                mostrarDialog("Modificar tarea", tareaList[it].nombre)
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
        binding.recyclerView.layoutManager = GridLayoutManager(this, 1)
        //binding.recyclerView.layoutManager = LinearLayoutManager(this)
        //binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.addTaskButton.setOnClickListener {
            mostrarDialog("Añadir nueva tarea", "",true)
        }

    }

    override fun onResume() {
        super.onResume()

        loadData()
    }

    private fun loadData() {
        tareaList = taskDAO.findAll()

        adapter.updateData(tareaList)
    }

     fun mostrarDialog(title: String, message: String, esNuevaTarea:Boolean = false) {
    val builder = AlertDialog.Builder(this)
         builder.setTitle(title)

         //builder.setMessage("Fecha a rellenar...")
//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))
        val dialogBinding = ActivityDetalleBinding.inflate(layoutInflater)

        builder.setView(dialogBinding.root)



         //botones comunes OK Cancel
         builder.setPositiveButton(android.R.string.yes)
         { dialog, which ->


             /*Toast.makeText(
                 this,
                 android.R.string.yes, Toast.LENGTH_SHORT
             ).show()*/
         }

         builder.setNegativeButton(android.R.string.no)
         { dialog, which ->
             /*Toast.makeText(
                 this,
                 android.R.string.no, Toast.LENGTH_SHORT
             ).show()*/
         }

    /*builder.setNeutralButton("Maybe")
    {
        dialog, which ->
        Toast.makeText(
            this,
            "Maybe", Toast.LENGTH_SHORT
        ).show()
    }*/

    //mostrar en pantalla
    val dialog = builder.create()


         //si es modificación muestra los botones
         if (!esNuevaTarea) {
             dialogBinding.nameEditText.visibility = View.GONE
             builder.setMessage(message)

         } else
         {

             dialogBinding.saveButton.setOnClickListener {
                 val taskName = dialogBinding.nameEditText.text.toString()
                 val task = Tarea(-1, taskName, false)
                 taskDAO.insert(task)
                 Toast.makeText(this, "Tarea guardad correctamente", Toast.LENGTH_SHORT).show()
                 loadData()
                 dialog.dismiss()
                 //finish()
             }

             /*
             val c = Calendar.getInstance()
             val year = c.get(Calendar.YEAR)
             val month = c.get(Calendar.MONTH)
             val day = c.get(Calendar.DAY_OF_MONTH)
             val picker = DatePickerDialog(this as Context, , year, month, day)
*/

         }

         dialog.show()
}

    class DatePickerFragment(val listener: (day: Int, month: Int, year: Int) -> Unit) :
        DialogFragment(), DatePickerDialog.OnDateSetListener {
        override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
            TODO("Not yet implemented")
        }

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val picker = DatePickerDialog(activity as Context, this, year, month, day)
            return picker
        }
    }

    fun showDialog() {
        val fragmentManager = supportFragmentManager
        val newFragment = DetalleActivity()
        //val newFragment = fragmentManager.
        //if (isLargeLayout) {
        if (true){
            // The device is using a large layout, so show the fragment as a
            // dialog.
            //newFragment.show(fragmentManager, "dialog")

            newFragment.showNoticeDialog()
        } else {
            // The device is smaller, so show the fragment fullscreen.
            val transaction = fragmentManager.beginTransaction()
            // For a polished look, specify a transition animation.
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            // To make it fullscreen, use the 'content' root view as the container
            // for the fragment, which is always the root view for the activity.

            transaction
                //.add(binding.root, newFragment())
                .addToBackStack(null)
                .commit()
        }
    }

}