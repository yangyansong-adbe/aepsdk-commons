/*
  Copyright 2024 Adobe. All rights reserved.
  This file is licensed to you under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License. You may obtain a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0
  Unless required by applicable law or agreed to in writing, software distributed under
  the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR REPRESENTATIONS
  OF ANY KIND, either express or implied. See the License for the specific language
  governing permissions and limitations under the License.
*/

package com.adobe.marketing.mobile.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.tasks.PublishToMavenRepository
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.extra
import org.gradle.plugins.signing.Sign
import org.gradle.plugins.signing.SigningExtension
import java.net.URI

class PublishPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        // Apply necessary plugins
        project.plugins.apply(BuildConstants.Plugins.MAVEN_PUBLISH)
        project.plugins.apply(BuildConstants.Plugins.SIGNING)

        project.afterEvaluate {
            configurePublishing(project)
            configureSigning(project)
        }
    }

    private fun configurePublishing(project: Project) {
        val publishingConfig = project.publishConfig
        val publishing = project.extensions.getByType(PublishingExtension::class.java)
        publishing.apply {
            publications {
                create("release", MavenPublication::class.java) {
                    groupId = project.publishGroupId
                    artifactId = project.publishArtifactId
                    version = project.publishVersion

                    artifact(project.moduleAARPath)
                    artifact(project.javadocJar)
                    artifact(project.sourcesJar)

                    pom {
                        name.set(publishingConfig.mavenRepoName.get())
                        description.set(publishingConfig.mavenRepoDescription.get())
                        url.set(BuildConstants.Publishing.DEVELOPER_DOC_URL)

                        licenses {
                            license {
                                name.set(BuildConstants.Publishing.LICENSE_NAME)
                                url.set(BuildConstants.Publishing.LICENSE_URL)
                                distribution.set(BuildConstants.Publishing.LICENSE_DIST)
                            }
                        }

                        developers {
                            developer {
                                id.set(BuildConstants.Publishing.DEVELOPER_ID)
                                name.set(BuildConstants.Publishing.DEVELOPER_NAME)
                                email.set(BuildConstants.Publishing.DEVELOPER_EMAIL)
                            }
                        }

                        val scmConnectionUrl = String.format(BuildConstants.Publishing.SCM_CONNECTION_URL_TEMPLATE, publishingConfig.gitRepoName.get())
                        val scmRepoUrl = String.format(BuildConstants.Publishing.SCM_REPO_URL_TEMPLATE, publishingConfig.gitRepoName.get())

                        scm {
                            connection.set(scmConnectionUrl)
                            developerConnection.set(scmConnectionUrl)
                            url.set(scmRepoUrl)
                        }

                        withXml {
                            val dependenciesNode = asNode().appendNode("dependencies")
                            publishingConfig.mavenDependencies.get().forEach { element ->
                                val dependencyNode = dependenciesNode.appendNode("dependency")
                                dependencyNode.appendNode("groupId", element.groupId)
                                dependencyNode.appendNode("artifactId", element.artifactId)
                                dependencyNode.appendNode("version", element.version)
                            }
                        }
                    }

                    repositories {
                        maven {
                            name = "sonatype"
                            url = URI(project.publishUrl)
                            credentials {
                                username = System.getenv("SONATYPE_USERNAME")
                                password = System.getenv("SONATYPE_PASSWORD")
                            }
                        }
                    }
                }
            }
        }
    }

    private fun configureSigning(project: Project) {
        // Set signing configuration
        project.extra.apply {
            set("signing.gnupg.executable", BuildConstants.Publishing.SIGNING_GNUPG_EXECUTABLE)
            set("signing.gnupg.keyName", BuildConstants.Publishing.SIGNING_GNUPG_KEY_NAME)
            set("signing.gnupg.passphrase", BuildConstants.Publishing.SIGNING_GNUPG_PASSPHRASE)
        }

        project.extensions.configure<SigningExtension> {
            useGpgCmd()

            val publishing = project.extensions.getByType(PublishingExtension::class.java)
            sign(publishing.publications)
        }

        // https://github.com/gradle/gradle/issues/5064 - Gpg does not support SigningExtension.required flag.
        // Skip signing if we are not publishing to MavenCentral.
        project.tasks.withType(Sign::class.java) {
            onlyIf {
                project.tasks.withType(PublishToMavenRepository::class.java).find { project.gradle.taskGraph.hasTask(it) } != null
            }
        }
    }
}
