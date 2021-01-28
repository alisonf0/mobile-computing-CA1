package com.example.DogWiki.data

import androidx.room.TypeConverter
import java.util.*


//A date converter which will convert the date to store in the database.
class DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }
}