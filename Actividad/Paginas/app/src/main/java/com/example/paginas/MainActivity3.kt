package com.example.paginas

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        val et1 = findViewById<EditText>(R.id.rfc)
        val et2 = findViewById<EditText>(R.id.nom)
        val et3 = findViewById<EditText>(R.id.mat)
        val boton1 = findViewById<Button>(R.id.balta)
        val boton2 = findViewById<Button>(R.id.brfc)
        val boton3 = findViewById<Button>(R.id.bnombre)
        val boton4 = findViewById<Button>(R.id.bbaja)
        val boton5 = findViewById<Button>(R.id.bmenu)

        boton1.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val registro = ContentValues()
            registro.put("rfc", et1.text.toString())
            registro.put("nombre", et2.text.toString())
            registro.put("materia", et3.text.toString())
            bd.insert("maestros", null, registro)
            bd.close()
            et1.setText("")
            et2.setText("")
            et3.setText("")
            Toast.makeText(this, "Se cargaron los datos", Toast.LENGTH_SHORT).show()
        }

        boton2.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val fila = bd.rawQuery("SELECT nombre, materia FROM maestros WHERE rfc='${et1.text.toString()}'", null)
            if (fila.moveToFirst()) {
                et2.setText(fila.getString(0))
                et3.setText(fila.getString(1))
            } else {
                Toast.makeText(this, "No existe", Toast.LENGTH_SHORT).show()
            }
            bd.close()
        }

        boton3.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val fila = bd.rawQuery("SELECT rfc, materia FROM maestros WHERE nombre='${et2.text.toString()}'", null)
            if (fila.moveToFirst()) {
                et1.setText(fila.getString(0))
                et3.setText(fila.getString(1))
            } else {
                Toast.makeText(this, "No existe", Toast.LENGTH_SHORT).show()
            }
            bd.close()
        }

        boton4.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val cant = bd.delete("maestros", "rfc='${et1.text.toString()}'", null)
            bd.close()
            et1.setText("")
            et2.setText("")
            et3.setText("")
            if (cant == 1)
                Toast.makeText(this, "Se borró", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "No existe", Toast.LENGTH_SHORT).show()
        }

        boton5.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
