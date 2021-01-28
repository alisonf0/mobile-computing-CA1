package com.example.DogWiki

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.DogWiki.data.AppDatabase
import com.example.DogWiki.data.BreedDao
import com.example.DogWiki.data.BreedEntity
import com.example.DogWiki.data.SampleDataProvider
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var dao: BreedDao
    private lateinit var database: AppDatabase

    @Before
    fun createDb() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = database.breedDao()!!
    }

    @Test
    fun createBreeds() {
        dao.insertAll(SampleDataProvider.getBreeds())
        val count = dao.getCount()
        assertEquals(count, SampleDataProvider.getBreeds().size)
    }

    @Test
    fun insertBreed() {
        val breed = BreedEntity()
        breed.text = "some text"
        breed.text2 = "more text"
        breed.text3 = "text again"
        dao.insertBreed(breed)
        val savedBreed = dao.getBreedById(1)
        assertEquals(savedBreed?.id ?: 0, 1)
    }

    @After
    fun closeDb() {
        database.close()
    }
}