package com.example.blindfoldchess

import android.app.Application

class BlindfoldChessApp : Application() {

    lateinit var tts: TTS

    override fun onCreate() {
        super.onCreate()
        tts = TTS(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        tts.shutdown()
    }
}
