package com.saulmm.codewars.navigation.contract

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

open class ScreenDest(
    private val baseRoute: String,
    open val args: List<ScreenArg<*>> = emptyList(),
) {
    val route: String
        get() = "$baseRoute$argsPath"

    val navArgs: List<NamedNavArgument>
        get() {
            return args.map {
                navArgument(it.argName) {
                    type = it.argType
                    nullable = it.isNullable
                }
            }
        }

     val argsPath: String
        get() {
            return if (args.isNotEmpty()) {
                "?" + args.joinToString("&") {
                    buildString {
                        append(it.argName)
                        append("={")
                        append(it.argName)
                        append("}")
                    }
                }
            } else {
                ""
            }
        }

}