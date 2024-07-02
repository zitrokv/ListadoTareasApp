package com.example.listadotareasapp.data

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import com.example.listadotareasapp.utils.DatabaseManager

class TareaDAO(context: Context) {
    private val databaseManager: DatabaseManager = DatabaseManager(context)

    fun insert(tarea: Tarea){
        val db = databaseManager.writableDatabase

        val values = ContentValues()
        values.put(Tarea.COLUMNA_NOMBRE, tarea.nombre)
        values.put(Tarea.COLUMNA_REALIZADA, tarea.realizada)

        val newRowId = db.insert(Tarea.TABLE_NAME, null, values)
        tarea.id = newRowId.toInt()
    }

    fun update(tarea: Tarea){
        val db = databaseManager.writableDatabase

        val values= ContentValues()
        values.put(Tarea.COLUMNA_NOMBRE, tarea.nombre)
        values.put(Tarea.COLUMNA_REALIZADA, tarea.realizada)

        val updatedRows = db.update(
            Tarea.TABLE_NAME,
            values,
            "${BaseColumns._ID} = ${tarea.id}",
            null
        )
    }

    fun delete(tarea:Tarea)
    {
        val db = databaseManager.writableDatabase
        val deletedRows = db.delete(Tarea.TABLE_NAME, "${BaseColumns._ID} = ${tarea.id}",null)
    }

    fun find(id: Int):Tarea? {

        val db = databaseManager.readableDatabase
        val projection = arrayOf(BaseColumns._ID, Tarea.COLUMNA_NOMBRE, Tarea.COLUMNA_REALIZADA)

        val cursor = db.query(
            Tarea.TABLE_NAME,
            projection,
            "${BaseColumns._ID} = $id",
            null,
            null,
            null,
            null
        )

        var tarea: Tarea? = null
        if (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(Tarea.COLUMNA_NOMBRE))
            val done = cursor.getInt(cursor.getColumnIndexOrThrow(Tarea.COLUMNA_REALIZADA)) == 1
            tarea = Tarea(id, name, done)
        }

        cursor.close()
        db.close()
        return tarea
    }
    fun findAll() : List<Tarea> {
        val db = databaseManager.readableDatabase

        val projection = arrayOf(BaseColumns._ID, Tarea.COLUMNA_NOMBRE, Tarea.COLUMNA_REALIZADA)

        val cursor = db.query(
            Tarea.TABLE_NAME,                        // The table to query
            projection,                             // The array of columns to return (pass null to get all)
            null,                            // The columns for the WHERE clause
            null,                         // The values for the WHERE clause
            null,                            // don't group the rows
            null,                             // don't filter by row groups
            null                             // The sort order
        )

        var tasks = mutableListOf<Tarea>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(Tarea.COLUMNA_NOMBRE))
            val done = cursor.getInt(cursor.getColumnIndexOrThrow(Tarea.COLUMNA_REALIZADA)) == 1
            val task = Tarea(id, name, done)
            tasks.add(task)
        }
        cursor.close()
        db.close()
        return tasks
    }


}