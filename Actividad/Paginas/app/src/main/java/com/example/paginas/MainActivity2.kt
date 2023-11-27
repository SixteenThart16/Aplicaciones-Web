package com.example.paginas

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val et1 = findViewById<EditText>(R.id.rfc)
        val et2 = findViewById<EditText>(R.id.con)
        val et3 = findViewById<EditText>(R.id.grupo)
        val boton1 = findViewById<Button>(R.id.balta)
        val boton2 = findViewById<Button>(R.id.bnombre)
        val boton3 = findViewById<Button>(R.id.bcontrol)
        val boton4 = findViewById<Button>(R.id.bbaja)
        val boton5 = findViewById<Button>(R.id.bmenu)

        boton1.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val registro = ContentValues().apply {
                put("nombre", et1.text.toString())
                put("no_control", et2.text.toString())
                put("grupo", et3.text.toString())
            }
            bd.insert("alumnos", null, registro)
            bd.close()
            et1.setText("")
            et2.setText("")
            et3.setText("")
            Toast.makeText(this, "Se cargaron los datos del alumno", Toast.LENGTH_SHORT).show()
        }

        boton2.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.readableDatabase
            val fila = bd.rawQuery("SELECT no_control, grupo FROM alumnos WHERE nombre=?", arrayOf(et1.text.toString()))
            if (fila.moveToFirst()) {
                et2.setText(fila.getString(0))
                et3.setText(fila.getString(1))
            } else {
                Toast.makeText(this, "No existe un alumno con dicho nombre", Toast.LENGTH_SHORT).show()
            }
            bd.close()
        }

        boton3.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.readableDatabase
            val fila = bd.rawQuery("SELECT nombre, grupo FROM alumnos WHERE no_control=?", arrayOf(et2.text.toString()))
            if (fila.moveToFirst()) {
                et1.setText(fila.getString(0))
                et3.setText(fila.getString(1))
            } else {
                Toast.makeText(this, "No existe un alumno con el número de control proporcionado", Toast.LENGTH_SHORT).show()
            }
            bd.close()
        }

        boton4.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val cant = bd.delete("alumnos", "nombre=?", arrayOf(et1.text.toString()))
            bd.close()
            et1.setText("")
            et2.setText("")
            et3.setText("")
            if (cant == 1) {
                Toast.makeText(this, "Se borró el alumno", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No existe el alumno", Toast.LENGTH_SHORT).show()
            }
        }

        boton5.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
