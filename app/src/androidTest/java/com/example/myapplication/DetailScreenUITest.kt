package com.example.myapplication

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.myapplication.ui.detail.DetailRoute
import com.example.myapplication.ui.detail.DetailViewModel
import com.example.myapplication.ui.theme.TemplateComposeTheme
import org.junit.Rule
import org.junit.Test

class DetailScreenUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun detailScreenTest() {
        val detailViewModel = DetailViewModel()

        composeTestRule.setContent {
            TemplateComposeTheme {
                DetailRoute(detailViewModel, true)
            }
        }

        composeTestRule.onNodeWithText("Hello Detail!").assertIsDisplayed()
    }
}
