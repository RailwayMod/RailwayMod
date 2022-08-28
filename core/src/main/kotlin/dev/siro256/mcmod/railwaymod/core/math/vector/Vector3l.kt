package dev.siro256.mcmod.railwaymod.core.math.vector

data class Vector3l(var x: Long, var y: Long, var z: Long): Vector3<Long, Vector3l> {
    override fun toArray() = arrayOf(x, y, z)

    override fun toList() = listOf(x, y, z)

    override fun length() = toVector3d().length()

    override operator fun plus(other: Vector3l) = Vector3l(x + other.x, y + other.y, z + other.z)

    override operator fun minus(other: Vector3l) = Vector3l(x - other.x, y - other.y, z - other.z)

    override operator fun times(other: Vector3l) = Vector3l(x * other.x, y * other.y, z * other.z)

    override operator fun div(other: Vector3l) = Vector3l(x / other.x, y / other.y, z / other.z)

    override operator fun rem(other: Vector3l) = Vector3l(x % other.x, y % other.y, z % other.z)

    override fun toVector3i() = Vector3i(x.toInt(), y.toInt(), z.toInt())

    override fun toVector3l() = Vector3l(x, y, z)

    override fun toVector3f() = Vector3f(x.toFloat(), y.toFloat(), z.toFloat())

    override fun toVector3d() = Vector3d(x.toDouble(), y.toDouble(), z.toDouble())
}
