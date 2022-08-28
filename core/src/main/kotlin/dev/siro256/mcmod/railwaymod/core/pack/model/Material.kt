package dev.siro256.mcmod.railwaymod.core.pack.model

import net.minecraft.resources.ResourceLocation
import java.awt.Color

data class Material(
    val name: String,
    val texture: ResourceLocation,
    val ambientColor: Color,
    val diffuseColor: Color,
    val specularColor: Color,
    val specularIndex: Float,
    val transparency: Float
)
