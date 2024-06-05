package com.example.mindofthevalley

import com.example.mindofthevalley.data.Category
import com.example.mindofthevalley.data.Course
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DataClassUnitTest {
    private lateinit var gson: Gson

    /**
     * Just a simple unit test for the GSON-JSON converter
     * Since it's just the converter, don't need unit tests for all ~/data/~ objects
     */

    @Before
    fun setUp() {
        gson = Gson()
    }

    @Test
    fun testCategoryValidJson() {
        val json = """{"name": "Steven Hawking"}"""
        val category = gson.fromJson(json, Category::class.java)

        assertNotNull(category)
        assertEquals("Steven Hawking", category.categoryName)
    }

    @Test
    fun testCategoryNullName() {
        val json = """{"name": null}"""
        val category = gson.fromJson(json, Category::class.java)

        assertNotNull(category)
        assertEquals(null, category.categoryName)
    }

    @Test
    fun testCategoryInvalidJson() {
        val json = """{"name"": 123}""" //should not be a number
        assertThrows(JsonSyntaxException::class.java) {
            gson.fromJson(json, Category::class.java)
        }
    }

    @Test
    fun testDeserializeValidJson() {
        val json = """
            {
                "type": "Course",
                "title": "Exploring the art of Meditation",
                "coverAsset": {"url": "https://mindvalley.com/asset/meditation.jpg"},
                "channel": {"title": "Mind Opener"}
            }
        """
        val course = gson.fromJson(json, Course::class.java)

        assertNotNull(course)
        assertEquals("Course", course.type)
        assertEquals("Exploring the art of Meditation", course.title)
        assertNotNull(course.coverAsset)
        assertEquals("https://mindvalley.com/asset/meditation.jpg", course.coverAsset?.coverUrl)
        assertNotNull(course.channelMini)
        assertEquals("Mind Opener", course.channelMini?.title)
    }

    @Test
    fun testDeserializeNullFields() {
        val json = """
            {
                "type": null,
                "title": null,
                "coverAsset": null,
                "channel": null
            }
        """
        val course = gson.fromJson(json, Course::class.java)

        assertNotNull(course)
        assertNull(course.type)
        assertNull(course.title)
        assertNull(course.coverAsset)
        assertNull(course.channelMini)
    }

    @Test
    fun testDeserializeInvalidJson() {
        val json = """
            {
                "type": 123,
                "title": "Exploring the art of Meditation",
                "coverAsset": {"url"": "https://mindvalley.com/asset/meditation.jpg"},
                "channel": {"title": "Mind Opener"}
            }
        """ // 'type' should not be a number

        assertThrows(JsonSyntaxException::class.java) {
            gson.fromJson(json, Course::class.java)
        }
    }
}