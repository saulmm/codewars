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
include(":feature:home")
include(":feature:kata-detail")
