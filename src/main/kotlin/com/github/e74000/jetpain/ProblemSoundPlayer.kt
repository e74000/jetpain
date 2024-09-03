package com.github.e74000.jetpain

import com.intellij.analysis.problemsView.ProblemsListener
import com.intellij.analysis.problemsView.Problem

class ProblemSoundPlayer : ProblemsListener {

    override fun problemAppeared(problem: Problem) {
        // Play the Factorio building destroyed sound when a problem appears
        MusicPlayer.playSound("/music/alert-destroyed.wav", loop=false)
    }

    override fun problemDisappeared(problem: Problem) {
        // nah
    }

    override fun problemUpdated(problem: Problem) {
        // nah
    }
}
