package com.sevdesk.common

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

object ObjectMapper {
    private val mapper = jacksonObjectMapper()
        .registerKotlinModule()

    fun <T> deserialize(source: String, clazz: Class<T>): T = mapper.readValue(source, clazz)
    fun <T> serialize(source: T): String = mapper.writeValueAsString(source)
}

inline fun <reified T> String.deserializeTo(): T =
    ObjectMapper.deserialize(this, T::class.java)

inline fun <reified T> T.serializeToString(): String =
    ObjectMapper.serialize(this)
