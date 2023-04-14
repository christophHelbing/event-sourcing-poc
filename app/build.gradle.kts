plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.10"
    application
}

repositories {
    mavenCentral()
}

dependencies {
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
