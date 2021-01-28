package com.example.DogWiki.data

import java.util.*


//Sample dog breed data. Clicking the "generate sample data" button will generate three breed objects populated with this sample data. This is useful for testing the app's functionality.
class SampleDataProvider {

    companion object {
        //The text that will populate the sample breed objects.
        private val sampleText1 = "Sample Breed 1"
        private val sampleText2 = "Sample Breed 2"
        private val sampleText3 = """Sample Breed 3""".trimIndent()

        //Creating note times.
        private fun getDate(diff: Long): Date {
            return Date(Date().time + diff)
        }

        //Makes an array list with the sample data.
        fun getBreeds() = arrayListOf(
            BreedEntity(getDate(0), sampleText1, sampleText1, sampleText1),
            BreedEntity(getDate(1), sampleText2, sampleText2, sampleText2),
            BreedEntity(getDate(2), sampleText3, sampleText3, sampleText3)
        )

    }
}