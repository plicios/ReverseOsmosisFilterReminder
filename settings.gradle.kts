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
rootProject.name = "ReverseOsmosisFilterReminder"
include(":app")
include(":core:database")
include(":core:data")
include(":core:model")
include(":feature:reminder")
include(":feature:filterSetup")
include(":core:mvi")
include(":core:ui")
include(":core:common")
include(":core:navigation")
//include(Modules.reminder)
//include(Modules.filterSetup)