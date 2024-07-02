package com.example.listadotareasapp.data

import android.provider.BaseColumns

data class Tarea(var id:Int, var nombre:String, var realizada:Boolean)
{

    companion object{
        const val TABLE_NAME = "Tarea"
        const val COLUMNA_NOMBRE = "nombre"
        const val COLUMNA_REALIZADA = "realizada"

        const val SQL_CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COLUMNA_NOMBRE TEXT," +
                    "$COLUMNA_REALIZADA INTEGER)"

        const val SQL_DROP_TABLE ="DROP TABLE IF EXISTS $TABLE_NAME"
    }

}
