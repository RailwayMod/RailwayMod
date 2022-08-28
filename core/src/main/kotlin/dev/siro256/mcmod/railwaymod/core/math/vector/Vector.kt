package dev.siro256.mcmod.railwaymod.core.math.vector

import kotlin.math.sign

interface Vector<T, U: Vector<T, U>>: Comparable<U> {
    fun toArray(): Array<T>

    fun toList(): List<T>

    fun length(): Double

    operator fun plus(other: U): U

    operator fun minus(other: U): U

    operator fun times(other: U): U

    operator fun div(other: U): U

    operator fun rem(other: U): U

    override fun compareTo(other: U) = sign(length() - other.length()).toInt()
}
