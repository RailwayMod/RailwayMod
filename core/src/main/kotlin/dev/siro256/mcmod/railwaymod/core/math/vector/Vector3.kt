package dev.siro256.mcmod.railwaymod.core.math.vector

interface Vector3<T, U: Vector3<T, U>>: Vector<T, U> {
    fun toVector3i(): Vector3i

    fun toVector3l(): Vector3l

    fun toVector3f(): Vector3f

    fun toVector3d(): Vector3d
}
