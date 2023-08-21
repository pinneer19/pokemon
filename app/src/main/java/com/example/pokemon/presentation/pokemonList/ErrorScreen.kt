package com.example.pokemon.presentation.pokemonList

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokemon.R
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun ErrorScreen(
    isRefreshing: Boolean,
    error: String,
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        onRefresh = onRefresh
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .fillMaxSize()
                .padding(bottom = 60.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ErrorImage(modifier = Modifier.fillMaxSize())
                Text(
                    text = stringResource(id = R.string.error_message),
                    fontSize = 32.sp,
                    color = Color.Red
                )
                Text(
                    modifier = Modifier.padding(top = 10.dp, start = 20.dp, end = 20.dp),
                    text = error,
                    textAlign = TextAlign.Center,
                    lineHeight = 25.sp,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun ErrorImage(modifier: Modifier = Modifier) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Circle(0.05f, 250.dp)
        Circle(0.2f, 200.dp)
        Circle(0.5f, 150.dp)
        Circle(1f, 100.dp)

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.error_icon),
            contentDescription = "Error",
            tint = Color.Red,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(10.dp, Color.Red, CircleShape)
                .background(Color.White)

        )
    }

}

@Composable
fun Circle(alpha: Float, size: Dp) {
    Canvas(modifier = Modifier.size(size), onDraw = {
        drawCircle(alpha = alpha, color = Color.Red)
    })
}