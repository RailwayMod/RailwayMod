package dev.siro256.mcmod.railwaymod.core.math.vector

data class Vector2i(var x: Int, var y: Int): Vector2<Int, Vector2i> {
    override fun toArray() = arrayOf(x, y)

    override fun toList() = listOf(x, y)

    override fun length() = toVector2d().length()

    override fun plus(other: Vector2i) = Vector2i(x + other.x, y + other.y)

    override fun minus(other: Vector2i) = Vector2i(x - other.x, y - other.y)

    override fun times(other: Vector2i) = Vector2i(x * other.x, y * other.y)

    override fun div(other: Vector2i) = Vector2i(x / other.x, y / other.y)

    override fun rem(other: Vector2i) = Vector2i(x % other.x, y % other.y)

    override fun toVector2i() = Vector2i(x, y)

    override fun toVector2l() = Vector2l(x.toLong(), y.toLong())

    override fun toVector2f() = Vector2f(x.toFloat(), y.toFloat())

    override fun toVector2d() = Vector2d(x.toDouble(), y.toDouble())
}
