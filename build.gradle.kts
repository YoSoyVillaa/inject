plugins {
    `java-library`
    `maven-publish`
}

repositories {
    mavenCentral()
}

dependencies {
    api("javax.inject:javax.inject:1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

tasks {
    test {
        useJUnitPlatform()
    }
    jar {
        manifest {
            attributes("Automatic-Module-Name" to "team.unnamed.inject")
        }
    }
}

val repositoryName: String by project
val snapshotRepository: String by project
val releaseRepository: String by project

publishing {
    repositories {
        maven {
            val snapshot = project.version.toString().endsWith("-SNAPSHOT")

            name = repositoryName
            url = if (snapshot) { uri(snapshotRepository) } else { uri(releaseRepository) }
            credentials(PasswordCredentials::class)
        }
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
