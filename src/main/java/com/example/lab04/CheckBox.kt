package com.example.lab04

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CheckBox(label : String, checked:Boolean, onCheckedChange:(Boolean) -> Unit, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Checkbox(checked = checked,
            onCheckedChange = onCheckedChange)
        Text(text = label)
    }
}

@Preview
@Composable
private fun CheckBoxPrev() {
    CheckBox(
        label = "모자",
        checked = false,
        onCheckedChange = {}
    )
}