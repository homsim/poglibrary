plugins {
    java
    alias(libsBackend.plugins.springBoot)
    alias(libsBackend.plugins.springDependencyManagement)
    alias(libsBackend.plugins.asciidoctor)
}

/*
group = "com.poglibrary"
version = "0.0.1-SNAPSHOT"
 */

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {
    implementation(libsBackend.spring.web)
    implementation(libsBackend.spring.data.jpa)
    implementation(libsBackend.spring.data.rest)
    implementation(libsBackend.spring.test)
    implementation(libsBackend.spring.restdocs.mockmvc)
    // https://mvnrepository.com/artifact/org.json/json
    implementation(libsBackend.org.json)
    compileOnly(libsBackend.lombok)
    runtimeOnly(libsBackend.mariadb.java.client)
    testRuntimeOnly(libsBackend.junit.platform.launcher)
    annotationProcessor(libsBackend.lombok)
}


tasks.withType<Test> {
    useJUnitPlatform()
}

// these tasks makes the backend run in the background which makes development of the app easier
tasks.register("runBackendInBackground_deprecated", JavaExec::class) {
    group = "application"
    description = "Run the backend server"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("com.poglibrary.backend.PogLibrary")
    javaLauncher.set(javaToolchains.launcherFor {
        languageVersion.set(JavaLanguageVersion.of(21))
    })
}

tasks.register("runBackendInBackground") {
    doLast() {
        val javaHome = System.getProperty("java.home")
        val javaExec = File(javaHome, "bin/java").absolutePath
        /* this is not yet ideal:
         * For my system this works because $JAVA_HOME is pointing to jdk-21,
         * but this is not necessarily system-interoperable
         * Also, its very error-prone, because it would be better to get the version from
         * above configurations somehow
         */
        val classpath = sourceSets["main"].runtimeClasspath.asPath
        val mainClass = "com.poglibrary.backend.PogLibrary"

        val processBuilder = ProcessBuilder(javaExec, "-cp", classpath, mainClass, "--warning-mode all")
        processBuilder.directory(projectDir)
        processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT)
        processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT)
        val process = processBuilder.start()

        // Print the PID of the started process for verification
        println("Backend Process started with PID: ${process.pid()}")

        // Ensure the Gradle task does not wait for process termination
        process.waitFor()


        /* ... does not work: for some reason it works when I execute this task from a terminal, i.e.
         * ./gradlew :backend:runBackendInBackground
         * , but when the task is run from android studio the process is kill instantly
         */
    }
}