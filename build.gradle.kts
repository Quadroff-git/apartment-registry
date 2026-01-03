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

@rem Set UTF-8 encoding for Russian text
chcp 65001 > nul
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

    implementation("org.apache.tomcat.embed:tomcat-embed-core:8.0.48")
    implementation("org.apache.tomcat.embed:tomcat-embed-jasper:8.0.48")
    implementation("tools.jackson.jr:jackson-jr-objects:3.0.1")
    compileOnly("javax.servlet:javax.servlet-api:4.0.1")
}

tasks.test {
    useJUnitPlatform()
}