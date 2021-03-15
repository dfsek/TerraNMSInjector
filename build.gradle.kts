plugins {
    java
}

group = "com.dfsek"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://repo.codemc.io/repository/nms/") }
    maven { url = uri("https://repo.codemc.org/repository/maven-public") }
    flatDir {
        dirs("../libs")
    }
}

dependencies {
    implementation("org.jetbrains:annotations:19.0.0")
    testCompile("junit", "junit", "4.12")
    compileOnly("org.spigotmc:spigot:1.16.5-R0.1-SNAPSHOT")
    compileOnly(fileTree("./libs/"))
}
