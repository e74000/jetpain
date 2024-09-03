package com.github.e74000.jetpain.listeners

import com.github.e74000.jetpain.BouncingLainWindow
import com.github.e74000.jetpain.MusicPlayer
import com.intellij.openapi.application.ApplicationActivationListener
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.IdeFrame
import com.intellij.openapi.wm.WindowManager
import javax.swing.ImageIcon
import javax.swing.Timer
import java.awt.Color

internal class JetPainApplicationActivationListener : ApplicationActivationListener {

    private var timer: Timer? = null
    private var lainWindow: BouncingLainWindow? = null
    private val colors = arrayOf(
        Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN,
        Color.CYAN, Color.BLUE, Color.MAGENTA, Color.PINK
    )
    private var currentIndex = 0

    override fun applicationActivated(ideFrame: IdeFrame) {
        thisLogger().warn("Application activated. Attempting to play music and start effects.")
        MusicPlayer.playMusic()

        val project = ideFrame.project
        if (project != null) {
            startGlobalRaveEffect(project)
            startBouncingLainEffect(project, "/images/lain.gif")
        }
    }

    override fun applicationDeactivated(ideFrame: IdeFrame) {
        thisLogger().warn("Application deactivated. Stopping music and effects.")
        MusicPlayer.stopMusic()
        stopRaveEffect()

        // Dispose of the Lain window
        lainWindow?.dispose()
        lainWindow = null
    }

    private fun startGlobalRaveEffect(project: Project) {
        val window = WindowManager.getInstance().getFrame(project)
        if (window != null) {
            val contentPane = window.contentPane
            timer = Timer(182) {
                applyRaveEffectToAllComponents(contentPane)
                currentIndex = (currentIndex + 1) % colors.size
            }
            timer?.start()
        }
    }

    private fun applyRaveEffectToAllComponents(component: java.awt.Component) {
        component.background = colors[currentIndex]
        if (component is java.awt.Container) {
            for (child in component.components) {
                applyRaveEffectToAllComponents(child)
            }
        }
    }

    private fun stopRaveEffect() {
        timer?.stop()
    }

    private fun startBouncingLainEffect(project: Project, gifPath: String) {
        val window = WindowManager.getInstance().getFrame(project)
        if (window != null) {
            val gifIcon = ImageIcon(this::class.java.getResource(gifPath))
            lainWindow = BouncingLainWindow(gifIcon)

            lainWindow?.isVisible = true // Show the Lain window
        }
    }
}
