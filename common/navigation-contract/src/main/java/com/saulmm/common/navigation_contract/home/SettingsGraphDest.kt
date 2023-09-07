package com.saulmm.common.navigation_contract.home

import com.saulmm.common.navigation_contract.ScreenDest

sealed class SettingsGraphDest {
    object Settings: ScreenDest(baseRoute = "$SETTINGS_GRAPH/settings")

    companion object {
        private const val SETTINGS_GRAPH = "settings"
    }
}