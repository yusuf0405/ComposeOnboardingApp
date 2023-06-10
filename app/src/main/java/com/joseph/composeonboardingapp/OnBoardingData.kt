package com.joseph.composeonboardingapp

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.joseph.composeonboardingapp.ui.theme.ColorBlue
import com.joseph.composeonboardingapp.ui.theme.ColorGreen
import com.joseph.composeonboardingapp.ui.theme.ColorYellow

data class OnBoardingData(
    @DrawableRes val imageResId: Int,
    val title: String,
    val desc: String,
    val backgroundColor: Color,
    val mainColor: Color = ColorBlue
) {

    companion object {
        fun allItems() = arrayListOf(
            OnBoardingData(
                imageResId = R.drawable.fruit,
                title = "Hmmm, Healthy Food",
                desc = "A variety of healthy foods made by the best chefs. Ingredients are easy to find. all delicious flavors can only be found at cookbunda",
                backgroundColor = Color(0xFF0189C5),
                mainColor = Color(0xFF00B5EA)

            ),
            OnBoardingData(
                imageResId = R.drawable.food,
                title = "Fresh Drinks, Stay Fresh",
                desc = "Not only food. we provide clear healthy drink options for you. Fresh taste always accompanies you",
                backgroundColor = Color(0xFFE4AF19),
                mainColor = ColorYellow
            ),
            OnBoardingData(
                imageResId = R.drawable.cooking,
                title = "Let’s Cooking",
                desc = "Are you ready to make a dish for your friends or family? create an account and cook",
                backgroundColor = Color(0xFF96E172),
                mainColor = ColorGreen
            ),
        )
    }

}