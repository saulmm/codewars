package com.saulmm.codewars.common.design.system.animation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn

val CodewarsEnterTransition: EnterTransition =
    fadeIn(animationSpec = tween(300, easing = LinearEasing)) +
            scaleIn(animationSpec = tween(150, easing = EaseIn), initialScale = .9f)
