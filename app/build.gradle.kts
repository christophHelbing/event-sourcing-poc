plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.10"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-core:2.15.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
    implementation(platform(Dependencies.arrowStack))
    implementation(Dependencies.arrowCore)
    testImplementation(TestDependencies.kTestJunit)
    testImplementation(TestDependencies.junitJupiterEngine)
}

application {
    mainClass.set("com.sevdesk.AppKt")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
