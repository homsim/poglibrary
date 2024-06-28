pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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
    versionCatalogs {
        create("libsApp") {
            from(files("app/gradle/libs.versions.app.toml"))
        }
        create("libsBackend") {
            from(files("backend/gradle/libs.versions.backend.toml"))
        }
    }
}

rootProject.name = "PogLibrary"
include(":app")
include(":backend")
