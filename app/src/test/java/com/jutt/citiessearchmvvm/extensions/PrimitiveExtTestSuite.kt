package com.jutt.citiessearchmvvm.extensions

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses
import java.util.*

/**
 * Test Suite for Primitive Extensions
 *
 * Runs all of the small test cases for PrimitiveExt.kt
 */
@RunWith(Suite::class)
@SuiteClasses(
    DoubleFormatNPlacesTest::class,
    StringTrimLastNTest::class,
    StringRemoveAtTest::class,
)
class PrimitiveExtTestSuite

/**
 * Test Cases for Double.formatUpToNPlaces extension method
 *
 */
class DoubleFormatNPlacesTest {

    private data class Case(
        val receiver: Double,
        val maximumPlacesParam: Int,
        val minimumPlacesParam: Int,
        val expectedResult: String
    )

    @Test
    fun formatNPlacesTest_WholeNumber() {
        Locale.setDefault(Locale.ENGLISH)

        val testCases = listOf(
            Case(
                receiver = 5.0,
                maximumPlacesParam = 2,
                minimumPlacesParam = 0,
                expectedResult = "5"
            ),
            Case(
                receiver = 5.0,
                maximumPlacesParam = 2,
                minimumPlacesParam = 1,
                expectedResult = "5.0"
            ),
            Case(
                receiver = 5.0,
                maximumPlacesParam = 2,
                minimumPlacesParam = 2,
                expectedResult = "5.00"
            ),
            Case(
                receiver = 5.0,
                maximumPlacesParam = 2,
                minimumPlacesParam = 5,
                expectedResult = "5.00"
            ),
            Case(
                receiver = 5.0,
                maximumPlacesParam = 0,
                minimumPlacesParam = 0,
                expectedResult = "5"
            ),
            Case(
                receiver = 5.0,
                maximumPlacesParam = 0,
                minimumPlacesParam = -1,
                expectedResult = "5"
            )
        )

        runTestCases(testCases)
    }

    @Test
    fun formatNPlacesTest_Decimal() {
        Locale.setDefault(Locale.ENGLISH)

        val testCases = listOf(
            Case(
                receiver = 5.000001,
                maximumPlacesParam = 2,
                minimumPlacesParam = 0,
                expectedResult = "5"
            ),
            Case(
                receiver = 5.000001,
                maximumPlacesParam = 10,
                minimumPlacesParam = 1,
                expectedResult = "5.000001"
            ),
            Case(
                receiver = 5.000001,
                maximumPlacesParam = 5,
                minimumPlacesParam = 5,
                expectedResult = "5.00000"
            ),
            Case(
                receiver = 5.000008,
                maximumPlacesParam = 5,
                minimumPlacesParam = 5,
                expectedResult = "5.00001"
            ),
            Case(
                receiver = 5.000001,
                maximumPlacesParam = 2,
                minimumPlacesParam = 5,
                expectedResult = "5.00"
            ),
            Case(
                receiver = 5.000001,
                maximumPlacesParam = 10,
                minimumPlacesParam = 10,
                expectedResult = "5.0000010000"
            ),
            Case(
                receiver = 5.000001,
                maximumPlacesParam = 0,
                minimumPlacesParam = 0,
                expectedResult = "5"
            ),
            Case(
                receiver = 5.000001,
                maximumPlacesParam = 0,
                minimumPlacesParam = -1,
                expectedResult = "5"
            ),
            Case(
                receiver = 5.9,
                maximumPlacesParam = 0,
                minimumPlacesParam = 0,
                expectedResult = "6"
            ),
            Case(
                receiver = 5.9999999,
                maximumPlacesParam = 3,
                minimumPlacesParam = 1,
                expectedResult = "6.0"
            ),
            Case(
                receiver = 5.9999999,
                maximumPlacesParam = 3,
                minimumPlacesParam = 3,
                expectedResult = "6.000"
            )
        )

        runTestCases(testCases)
    }

    @Test
    fun formatNPlacesTest_LocaleUsingCommas() {
        Locale.setDefault(Locale.FRANCE)

        val testCases = listOf(
            Case(
                receiver = 5.000001,
                maximumPlacesParam = 2,
                minimumPlacesParam = 5,
                expectedResult = "5,00"
            ),
            Case(
                receiver = 5.000001,
                maximumPlacesParam = 10,
                minimumPlacesParam = 10,
                expectedResult = "5,0000010000"
            ),
            Case(
                receiver = 5.9,
                maximumPlacesParam = 0,
                minimumPlacesParam = 0,
                expectedResult = "6"
            ),
            Case(
                receiver = 5.9999999,
                maximumPlacesParam = 3,
                minimumPlacesParam = 1,
                expectedResult = "6,0"
            )
        )

        runTestCases(testCases)
    }

