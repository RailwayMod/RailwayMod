package dev.siro256.mcmod.railwaymod.core.math.vector

data class Vector3f(var x: Float, var y: Float, var z: Float): Vector3<Float, Vector3f> {
    override fun toArray() = arrayOf(x, y, z)

    override fun toList() = listOf(x, y, z)

    override fun length() = toVector3d().length()

    override operator fun plus(other: Vector3f) = Vector3f(x + other.x, y + other.y, z + other.z)

    override operator fun minus(other: Vector3f) = Vector3f(x - other.x, y - other.y, z - other.z)

    override operator fun times(other: Vector3f) = Vector3f(x * other.x, y * other.y, z * other.z)

    override operator fun div(other: Vector3f) = Vector3f(x / other.x, y / other.y, z / other.z)

    override operator fun rem(other: Vector3f) = Vector3f(x % other.x, y % other.y, z % other.z)

    override fun toVector3i() = Vector3i(x.toInt(), y.toInt(), z.toInt())

    override fun toVector3l() = Vector3l(x.toLong(), y.toLong(), z.toLong())

    override fun toVector3f() = Vector3f(x, y, z)

    override fun toVector3d() = Vector3d(x.toDouble(), y.toDouble(), z.toDouble())
}
