package dev.siro256.mcmod.railwaymod.core.pack

import dev.siro256.mcmod.railwaymod.core.pack.model.Model
import java.util.Optional

data class Pack(
    val name: String,
    val models: List<Model>
) {
    fun getModel(name: String): Optional<Model> =
        models.find { it.name == name }.let {
            if (it == null) Optional.empty() else Optional.of(it)
        }
}
