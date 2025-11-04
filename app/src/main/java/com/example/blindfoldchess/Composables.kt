package com.example.blindfoldchess

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MenuButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,

        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),

        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF48474F),
            contentColor = Color.White
        ),

        shape = RoundedCornerShape(8.dp),

        contentPadding = PaddingValues(16.dp)
    ) {
        Text(text = text)
    }
}


@Composable
fun AnswerButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(2.dp, Color.Gray),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Gray)
    ) {
        Text(text)
    }
}

@Composable
fun TwoButtonsChoice(
    upperText: String,
    bottomText: String,
    upperOnClick: () -> Unit,
    bottomOnClick: () -> Unit,
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AnswerButton(
            text = upperText,
            onClick = upperOnClick,
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        )
        AnswerButton(
            text = bottomText,
            onClick = bottomOnClick,
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        )
    }
}