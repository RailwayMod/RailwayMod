package dev.siro256.mcmod.railwaymod.core.util

import org.lwjgl.opengl.GL32

inline fun glBegin(mode: Int, task: () -> Unit) {
    GL32.glBegin(mode)
    task.invoke()
    GL32.glEnd()
}
