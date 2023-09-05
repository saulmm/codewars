package com.saulmm.codewars.common.design.system.animation

import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut

val CodewarsExitTransition =
    fadeOut(animationSpec = tween(300, easing = LinearEasing)) +
            scaleOut(
                animationSpec = tween(150, easing = EaseOut),
                targetScale = .9f
            )
