package com.github.e74000.jetpain

import com.intellij.openapi.diagnostic.thisLogger
import java.io.BufferedInputStream
import java.io.InputStream
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip

object MusicPlayer {

    private var musicClip: Clip? = null

    fun playMusic() {
        thisLogger().warn("Attempting to play music...")
        playSound("/music/Caramelldansen.wav", loop = true)
    }

    fun stopMusic() {
        thisLogger().warn("Stopping music...")
        musicClip?.apply {
            stop()
            close()
        }
        musicClip = null
        thisLogger().warn("Music stopped.")
    }

    fun playSound(filePath: String, loop: Boolean = false) {
        thisLogger().warn("Attempting to play sound: $filePath")
        try {
            val audioInputStream: AudioInputStream = getAudioInputStream(filePath)
            val clip = AudioSystem.getClip().apply {
                open(audioInputStream)
                if (loop) {
                    loop(Clip.LOOP_CONTINUOUSLY)
                    musicClip = this
                } else {
                    start()
                }
            }

            if (!loop) {
                clip.addLineListener { event ->
                    if (event.type == javax.sound.sampled.LineEvent.Type.STOP) {
                        clip.close()
                        thisLogger().warn("Sound finished playing: $filePath")
                    }
                }
            }
        } catch (e: Exception) {
            thisLogger().error("Failed to play sound", e)
        }
    }

    private fun getAudioInputStream(filePath: String): AudioInputStream {
        val inputStream: InputStream = this.javaClass.getResourceAsStream(filePath)
            ?: throw IllegalArgumentException("File not found: $filePath")
        
        // Wrap the InputStream with BufferedInputStream to support mark/reset
        val bufferedInputStream = BufferedInputStream(inputStream)
        return AudioSystem.getAudioInputStream(bufferedInputStream)
    }
}
