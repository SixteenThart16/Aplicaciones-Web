package com.example.paginas

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        val et1 = findViewById<EditText>(R.id.nom)
        val et2 = findViewById<EditText>(R.id.profe)
        val et3 = findViewById<EditText>(R.id.aula)
        val boton1 = findViewById<Button>(R.id.balta)
        val boton2 = findViewById<Button>(R.id.bmat)
        val boton3 = findViewById<Button>(R.id.bmaestro)
        val boton4 = findViewById<Button>(R.id.bbaja)
        val boton5 = findViewById<Button>(R.id.bmenu)

        boton1.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val registro = ContentValues().apply {
                put("nombre", et1.text.toString())
                put("profesor", et2.text.toString())
                put("aula", et3.text.toString())
            }
            bd.insert("materias", null, registro)
            bd.close()
            et1.setText("")
            et2.setText("")
            et3.setText("")
            Toast.makeText(this, "Se cargaron los datos", Toast.LENGTH_SHORT).show()
        }

        boton2.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.readableDatabase
            val fila = bd.rawQuery("SELECT profesor, aula FROM materias WHERE nombre=?", arrayOf(et1.text.toString()))
            if (fila.moveToFirst()) {
                et2.setText(fila.getString(0))
                et3.setText(fila.getString(1))
            } else {
                Toast.makeText(this, "No existe la materia", Toast.LENGTH_SHORT).show()
            }
            bd.close()
        }

        boton3.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.readableDatabase
            val fila = bd.rawQuery("SELECT nombre, aula FROM materias WHERE profesor=?", arrayOf(et2.text.toString()))
            if (fila.moveToFirst()) {
                et1.setText(fila.getString(0))
                et3.setText(fila.getString(1))
            } else {
                Toast.makeText(this, "No existe la materia", Toast.LENGTH_SHORT).show()
            }
            bd.close()
        }

        boton4.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val cant = bd.delete("materias", "nombre=?", arrayOf(et1.text.toString()))
            bd.close()
            et1.setText("")
            et2.setText("")
            et3.setText("")
            if (cant == 1) {
                Toast.makeText(this, "Se borró la materia", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No existe la materia", Toast.LENGTH_SHORT).show()
            }
        }

        boton5.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

