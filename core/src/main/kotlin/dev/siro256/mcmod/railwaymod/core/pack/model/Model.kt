package dev.siro256.mcmod.railwaymod.core.pack.model

import dev.siro256.mcmod.railwaymod.core.math.vector.Vector2f
import dev.siro256.mcmod.railwaymod.core.math.vector.Vector3f

data class Model(
    val name: String,
    val materials: List<Material>,
    val vertices: List<Vector3f>,
    val uvs: List<Vector2f>,
    val normalVectors: List<Vector3f>,
    val objects: List<ModelObject>
)
