package com.example.workout_companion.viewmodel

import junit.framework.TestCase
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test
import org.junit.runner.RunWith

class EdamamViewModelKtTest : TestCase() {

    @Test
    fun testParseNext() {
        val url: String = "https://api.edamam.com/api/food-database/v2/parser?session=40&app_id=67913c85&app_key=6b0f2ff7f99be8df235bc62e9d582381&ingr=carrot"
        val session: String = parseNext(url)
        val expected: String = "40"
        MatcherAssert.assertThat(session, CoreMatchers.equalTo(expected))
    }
}