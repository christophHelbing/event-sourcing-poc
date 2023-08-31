plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.10"
    id("io.ktor.plugin") version "2.3.3"
    id("org.jetbrains.dokka") version "1.7.20"
    id(KotlinXSerialization) version Versions.kotlinVersion
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(KotlinXDependencies.kotlinXDateTime)
    implementation(KotlinXDependencies.kotlinXCoroutines)
    implementation(project.dependencies.platform(ArrowDependencies.arrowStack))
    implementation(ArrowDependencies.arrowCore)
    implementation(ArrowDependencies.arrowFxCoroutines)
    implementation(ArrowDependencies.arrowFxStm)
    implementation(ArrowDependencies.arrowOptics)
    implementation(ArrowDependencies.arrowSuspendApp)
    implementation(KTorDependencies.ktorServerCorsJVM)
    implementation(KTorDependencies.ktorServerCoreJVM)
    implementation(KTorDependencies.ktorServerContentNegotiationJVM)
    implementation(KTorDependencies.ktorSerializationKotlinXJsonJVM)
    implementation(KTorDependencies.ktorServerNettyJVM)
    implementation(KTorDependencies.ktorServerAuth)
    implementation(Dependencies.keycloakAuthZClient)
    implementation(Dependencies.keycloakAdapter)
    implementation(Dependencies.logbackClassic)
    implementation(ExposedDependencies.exposedCore)
    implementation(ExposedDependencies.exposedDao)
    implementation(ExposedDependencies.exposedJdbc)
    implementation(ExposedDependencies.exposedKotlinDateTime)
    implementation(Dependencies.okHttp)
    implementation(Dependencies.flyway)
    implementation(Dependencies.luceneCore)
    implementation(Dependencies.luceneCodecs)
    implementation(Dependencies.luceneQueryParser)
    implementation(Dependencies.luceneCommonAnalysis)
    implementation(Dependencies.luceneMemoryModule)
    implementation(Dependencies.luceneBackwardCodecs)
    implementation(Dependencies.jacksonDataformatXml)
    implementation(Dependencies.jacksonModuleKotlin)
    implementation(Dependencies.jacksonDatatypeJSR310)
    implementation(KTorDependencies.ktorClientCore)
    implementation(KTorDependencies.ktorClientOkHttp)
    implementation(KTorDependencies.ktorClientContentNegotiation)
    implementation(KTorDependencies.ktorClientKotlinxSerialization)
    implementation(KTorDependencies.ktorServeression)
    implementation(KTorDependencies.ktorNetworkTlsCertificates)
    implementation(Dependencies.koinCore)
    implementation(Dependencies.koinKtor)
    implementation(Dependencies.koinSlf4j)
    implementation(Dependencies.caffeine)
    dokkaGfmPlugin(Dependencies.dokka)

    testImplementation(TestDependencies.assertK)
    testImplementation(TestDependencies.ktorServerTestsJVM)
    testImplementation(TestDependencies.kotlinTestJUnit)
}

application {
    mainClass.set("com.sevdesk.AppKt")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
