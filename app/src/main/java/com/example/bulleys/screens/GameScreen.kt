package com.example.bulleys.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bulleys.R
import com.example.bulleys.components.GameDetail
import com.example.bulleys.components.GamePrompt
import com.example.bulleys.components.ResultDialog
import com.example.bulleys.components.TargetSlider
import com.example.bulleys.ui.theme.BulleysTheme
import kotlin.math.abs
import kotlin.random.Random

@Composable
fun GameScreen() {
    fun newTargetValue() = Random.nextInt(1, 100)

    var alertIsVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var sliderValue by rememberSaveable {
        mutableStateOf(0.5f)
    }
    var targetValue by rememberSaveable {
        mutableStateOf(newTargetValue())
    }
    val sliderToInt = (sliderValue * 100).toInt()

    var totalScore by rememberSaveable {
        mutableStateOf(0)
    }

    var currentRound by rememberSaveable {
        mutableStateOf(1)
    }

    // remove code smell
    fun differenceAmount() = abs(targetValue - sliderToInt)

    fun pointsForCurrentRound(): Int {
        val maxScore = 100
        val difference: Int = differenceAmount()
        var bouns = 0
        if (difference == 0) {
            bouns = 100
        } else if (bouns == 1) {
            bouns = 50
        }
        return (maxScore - difference) + bouns
    }

    fun alertTitle(): Int {
        val difference = differenceAmount()
        val title: Int = when {
            difference == 0 -> {
                R.string.alert_title_1
            }

            difference < 5 -> {
                R.string.alert_title_2
            }

            difference <= 10 -> {
                R.string.alert_title_3
            }

            else -> {
                R.string.alert_title_4
            }
        }

        return title
    }

    fun startNewGame() {
        totalScore = 0
        currentRound = 1
        sliderValue = 0.5f
        targetValue = newTargetValue()
    }

    Box {

        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.background),
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(R.string.background_image)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.weight(.5f))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.weight(9f)
            ) {
                GamePrompt(targetValue = targetValue)
                TargetSlider(
                    value = sliderValue,
                    valueChanged = { value ->
                        sliderValue = value
                    }
                )
                Button(
                    onClick = {
                        alertIsVisible = true
                        totalScore += pointsForCurrentRound()
                    },
                    shape = MaterialTheme.shapes.medium,
                    contentPadding = PaddingValues(16.dp),
                ) {
                    Text(text = stringResource(id = R.string.hit_me_button_text))
                }
                GameDetail(
                    modifier = Modifier.fillMaxWidth(),
                    totalScore = totalScore,
                    round = currentRound,
                    onStartOver = { startNewGame() }
                )
            }
            Spacer(modifier = Modifier.weight(.5f))
            if (alertIsVisible) {
                ResultDialog(
                    dialogTitle = alertTitle(),
                    hideDialog = { alertIsVisible = false },
                    sliderValue = sliderToInt,
                    points = pointsForCurrentRound(),
                    onRoundIncrement = {
                        currentRound += 1
                        targetValue = newTargetValue()
                    }
                )
            }
        }
    }
}


@Preview(showBackground = true, device = Devices.AUTOMOTIVE_1024p, widthDp = 864, heightDp = 432)
@Composable
fun GreetingPreview() {
    BulleysTheme {
        GameScreen()
    }
}