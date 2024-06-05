package com.example.mindofthevalley

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.getSystemService
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mindofthevalley.adapters.CourseItemAdapter
import com.example.mindofthevalley.data.ChannelMini
import com.example.mindofthevalley.data.Course
import com.example.mindofthevalley.data.CoverAsset
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AdapterClassUnitTest {
    private lateinit var adapter: CourseItemAdapter
    private lateinit var courseList: List<Course>

    @Before
    fun setUp() {
        //since we limit to 6 in the requirements, add 7
        courseList = listOf(
            Course("Course", "Course Title 1", CoverAsset("https://mindvalley.com/asset/meditation.jpg"), ChannelMini("Course Subtitle 1")),
            Course("Course", "Course Title 2", CoverAsset("https://mindvalley.com/asset/meditation.jpg"), ChannelMini("Course Subtitle 2")),
            Course("Course", "Course Title 3", CoverAsset("https://mindvalley.com/asset/meditation.jpg"), ChannelMini("Course Subtitle 3")),
            Course("Course", "Course Title 4", CoverAsset("https://mindvalley.com/asset/meditation.jpg"), ChannelMini("Course Subtitle 4")),
            Course("Course", "Course Title 5", CoverAsset("https://mindvalley.com/asset/meditation.jpg"), ChannelMini("Course Subtitle 5")),
            Course("Course", "Course Title 6", CoverAsset("https://mindvalley.com/asset/meditation.jpg"), ChannelMini("Course Subtitle 6")),
            Course("Course", "Course Title 7", CoverAsset("https://mindvalley.com/asset/meditation.jpg"), ChannelMini("Course Subtitle 7")),
        )
        adapter = CourseItemAdapter(courseList)
    }

    @Test
    fun getItemCountTest() {
        val expectedCount = if (courseList.size > 6) 6 else courseList.size
        assertEquals(expectedCount, adapter.itemCount)
    }
}