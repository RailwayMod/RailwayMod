package dev.siro256.mcmod.railwaymod.core.math.vector

interface Vector2<T, U: Vector2<T, U>>: Vector<T, U> {
    fun toVector2i(): Vector2i

    fun toVector2l(): Vector2l

    fun toVector2f(): Vector2f

    fun toVector2d(): Vector2d
}
