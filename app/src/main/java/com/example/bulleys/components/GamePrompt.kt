package com.example.bulleys.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.bulleys.R

@Composable
fun GamePrompt(
    targetValue: Int,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(text = stringResource(id = R.string.instruction_text))
        Text(
            text = targetValue.toString(),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier,
        )
    }

}