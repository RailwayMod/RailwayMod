package dev.siro256.mcmod.railwaymod.core.math.vector

import kotlin.math.pow
import kotlin.math.sqrt

data class Vector3d(var x: Double, var y: Double, var z: Double): Vector3<Double, Vector3d> {
    override fun toArray() = arrayOf(x, y, z)

    override fun toList() = listOf(x, y, z)

    override fun length() = sqrt(x.pow(2) + y.pow(2) + z.pow(2))

    override operator fun plus(other: Vector3d) = Vector3d(x + other.x, y + other.y, z + other.z)

    override operator fun minus(other: Vector3d) = Vector3d(x - other.x, y - other.y, z - other.z)

    override operator fun times(other: Vector3d) = Vector3d(x * other.x, y * other.y, z * other.z)

    override operator fun div(other: Vector3d) = Vector3d(x / other.x, y / other.y, z / other.z)

    override operator fun rem(other: Vector3d) = Vector3d(x % other.x, y % other.y, z % other.z)

    override fun toVector3i() = Vector3i(x.toInt(), y.toInt(), z.toInt())

    override fun toVector3l() = Vector3l(x.toLong(), y.toLong(), z.toLong())

    override fun toVector3f() = Vector3f(x.toFloat(), y.toFloat(), z.toFloat())

    override fun toVector3d() = Vector3d(x, y, z)
}
