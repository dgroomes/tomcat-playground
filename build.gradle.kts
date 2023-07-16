plugins {
    java
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.slf4j.api)
    implementation(libs.slf4j.simple)
    implementation(libs.tomcat.embedded)
}

application {
    mainClass.set("dgroomes.Main")
}
