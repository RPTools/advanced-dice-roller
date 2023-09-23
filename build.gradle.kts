plugins {
    antlr
    `java-library`
    jacoco
    application
    id("com.diffplug.spotless") version "6.21.0"
    `maven-publish`
    base
}
group = "net.rptools.advanced-dice"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    antlr("org.antlr:antlr4:4.13.0")
    implementation("org.reflections:reflections:0.10.2")
    implementation("org.apache.commons", "commons-text", "1.6")
    implementation("org.apache.commons", "commons-text", "1.6")
    implementation("com.github.jknack:handlebars:4.1.2")
    implementation("org.apache.logging.log4j", "log4j-api", "2.11.0");
    implementation("org.apache.logging.log4j", "log4j-1.2-api", "2.11.0");
    testImplementation("org.mockito:mockito-core:3.3.3");
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2");
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2");
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.2");
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.3");
}


configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_19
}

java {
    val mainJavaSourceSet: SourceDirectorySet = sourceSets.getByName("main").java
    mainJavaSourceSet.srcDir("build/generated/antlr/main")
    println(mainJavaSourceSet.srcDirs)
}

spotless {

    java {
        target("src/**/*.java")
        targetExclude("src/main/gen/*", "src/main/antlr/gen/*")
        licenseHeaderFile(file("spotless.license.java"))
        googleJavaFormat("1.16.0")
    }

    format("misc") {
        target("**/*.gradle", "**/.gitignore")

        // spotless has built-in rules for most basic formatting tasks
        trimTrailingWhitespace()
        indentWithSpaces(2)
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    //jvmArgs("--enable-preview")
    testLogging {
        events("passed", "skipped", "failed", "standard_error", "standard_out")
    }
}


application {
    mainClass.set("net.rptools.maptool.advanceddice.Main")
}

tasks.generateGrammarSource {
    maxHeapSize = "64m"
    arguments = arguments + listOf(
            "-visitor",
            "-long-messages",
            "-package", "net.rptools.maptool.advanceddice.parser",
            "-Xexact-output-dir"
    )
    // need to specify the exact output directory for the generated files for plugin to work with
    // split grammar files
    outputDirectory = File("build/generated/antlr/main/net/rptools/maptool/advanceddice/parser")
}

publishing {
    publications {
        create<MavenPublication>("advanced-dice-rolls") {
            from(components["java"])
            pom {
                groupId = "net.rptools.maptool"
                artifactId = "advanced-rolls"
            }
        }
    }
}
