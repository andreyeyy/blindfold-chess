package com.example.blindfoldchess

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.blindfoldchess.ui.theme.BlindfoldChessTheme
import kotlin.random.Random
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

class Mode1Activity : ComponentActivity() {

    private val darkSquares = arrayOf(
        "a1", "a3", "a5", "a7", "b2", "b4", "b6", "b8",
        "c1", "c3", "c5", "c7", "d2", "d4", "d6", "d8",
        "e1", "e3", "e5", "e7", "f2", "f4", "f6", "f8",
        "g1", "g3", "g5", "g7", "h2", "h4", "h6", "h8"
    )

    private val lightSquares = arrayOf(
        "a2", "a4", "a6", "a8", "b1", "b3", "b5", "b7",
        "c2", "c4", "c6", "c8", "d1", "d3", "d5", "d7",
        "e2", "e4", "e6", "e8", "f1", "f3", "f5", "f7",
        "g2", "g4", "g6", "g8", "h1", "h3", "h5", "h7"
    )

    private var labelText by mutableStateOf("")

    private lateinit var tts: TTS
    private val handler = Handler(Looper.getMainLooper())

    private var isLightSquare = false

    private var canClick = false

    private val saySquareDelay = 1000L
    private fun sayNextSquare(){
        isLightSquare = Random.nextBoolean()


        val nextSquare = if (isLightSquare){
            lightSquares.random()
        } else {
            darkSquares.random()
        }

        tts.say(nextSquare)

        labelText = "Is $nextSquare light or dark?"

        canClick = true
    }

    private fun submitAnswer(isLight: Boolean){
        if (canClick) {
            canClick = false
            if (isLight == isLightSquare) {
                tts.say("Yes")
                labelText = "Yes!"
            } else {
                tts.say("No")
                labelText = "No"
            }
            handler.postDelayed({ sayNextSquare() }, saySquareDelay)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tts = (application as BlindfoldChessApp).tts

        handler.postDelayed({ sayNextSquare() }, saySquareDelay)

        setContent {
            BlindfoldChessTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = labelText,
                            modifier = Modifier.align(Alignment.TopCenter).padding(top = 24.dp)
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        SquareButton(
                            text = "Light square",
                            onClick = {
                                submitAnswer(true)
                            },
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize()
                        )
                        SquareButton(
                            text = "Dark square",
                            onClick = {
                                submitAnswer(false)
                            },
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun SquareButton(
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
