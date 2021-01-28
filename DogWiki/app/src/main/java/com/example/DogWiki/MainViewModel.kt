package com.example.DogWiki

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.DogWiki.data.AppDatabase
import com.example.DogWiki.data.BreedEntity
import com.example.DogWiki.data.SampleDataProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val database = AppDatabase.getInstance(app)
    val breedsList = database?.breedDao()?.getAll()

    //Adds sample data to the breeds list.
    fun addSampleData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val sampleBreeds = SampleDataProvider.getBreeds()
                database?.breedDao()?.insertAll(sampleBreeds)
            }
        }
    }

    //Deletes selected breeds from the breeds list.
    fun deleteBreeds(selectedBreeds: List<BreedEntity>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database?.breedDao()?.deleteBreeds(selectedBreeds)
            }
        }

    }

    //Deletes all breeds from the database.
    fun deleteAllBreeds() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database?.breedDao()?.deleteAll()
            }
        }
    }

}