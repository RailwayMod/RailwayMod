package dev.siro256.mcmod.railwaymod.core.math.vector

data class Vector2l(var x: Long, var y: Long): Vector2<Long, Vector2l> {
    override fun toArray() = arrayOf(x, y)

    override fun toList() = listOf(x, y)

    override fun length() = toVector2d().length()

    override fun plus(other: Vector2l) = Vector2l(x + other.x, y + other.y)

    override fun minus(other: Vector2l) = Vector2l(x - other.x, y - other.y)

    override fun times(other: Vector2l) = Vector2l(x * other.x, y * other.y)

    override fun div(other: Vector2l) = Vector2l(x / other.x, y / other.y)

    override fun rem(other: Vector2l) = Vector2l(x % other.x, y % other.y)

    override fun toVector2i() = Vector2i(x.toInt(), y.toInt())

    override fun toVector2l() = Vector2l(x, y)

    override fun toVector2f() = Vector2f(x.toFloat(), y.toFloat())

    override fun toVector2d() = Vector2d(x.toDouble(), y.toDouble())
}
