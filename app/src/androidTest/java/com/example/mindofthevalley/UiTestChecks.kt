package com.example.mindofthevalley

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers

class UiTestChecks: ViewAssertion {

    override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }

        val recyclerView = view as RecyclerView
        val adapter = recyclerView.adapter
        val adapterItemCount = adapter?.itemCount ?: 0
        MatcherAssert.assertThat(adapterItemCount, Matchers.greaterThan(0))
    }

}