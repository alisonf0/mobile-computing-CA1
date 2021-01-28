package com.example.DogWiki.data

//Importing Room to use to make the database.

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

//Initialising the Database entity using the BreedEntity as an entity.
@Database(entities = [BreedEntity::class], version = 1, exportSchema = false)

//Referencing the DateConverter.
@TypeConverters(DateConverter::class)

abstract class AppDatabase: RoomDatabase() {

    abstract fun breedDao(): BreedDao?

//Building the DogWiki.db database using Room. This will store the Breed object instances.
    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "dogwiki.db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}