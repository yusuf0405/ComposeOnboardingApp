package com.joseph.composeonboardingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.joseph.composeonboardingapp.ui.theme.BottomCardShape
import com.joseph.composeonboardingapp.ui.theme.ComposeOnboardingAppTheme
import com.joseph.composeonboardingapp.ui.theme.Poppins
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ComposeOnboardingAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        val items = OnBoardingData.allItems()

                        val pagerState = rememberPagerState(
                            pageCount = items.size,
                            initialOffscreenLimit = 2,
                            infiniteLoop = false,
                            initialPage = 0
                        )
                        OnBoardingPager(
                            items = items,
                            pagerState = pagerState
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun OnBoardingPager(
    modifier: Modifier = Modifier,
    items: List<OnBoardingData>,
    pagerState: PagerState,
) {

    val scope = rememberCoroutineScope()
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            HorizontalPager(state = pagerState) { page ->
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .background(items[page].backgroundColor),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Image(
                        painter = painterResource(id = items[page].imageResId),
                        contentDescription = null,
                        modifier = modifier.fillMaxWidth()
                    )
                }
            }
        }

        Box(modifier = modifier.align(Alignment.BottomCenter)) {
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .height(340.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 0.dp,
                ),
                shape = BottomCardShape.large
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        PagerIndicator(
                            items = items,
                            currentPage = pagerState.currentPage
                        )
                        Text(
                            text = items[pagerState.currentPage].title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp, end = 30.dp),
                            color = items[pagerState.currentPage].mainColor,
                            fontFamily = Poppins,
                            textAlign = TextAlign.Right,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            text = items[pagerState.currentPage].desc,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = 15.dp,
                                    start = 20.dp,
                                    end = 20.dp
                                ),
                            color = Color.Gray,
                            fontFamily = Poppins,
                            textAlign = TextAlign.Center,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraLight
                        )
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(
                                horizontal = 20.dp,
                                vertical = 50.dp
                            )
                    ) {
                        val item = items[pagerState.currentPage]
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            if (pagerState.currentPage != 2) {
                                TextButton(onClick = {
                                    //skip
                                }) {
                                    Text(
                                        text = "Skip Now",
                                        color = Color(0xFF292D32),
                                        fontFamily = Poppins,
                                        textAlign = TextAlign.Right,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }

                                OutlinedButton(
                                    onClick = {
                                        scope.launch {
                                            pagerState.scrollToPage(
                                                pagerState.currentPage + 1,
                                                pageOffset = 0f
                                            )
                                        }
                                    },
                                    border = BorderStroke(
                                        14.dp,
                                        item.mainColor
                                    ),
                                    shape = RoundedCornerShape(50), // = 50% percent
                                    //or shape = CircleShape
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = item.mainColor),
                                    modifier = Modifier.size(65.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_right_arrow),
                                        contentDescription = "",
                                        tint = item.mainColor,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            } else {
                                Button(
                                    onClick = {
                                        //show home screen
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = item.mainColor
                                    ),
                                    contentPadding = PaddingValues(vertical = 12.dp),
                                    elevation = ButtonDefaults.elevatedButtonElevation(
                                        defaultElevation = 0.dp
                                    )
                                ) {
                                    Text(
                                        text = "Get Started",
                                        color = Color.White,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PagerIndicator(
    items: List<OnBoardingData>,
    currentPage: Int
) {
    Row(
        modifier = Modifier.padding(top = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(items.size) {
            Indicator(
                isSelected = it == currentPage,
                color = items[it].mainColor
            )
        }
    }
}

@Composable
private fun Indicator(
    isSelected: Boolean,
    color: Color
) {
    val width = animateDpAsState(targetValue = if (isSelected) 40.dp else 10.dp)
    Box(
        modifier = Modifier
            .padding(4.dp)
            .height(10.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(
                color = if (isSelected) color else Color.Gray.copy(alpha = 0.5f)
            )
    )

}









