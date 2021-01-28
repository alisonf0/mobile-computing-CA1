package com.example.DogWiki.data

import androidx.lifecycle.LiveData
import androidx.room.*


//Creating the Data Access Object (DAO) BreedDao for use with Room.
@Dao
interface BreedDao {

    //Setting up the CRUD for the Breed Entities to pass data to the Room database.

    //Create/Update
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBreed(breed: BreedEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(breeds: List<BreedEntity>)

    //Read
    @Query("SELECT * FROM breeds ORDER BY date ASC")
    fun getAll(): LiveData<List<BreedEntity>>

    @Query("SELECT * FROM breeds WHERE id = :id")
    fun getBreedById(id: Int): BreedEntity?

    @Query("SELECT COUNT(*) from breeds")
    fun getCount(): Int

    //Delete Selected
    @Delete
    fun deleteBreeds(selectedBreeds: List<BreedEntity>): Int

    //Delete All
    @Query("DELETE FROM breeds")
    fun deleteAll():Int

    //Delete
    @Delete
    fun deleteBreed(breed: BreedEntity)

}