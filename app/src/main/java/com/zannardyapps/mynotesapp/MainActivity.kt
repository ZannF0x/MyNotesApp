package com.zannardyapps.mynotesapp

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.zannardyapps.mynotesapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val buttonOfSave = binding.fab

        buttonOfSave.setOnClickListener {

            val noteRetrieved = binding.editContainer.editTextNotes.text.toString()

            if (noteRetrieved == ""){
                Toast.makeText(this, "Digite alguma anotação...", Toast.LENGTH_SHORT).show()

            } else {
                GlobalScope.launch {
                    DataStoreMenager.saveNote(this@MainActivity, "note", binding.editContainer.editTextNotes.text.toString())
                }
                Toast.makeText(this, "Anotação salva!", Toast.LENGTH_SHORT).show()

            }

        }

        val buttonOfDelete = binding.fab2

        buttonOfDelete.setOnClickListener {

            val noteRetrieved = binding.editContainer.editTextNotes.text.toString()

            if (noteRetrieved == ""){
                Toast.makeText(this, "Digite alguma anotação...", Toast.LENGTH_SHORT).show()

            } else {
                binding.editContainer.editTextNotes.setText("")
                GlobalScope.launch {
                    DataStoreMenager.saveNote(this@MainActivity, "note", binding.editContainer.editTextNotes.text.toString())
                }
                Toast.makeText(this, "Apagando anotações...", Toast.LENGTH_SHORT).show()
            }
        }

        GlobalScope.launch(Dispatchers.IO) {
            val noteValueSaved = DataStoreMenager.getToNote(this@MainActivity, "note")

            if (noteValueSaved != ""){
                GlobalScope.launch(Dispatchers.Main){
                    binding.editContainer.editTextNotes.setText(noteValueSaved)
                }
            }
        }

    }


}