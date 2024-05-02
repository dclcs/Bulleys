package com.example.bulleys.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.bulleys.R

@Composable
fun ResultDialog(
    dialogTitle: Int,
    hideDialog: () -> Unit,
    onRoundIncrement: () -> Unit,
    sliderValue: Int,
    points: Int,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = {
            hideDialog()
            onRoundIncrement()
        },
        confirmButton = {
            TextButton(onClick = {
                hideDialog()
                onRoundIncrement()
            }) {
                Text(text = stringResource(id = R.string.result_dialog_button_text))
            }
        },
        title = { Text(text = stringResource(id = dialogTitle)) },
        text = { Text(text = stringResource(id = R.string.result_dialog_message, sliderValue, points)) },
    )
}
