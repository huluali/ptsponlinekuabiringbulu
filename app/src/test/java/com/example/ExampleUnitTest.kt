package com.example

import com.example.data.local.entity.IkmSurveyEntity
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun ikmSurveyEntity_calculatesAverageScoreCorrectly() {
        val survey = IkmSurveyEntity(
            id = 1L,
            respondentName = "Daud dg Situru",
            respondentPhone = "081234567890",
            serviceName = "Pendaftaran Nikah di Luar KUA (Bedol)",
            village = "Tonrorita",
            overallRating = 5,
            ratingRequirements = 5,
            ratingProcedure = 5,
            ratingSpeed = 4,
            ratingCost = 5,
            ratingStaff = 5,
            ratingFacility = 4,
            feedback = "Pelayanan sangat memuaskan, ramah dan bebas pungli."
        )

        assertEquals("Daud dg Situru", survey.respondentName)
        assertEquals(5, survey.overallRating)

        val indicators = listOf(
            survey.ratingRequirements,
            survey.ratingProcedure,
            survey.ratingSpeed,
            survey.ratingCost,
            survey.ratingStaff,
            survey.ratingFacility
        )
        val averageRating = indicators.average()
        // (5 + 5 + 4 + 5 + 5 + 4) / 6 = 28 / 6 = 4.666...
        assertTrue(averageRating > 4.6 && averageRating < 4.7)

        // Permenpan RB conversion: Index (scale 1-5) converted to standard 100% scale
        val ikmScorePercent = (averageRating / 5.0) * 100.0
        assertTrue(ikmScorePercent >= 90.0) // Sangat Baik (A)
    }
}
