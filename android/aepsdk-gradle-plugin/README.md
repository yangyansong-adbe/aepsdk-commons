# AEPSDK Gradle Plugin

## Overview
This Gradle plugin is designed to simplify the process of configuring and publishing Adobe AEP Android extensions. It streamlines tasks such as generating the POM file, managing dependencies, and configuring various build settings. 

> [!WARNING]
> This plugin is only intended for Adobe extensions and is not be suitable for general use.

## Usage

### Add JitPack repository

To get started, include the JitPack repository in your project's root `build.gradle.kts` file:
```kotlin
// Project's root build.gradle.kts

buildscript {
    repositories {
        maven { url = uri("https://jitpack.io") }
    }
}
```

### Add Plugin dependency

Next, replace `x.x.x` with the appropriate version or commit in the following code snippet within your project's root `build.gradle.kts` file:
```kotlin
// Project's root build.gradle.kts

buildscript {
    dependencies {
        classpath("com.github.adobe:aepsdk-commons:x.x.x")
    }
}
```

### Apply the plugin

Apply the plugin in your module's `build.gradle.kts` file:

```kotlin
// Module's build.gradle.kts

plugins {
    id("aep-library")
}
```

### Plugin Configuration

You can configure the plugin for your extension using the `aepLibrary` DSL. 

```
aepLibrary {
    // Name of the module associated with your project. 
    // If absent, it searches in Gradle properties and fails if not provided (required)
    moduleName = "your_module_name"

    // Version of the module associated with your project. 
    // If absent, it searches in Gradle properties and fails if not provided (required)
    moduleVersion = "your_module_version"

    // Namespace for configuring Android library (required)
    namespace = "your_namespace_here"

    // Indicates whether the library uses Jetpack Compose (optional, default: false)
    compose = false

    // Indicates whether DokkaDocs should be enabled (optional, default: false)
    enableDokkaDoc = false

    // Enables Spotless for linting (optional, default: false)
    enableSpotless = false

    // Configures Spotless to use Prettier for formatting Java code (optional, default: false)
    enableSpotlessPrettierForJava = false

    // Enables Checkstyle (optional, default: false)
    enableCheckStyle = false

    // Disables adding common build dependencies (optional, default: false)
    disableCommonDependencies = false

    // Publishing settings
    publishing {
        // Git repository name associated with your project (required)
        gitRepoName = "your_git_repo_name_here"

        // Maven repository name associated with your project. 
        // If absent, it searches in Gradle properties and fails if not provided (required)
        mavenRepoName = "your_maven_repo_name"

        // Maven repository description associated with your project. 
        // If absent, it searches in Gradle properties and fails if not provided (required)
        mavenRepoDescription = "your_maven_repo_description"

        // Adds Maven dependency
        addMavenDependency("your_group_id_here", "your_artifact_id_here", "your_version_here")

        // Helpers for common AEP dependencies

        // Adds Core extension as dependency
        addCoreDependency("your_version_here")

        // Adds Identity extension as dependency
        addIdentityDependency("your_version_here")

        // Adds Edge extension as dependency
        addEdgeDependency("your_version_here")

        // Adds EdgeIdentity extension as dependency
        addEdgeIdentityDependency("your_version_here")
    }
}
```