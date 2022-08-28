package dev.siro256.mcmod.railwaymod.core.pack.model

import java.util.Optional

data class Vertex(
    val vertexIndex: Int,
    val uvIndex: Optional<Int>,
    val normalVectorIndex: Optional<Int>
)
