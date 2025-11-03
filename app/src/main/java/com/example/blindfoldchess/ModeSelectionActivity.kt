package com.example.blindfoldchess

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blindfoldchess.ui.theme.BlindfoldChessTheme

class ModeSelectionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlindfoldChessTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ModeSelectionScreen()
                }
            }
        }
    }
}

@Composable
fun ModeSelectionScreen() {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            "Select mode",
            modifier = Modifier.align(Alignment.TopCenter).padding(top = 32.dp),
            fontSize = 30.sp
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MenuButton(
                text="Square color guessing",
                onClick = {context.startActivity(Intent(context, Mode1Activity::class.java))}
            )
            MenuButton(
                text="Move legality checking",
                onClick = {context.startActivity(Intent(context, Mode2Activity::class.java))}
            )

        }
    }
}
