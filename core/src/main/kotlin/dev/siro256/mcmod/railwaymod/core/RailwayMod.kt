package dev.siro256.mcmod.railwaymod.core

import dev.siro256.mcmod.railwaymod.core.render.RenderDataManager
import dev.siro256.mcmod.railwaymod.core.renderer.RendererTestEntity
import net.minecraft.resources.ResourceLocation

object RailwayMod {
    fun initialize() {
        registerDefaultShaders()
        RendererTestEntity.init()
    }

    private fun registerDefaultShaders() {
        mapOf(
            ResourceLocation(Values.MOD_ID, "test") to Pair("shader/test.vert", "shader/test.frag"),
            ResourceLocation(Values.MOD_ID, "gradation") to Pair("shader/gradation.vert", "shader/gradation.frag")
        ).forEach { (identifier, file) ->
            RenderDataManager.registerShader(
                identifier,
                Thread.currentThread().contextClassLoader.getResourceAsStream(file.first)!!.readBytes().decodeToString(),
                Thread.currentThread().contextClassLoader.getResourceAsStream(file.second)!!.readBytes().decodeToString()
            )
        }
    }
}
