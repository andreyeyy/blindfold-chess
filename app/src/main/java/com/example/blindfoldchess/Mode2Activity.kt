package com.example.blindfoldchess

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.compose.setContent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.blindfoldchess.ui.theme.BlindfoldChessTheme
import kotlin.random.Random

class Mode2Activity : ComponentActivity() {

    private lateinit var tts: TTS
    private val handler = Handler(Looper.getMainLooper())

    private var isLegalMove = false

    private var labelText by mutableStateOf("")

    private fun checkMove(move: Pair<Int, Int>): Boolean{
        return move.first >= 0 && move.first < 8 && move.second >= 0 && move.second < 8
    }

    private var canClick = false

    private fun generateCandidateMoves(
        fileFrom: Int,
        rankFrom: Int,
        directions: Array<Pair<Int, Int>>,
        maxDistance: Int = 1
    ): List<Pair<Int, Int>> {
        val candidates = mutableListOf<Pair<Int, Int>>()
        for (distance in 1..maxDistance) {
            for ((df, dr) in directions) {
                val newFile = fileFrom + df * distance
                val newRank = rankFrom + dr * distance
                val newMove = Pair(newFile, newRank)
                if (checkMove(newMove)) candidates.add(newMove)
            }
        }
        return candidates
    }

    private fun pickMove(
        legal: Boolean,
        fileFrom: Int,
        rankFrom: Int,
        directions: Array<Pair<Int, Int>>,
        maxDistance: Int = 1
    ): Pair<Int, Int>{

        val offset = arrayOf(
            Pair(1, 0), Pair(1, 1), Pair(0, 1), Pair(-1, 1), Pair(-1, 0), Pair(-1, -1), Pair(0, -1), Pair(1, -1)
        )

        val candidateMoves = generateCandidateMoves(fileFrom, rankFrom, directions, maxDistance)
        val winnerMove = candidateMoves.random()

        if (legal) return winnerMove

        val candidateIllegalMoves = generateCandidateMoves(winnerMove.first, winnerMove.second, offset, 1)
        val winnerIllegalMove = candidateIllegalMoves.random()

        return winnerIllegalMove

    }

    private val sayMoveDelay = 1000L
    private fun sayNextMove(){

        val pieces = arrayOf("knight", "bishop", "queen")
        val chosenPiece = pieces.random()
        isLegalMove = Random.nextBoolean()

        val fileFrom = Random.nextInt(0, 8)
        val rankFrom = Random.nextInt(0, 8)

        var moveTo = Pair(-1, -1)

        when (chosenPiece){
            "knight" -> {
                val knightMoves = arrayOf(
                    Pair(2, 1), Pair(1, 2), Pair(-1, 2), Pair(-2, 1), Pair(-2, -1), Pair(-1, -2), Pair(1, -2), Pair(2, -1)
                )

                moveTo = pickMove(isLegalMove, fileFrom, rankFrom, knightMoves, 1)

            }
            "bishop" -> {
                val bishopMoves = arrayOf(
                    Pair(1, 1), Pair(1, -1), Pair(-1, 1), Pair(-1, -1)
                )

                moveTo = pickMove(isLegalMove, fileFrom, rankFrom, bishopMoves, 8)

            }
            "queen" -> {
                val queenMoves = arrayOf(
                    Pair(1, 0), Pair(1, 1), Pair(0, 1), Pair(-1, 1), Pair(-1, 0), Pair(-1, -1), Pair(0, -1), Pair(1, -1)
                )

                moveTo = pickMove(isLegalMove, fileFrom, rankFrom, queenMoves, 8)
            }
        }

        val phrase = "$chosenPiece ${squareToStr(fileFrom, rankFrom)} to ${squareToStr(moveTo.first, moveTo.second)}"
        labelText = "Is $phrase a legal move?"
        tts.say(phrase)
        canClick = true
    }

    private fun submitAnswer(isLegal: Boolean){
        if (canClick) {
            canClick = false
            if (isLegal == isLegalMove) {
                tts.say("Yes")
                labelText = "Yes!"
            } else {
                tts.say("No")
                labelText = "No"
            }

            handler.postDelayed({ sayNextMove() }, sayMoveDelay)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tts = (application as BlindfoldChessApp).tts

        handler.postDelayed({ sayNextMove() }, sayMoveDelay)

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
                    TwoButtonsChoice(
                        upperText =  "Legal move",
                        bottomText =  "Illegal move",
                        upperOnClick = { submitAnswer(true) },
                        bottomOnClick = { submitAnswer(false) })
                }
            }

        }
    }
}