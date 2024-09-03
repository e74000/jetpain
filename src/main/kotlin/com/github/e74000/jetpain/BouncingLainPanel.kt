package com.github.e74000.jetpain

import javax.swing.Icon
import javax.swing.JWindow
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Toolkit
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.Timer

class BouncingLainWindow(private val gifIcon: Icon) : JWindow(), ActionListener {

    private var xDirection = 1
    private var yDirection = 1
    private val speed = 2
    private val gifWidth = gifIcon.iconWidth
    private val gifHeight = gifIcon.iconHeight

    private var lastX = 0
    private var lastY = 0

    init {
        size = Dimension(gifWidth, gifHeight)
        isAlwaysOnTop = true
        background = Color(0, 0, 0, 0) // Transparent background

        // Position the window at the top-left corner initially
        setLocation(0, 0)

        val timer = Timer(16, this) // Approx. 60 FPS for smooth animation
        timer.start()
    }

    override fun paint(g: Graphics) {
        super.paint(g)
        gifIcon.paintIcon(this, g, 0, 0) // Draw the GIF at the top-left corner of the window
    }

    override fun actionPerformed(e: ActionEvent) {
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        val topOffset = 22 // Approximate height of the macOS menu bar; adjust if necessary

        // Calculate the next position
        var newX = x + xDirection * speed
        var newY = y + yDirection * speed

        // Detect edge bounce by checking if movement has stopped
        if (newX == lastX) {
            xDirection *= -1
            newX = x + xDirection * speed
        }
        if (newY == lastY) {
            yDirection *= -1
            newY = y + yDirection * speed
        }

        // Ensure we don't go beyond the screen boundaries
        if (newX <= 0 || newX + gifWidth >= screenSize.width) {
            xDirection *= -1
            newX = x + xDirection * speed
        }
        if (newY <= topOffset || newY + gifHeight >= screenSize.height) {
            yDirection *= -1
            newY = y + yDirection * speed
        }

        // Update the last known position
        lastX = newX
        lastY = newY

        // Update the position of the window
        setLocation(newX, newY)
    }
}
