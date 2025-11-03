package com.example.blindfoldchess

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blindfoldchess.ui.theme.BlindfoldChessTheme
import com.github.bhlangonijr.chesslib.Board

class MainActivity : ComponentActivity() {
    lateinit var tts: TTS
    private val board = Board()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tts = TTS(this)

        // Example of how to use chesslib
        val move = "e4"
        board.doMove(move)
        Log.d("Chess", "Board FEN: ${board.fen}")

        setContent {
            BlindfoldChessTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val context = LocalContext.current
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            "Blindfold Chess",
                            modifier = Modifier.align(Alignment.TopCenter).padding(top = 32.dp),
                            fontSize = 30.sp
                        )
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(onClick = { context.startActivity(Intent(context, ModeSelectionActivity::class.java)) }) {
                                Text("Start")
                            }
                            Button(onClick = { context.startActivity(Intent(context, TutorialActivity::class.java)) }) {
                                Text("Tutorial")
                            }
                        }
                    }


                }
            }
        }
    }

    override fun onDestroy() {
        tts.shutdown()
        super.onDestroy()
    }
}
