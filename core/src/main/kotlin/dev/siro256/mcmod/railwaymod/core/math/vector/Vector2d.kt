package dev.siro256.mcmod.railwaymod.core.math.vector

import kotlin.math.pow
import kotlin.math.sqrt

data class Vector2d(var x: Double, var y: Double): Vector2<Double, Vector2d> {
    override fun toArray() = arrayOf(x, y)

    override fun toList() = listOf(x, y)

    override fun length() = sqrt(x.pow(2) + y.pow(2))

    override fun plus(other: Vector2d) = Vector2d(x + other.x, y + other.y)

    override fun minus(other: Vector2d) = Vector2d(x - other.x, y - other.y)

    override fun times(other: Vector2d) = Vector2d(x * other.x, y * other.y)

    override fun div(other: Vector2d) = Vector2d(x / other.x, y / other.y)

    override fun rem(other: Vector2d) = Vector2d(x % other.x, y % other.y)

    override fun toVector2i() = Vector2i(x.toInt(), y.toInt())

    override fun toVector2l() = Vector2l(x.toLong(), y.toLong())

    override fun toVector2f() = Vector2f(x.toFloat(), y.toFloat())

    override fun toVector2d() = Vector2d(x, y)
}
