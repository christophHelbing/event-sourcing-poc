object ArrowDependencies {
    val arrowStack = "io.arrow-kt:arrow-stack:${Versions.arrowVersion}"
    val arrowCore = "io.arrow-kt:arrow-core"
    val arrowFxCoroutines = "io.arrow-kt:arrow-fx-coroutines"
    val arrowFxStm = "io.arrow-kt:arrow-fx-stm"
    val arrowOptics = "io.arrow-kt:arrow-optics"
    val arrowSuspendApp = "io.arrow-kt:suspendapp:${Versions.arrowSuspendApp}"
    val arrowCoreJs = "io.arrow-kt:arrow-core-js:${Versions.arrowVersion}"
}

object KTorDependencies {
    val ktorServerCorsJVM = "io.ktor:ktor-server-cors-jvm:${Versions.ktorVersion}"
    val ktorServerCoreJVM = "io.ktor:ktor-server-core-jvm:${Versions.ktorVersion}"
    val ktorServerContentNegotiationJVM =
        "io.ktor:ktor-server-content-negotiation-jvm:${Versions.ktorVersion}"
    val ktorSerializationKotlinXJsonJVM =
        "io.ktor:ktor-serialization-kotlinx-json-jvm:${Versions.ktorVersion}"
    val ktorServerNettyJVM = "io.ktor:ktor-server-netty-jvm:${Versions.ktorVersion}"
    val ktorServerAuth = "io.ktor:ktor-server-auth:${Versions.ktorVersion}"
    val ktorClientCore = "io.ktor:ktor-client-core:${Versions.ktorVersion}"
    val ktorClientOkHttp = "io.ktor:ktor-client-okhttp:${Versions.ktorVersion}"
    val ktorClientContentNegotiation = "io.ktor:ktor-client-content-negotiation:${Versions.ktorVersion}"
    val ktorClientKotlinxSerialization = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktorVersion}"
    val ktorNetworkTlsCertificates = "io.ktor:ktor-network-tls-certificates:${Versions.ktorVersion}"
    val ktorServeression = "io.ktor:ktor-server-sessions:${Versions.ktorVersion}"
}

object ExposedDependencies {
    val exposedCore = "org.jetbrains.exposed:exposed-core:${Versions.exposedVersion}"
    val exposedDao = "org.jetbrains.exposed:exposed-dao:${Versions.exposedVersion}"
    val exposedJdbc = "org.jetbrains.exposed:exposed-jdbc:${Versions.exposedVersion}"
    val exposedKotlinDateTime = "org.jetbrains.exposed:exposed-kotlin-datetime:${Versions.exposedVersion}"
}

object KotlinXDependencies {
    val kotlinXCoroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersion}"
    val kotlinXDateTime = "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.kotlinXDateTime}"
}

object Dependencies {
    val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlinVersion}"
    val keycloakAuthZClient = "org.keycloak:keycloak-authz-client:${Versions.keycloakVersion}"
    val keycloakAdapter = "org.keycloak:keycloak-installed-adapter:${Versions.keycloakVersion}"
    val logbackClassic = "ch.qos.logback:logback-classic:${Versions.logbackVersion}"
    val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttpVersion}"
    val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshiVersion}"
    val flyway = "org.flywaydb:flyway-core:${Versions.flywayVersion}"
    val jacksonDataformatXml = "com.fasterxml.jackson.dataformat:jackson-dataformat-xml:${Versions.jacksonDataformatXMLVersion}"
    val jacksonModuleKotlin = "com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.jacksonDataformatXMLVersion}"
    val jacksonDatatypeJSR310 = "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${Versions.jacksonDataformatXMLVersion}"
    val luceneCore = "org.apache.lucene:lucene-core:${Versions.luceneCoreVersion}"
    val luceneCodecs = "org.apache.lucene:lucene-codecs:${Versions.luceneCoreVersion}"
    val luceneCommonAnalysis = "org.apache.lucene:lucene-analysis-common:${Versions.luceneCoreVersion}"
    val luceneBackwardCodecs = "org.apache.lucene:lucene-backward-codecs:${Versions.luceneCoreVersion}"
    val luceneQueryParser = "org.apache.lucene:lucene-queryparser:${Versions.luceneCoreVersion}"
    val luceneMemoryModule = "org.apache.lucene:lucene-memory:${Versions.luceneCoreVersion}"
    val koinCore = "io.insert-koin:koin-core:${Versions.koinVersion}"
    val koinKtor = "io.insert-koin:koin-ktor:${Versions.koinVersion}"
    val koinSlf4j = "io.insert-koin:koin-logger-slf4j:${Versions.koinVersion}"
    val caffeine = "com.github.ben-manes.caffeine:caffeine:${Versions.caffeineVersion}"
    val dokka = "org.jetbrains.dokka:kotlin-as-java-plugin:${Versions.dokkaVersion}"
}

object RuntimeDependencies {
}

object TestDependencies {
    val assertK = "com.willowtreeapps.assertk:assertk-jvm:${Versions.assertKVersion}"
    val ktorServerTestsJVM = "io.ktor:ktor-server-tests-jvm:${Versions.ktorVersion}"
    val kotlinTestJUnit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlinVersion}"
    val mockk = "io.mockk:mockk:${Versions.mockkVersion}"
}
