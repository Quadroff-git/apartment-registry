plugins {
    id ("application")
}

version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass = "Main"
}

tasks.withType<CreateStartScripts> {
    doLast {
        // Modify Windows batch script
        val windowsScriptFile = File(outputDir, "${applicationName}.bat")
        var windowsScript = windowsScriptFile.readText()

        // Add DEFAULT_ARGS variable right after the @rem section
        windowsScript = windowsScript.replace(
            "@rem Set local scope for the variables with windows NT shell",
            """@rem Set local scope for the variables with windows NT shell

@rem Command line arguments for db connection
@rem Format: url user password
set DEFAULT_ARGS=jdbc:postgresql:apartment_registry postgres root
"""
        )

        // Use the DEFAULT_ARGS in the execution line
        windowsScript = windowsScript.replace(
            "-classpath \"%CLASSPATH%\" Main %",
            "-classpath \"%CLASSPATH%\" Main %DEFAULT_ARGS% %"
        )

        windowsScriptFile.writeText(windowsScript)
    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.postgresql:postgresql:42.7.8")
}

tasks.test {
    useJUnitPlatform()
}