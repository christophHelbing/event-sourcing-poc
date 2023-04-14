/*
 * Copyright (c) 2023, sevDesk
 *
 * SPDX-License-Identifier: BSD-2-Clause
 */
private object Versions {
    const val arrowStackVersion = "1.2.0-RC"
    const val junitJupiterEngineVersion = "5.9.1"
}

object Dependencies {
    const val arrowStack = "io.arrow-kt:arrow-stack:${Versions.arrowStackVersion}"
    const val arrowCore = "io.arrow-kt:arrow-core"
}

object TestDependencies {
    const val kTestJunit = "org.jetbrains.kotlin:kotlin-test-junit5"
    const val junitJupiterEngine = "org.junit.jupiter:junit-jupiter-engine:${Versions.junitJupiterEngineVersion}"
}
