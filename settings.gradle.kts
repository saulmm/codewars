pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Codewars"
include(":app")
include(":entity")
include(":services")
include(":services:codewars-api")
include(":common")
include(":common:network")
include(":feature")
include(":feature:challenges")
include(":common:navigation-contract")
include(":common:codewars-design-system")
include(":common:android")
