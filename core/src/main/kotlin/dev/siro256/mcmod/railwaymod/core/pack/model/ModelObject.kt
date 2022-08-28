package dev.siro256.mcmod.railwaymod.core.pack.model

import java.util.*

data class ModelObject(
    val name: String,
    val material: Optional<String>,
    val surface: List<Surface>
)