    @Test(expected = IllegalArgumentException::class)
    fun formatNPlacesTest_Exception() {
        Locale.setDefault(Locale.ENGLISH)

        val testCases = listOf(
            Case(
                receiver = 5.000001,
                maximumPlacesParam = -1,
                minimumPlacesParam = -10,
                expectedResult = "5"
            ),
            Case(
                receiver = 0.0,
                maximumPlacesParam = -1,
                minimumPlacesParam = 1000,
                expectedResult = "5.000001"
            )
        )

        runTestCases(testCases)
    }

    private fun runTestCases(testCases: List<Case>) {
        testCases.forEach { (receiver, maximumPlacesParam, minimumPlacesParam, expectedResult) ->
            val actualResult = receiver.formatUpToNPlaces(maximumPlacesParam, minimumPlacesParam)
            assertEquals(expectedResult, actualResult)
        }
    }
}

/**
 * Test Cases for String.trimLastN extension method
 *
 */
class StringTrimLastNTest {

    private data class Case(
        val receiver: String,
        val nParam: Int,
        val predicateParam: (Char) -> Boolean,
        val breakOnFalseCondition: Boolean,
        val expectedResult: String
    )

    @Test
    fun trimLastN_test() {
        val testCases = listOf(
            Case(receiver = "", nParam = 0, expectedResult = "", predicateParam = {
                it == 'a'
            }, breakOnFalseCondition = true),
            Case(receiver = "", nParam = 0, expectedResult = "", predicateParam = {
                it == 'a'
            }, breakOnFalseCondition = false),
            Case(receiver = "", nParam = 10, expectedResult = "", predicateParam = {
                it == 'a'
            }, breakOnFalseCondition = true),
            Case(receiver = "", nParam = 10, expectedResult = "", predicateParam = {
                it == 'a'
            }, breakOnFalseCondition = false),
            Case(receiver = "", nParam = -1, expectedResult = "", predicateParam = {
                it == 'a'
            }, breakOnFalseCondition = true),
            Case(receiver = "", nParam = -1, expectedResult = "", predicateParam = {
                it == 'a'
            }, breakOnFalseCondition = false),
            Case(receiver = "abcde", nParam = 3, expectedResult = "ab", predicateParam = {
                true
            }, breakOnFalseCondition = true),
            Case(receiver = "abcde", nParam = 3, expectedResult = "ab", predicateParam = {
                true
            }, breakOnFalseCondition = false),
            Case(receiver = "abcde", nParam = 3, expectedResult = "abcde", predicateParam = {
                false
            }, breakOnFalseCondition = true),
            Case(receiver = "abcde", nParam = 3, expectedResult = "abcde", predicateParam = {
                false
            }, breakOnFalseCondition = false),
            Case(receiver = "10.0000", nParam = 10, expectedResult = "10.", predicateParam = {
                it == '0'
            }, breakOnFalseCondition = true),
            Case(receiver = "10.0000", nParam = 10, expectedResult = "1.", predicateParam = {
                it == '0'
            }, breakOnFalseCondition = false),
            Case(
                receiver = "10.909090",
                nParam = 10,
                expectedResult = "10.909090",
                predicateParam = {
                    it == '9'
                },
                breakOnFalseCondition = true
            ),
            Case(receiver = "10.909090", nParam = 10, expectedResult = "10.000", predicateParam = {
                it == '9'
            }, breakOnFalseCondition = false)
        )

        testCases.forEach { (receiver, nParam, predicateParam, breakOnFalseCondition, expectedResult) ->
            val actualResult =
                receiver.trimLastN(nParam, predicateParam, breakOnNegative = breakOnFalseCondition)
            assertEquals(expectedResult, actualResult)
        }
    }
}

/**
 * Test Cases for String.removeAt(Int)
 *
 */
class StringRemoveAtTest {

    data class RemoveAtTestCase(
        val receiver: String,
        val indexParam: Int,
        val expectedResult: String
    )

    @Test
    fun stringRemoveAtTest_HappyPath() {
        val testCases = listOf(
            RemoveAtTestCase(receiver = "abcde", indexParam = 0, expectedResult = "bcde"),
            RemoveAtTestCase(receiver = "abcde", indexParam = 4, expectedResult = "abcd"),
            RemoveAtTestCase(receiver = "abcde", indexParam = 2, expectedResult = "abde"),
            RemoveAtTestCase(receiver = "a", indexParam = 0, expectedResult = "")
        )

        testCases.forEach { (receiver, indexParam, expectedResult) ->
            val actualResult = receiver.removeAt(indexParam)
            assertEquals(expectedResult, actualResult)
        }
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun stringRemoveAtTest_Exception() {
        val testCases = listOf(
            RemoveAtTestCase(receiver = "abcde", indexParam = 10, expectedResult = ""),
            RemoveAtTestCase(receiver = "", indexParam = 0, expectedResult = ""),
            RemoveAtTestCase(receiver = "", indexParam = -1, expectedResult = "")
        )

        testCases.forEach { (receiver, indexParam, expectedResult) ->
            val actualResult = receiver.removeAt(indexParam)
            assertEquals(expectedResult, actualResult)
        }
    }
}
