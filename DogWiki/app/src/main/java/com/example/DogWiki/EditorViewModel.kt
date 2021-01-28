package com.example.DogWiki

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.DogWiki.data.AppDatabase
import com.example.DogWiki.data.BreedEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditorViewModel(app: Application) : AndroidViewModel(app) {

    private val database = AppDatabase.getInstance(app)
    val currentBreed = MutableLiveData<BreedEntity>()

    //The function get breed by id finds the dog breed by their automatically generated id number.
    fun getBreedById(breedId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val breed =
                    if (breedId != NEW_BREED_ID) {
                        database?.breedDao()?.getBreedById(breedId)
                    } else {
                        BreedEntity()
                    }
                currentBreed.postValue(breed)
            }
        }
    }

    //The function update breed is called when the user wants to add data to or update a breed entity.
    fun updateBreed() {
        currentBreed.value?.let {

            //Trim removes leading and tailing spaces.
            it.text = it.text.trim()
            it.text2 = it.text2.trim()
            it.text3 = it.text3.trim()

            //If the user creates a new breed and leaves all the fields empty, the empty breed entity will not be saved.
            if (it.id == NEW_BREED_ID && it.text.isEmpty() && it.text2.isEmpty() && it.text3.isEmpty()) {
                return
            }

            //If the user deletes all data from a breed, it will be deleted.
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    if (it.text.isEmpty() && it.text2.isEmpty() && it.text3.isEmpty()) {
                        database?.breedDao()?.deleteBreed(it)
                    } else {
                        database?.breedDao()?.insertBreed(it)
                    }
                }
            }

        }


    }

}