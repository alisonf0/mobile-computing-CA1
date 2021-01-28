package com.example.DogWiki.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.DogWiki.NEW_BREED_ID
import kotlinx.android.parcel.Parcelize
import java.util.*

//Establishing the BreedEntity table.
@Parcelize
@Entity(tableName = "breeds")

//Initialising the BreedEntity class.
data class BreedEntity(

    //Setting the primary key to auto generate.
    @PrimaryKey(autoGenerate = true)

    //Establishing class variables.

    //The ID is the number that identifies the instance.
    //The date helps to order them.
    //Text is the name of the breed.
    //Text 2 is the breed type (toy, utility, hound)
    //Text 3 is the description of the breed.
    var id: Int,
    var date: Date,
    var text: String,
    var text2: String,
    var text3: String

    //Passes the data along in a parcel.
) : Parcelable {
    constructor() : this(NEW_BREED_ID, Date(), "", "", "")
    constructor(date: Date, text: String, text2: String, text3: String) : this(NEW_BREED_ID, date, text, text2, text3)
}
