package com.example.listadotareasapp.activities

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.example.listadotareasapp.R
import com.example.listadotareasapp.data.Tarea
import com.example.listadotareasapp.data.TareaDAO
import com.example.listadotareasapp.databinding.ActivityDetalleBinding
import java.util.Calendar

/*class DetalleActivity : AppCompatActivity() {
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
}*/


class DetalleActivity : FragmentActivity(),
NoticeDialogFragment.NoticeDialogListener {

    fun showNoticeDialog() {
        Toast.makeText(this,"aqui llega",Toast.LENGTH_SHORT).show()
        // Create an instance of the dialog fragment and show it.
        val dialog = NoticeDialogFragment()
        dialog.show(supportFragmentManager, "NoticeDialogFragment")
    }

    override fun onCreatePanelView(featureId: Int): View? {

        return super.onCreatePanelView(featureId)
    }

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following
    // methods defined by the NoticeDialogFragment.NoticeDialogListener
    // interface.
    override fun onDialogPositiveClick(dialog: DialogFragment) {
        // User taps the dialog's positive button.
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        // User taps the dialog's negative button.
    }

}






class NoticeDialogFragment : DialogFragment() {
    // Use this instance of the interface to deliver action events.
    internal lateinit var listener: NoticeDialogListener

    // The activity that creates an instance of this dialog fragment must
    // implement this interface to receive event callbacks. Each method passes
    // the DialogFragment in case the host needs to query it.
    interface NoticeDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    // Override the Fragment.onAttach() method to instantiate the
    // NoticeDialogListener.
    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Verify that the host activity implements the callback interface.
        try {
            // Instantiate the NoticeDialogListener so you can send events to
            // the host.
            listener = context as NoticeDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface. Throw exception.
            throw ClassCastException((context.toString() +
                    " must implement NoticeDialogListener"))
        }
    }


    private lateinit var binding: ActivityDetalleBinding

    private lateinit var taskDAO: TareaDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleBinding.inflate(layoutInflater)
        //setContentView(binding.root)

        taskDAO = TareaDAO(binding.nameEditText.context)

        binding.saveButton.setOnClickListener {

            /* binding.deadLineDatePicker.setOnDateChangedListener{
               val calendar = Calendar.getInstance()
                calendar.set(binding.deadLineDatePicker.year,
                    binding.deadLineDatePicker.month,
                    binding.deadLineDatePicker.dayOfMonth)
            }*/
            val taskName = binding.nameEditText.text.toString()
            val task = Tarea(-1, taskName, false)
            taskDAO.insert(task)
            Toast.makeText(binding.nameEditText.context, "Tarea guardad correctamente", Toast.LENGTH_SHORT).show()
            //finish()
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout to use as a dialog or embedded fragment.
        return inflater.inflate(R.layout.activity_detalle, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        /*val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog*/

        return activity?.let {
            // Use the Builder class for convenient dialog construction.
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;

            /*builder.setMessage("Start game")
                .setPositiveButton("Start") { dialog, id ->
                    // START THE GAME!
                }
                .setNegativeButton("Cancel") { dialog, id ->
                    // User cancelled the dialog.
                }
                */

            // Create the AlertDialog object and return it.
            //builder.create()



            binding.saveButton.setOnClickListener {
                val taskName = binding.nameEditText.text.toString()
                val task = Tarea(-1, taskName, false)
                taskDAO.insert(task)
                Toast.makeText( binding.saveButton.context, "Tarea guardad correctamente", Toast.LENGTH_SHORT).show()
                //finish()
            }


            /*************/
            // Get the layout inflater.
            //val inflater: LayoutInflater

            //inflater.inflate(R.layout.activity_detalle, binding.root)

            // Inflate and set the layout for the dialog.
            // Pass null as the parent view because it's going in the dialog
            // layout.
            builder.setView(inflater.inflate(R.layout.activity_detalle, binding.root))
                // Add action buttons.
                .setPositiveButton("Sing in", //R.string.signin,
                    DialogInterface.OnClickListener { dialog, id ->
                        // Sign in the user.
                    })
                .setNegativeButton("Cancel", //R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog()?.cancel()

                    })
            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")


    }


}


