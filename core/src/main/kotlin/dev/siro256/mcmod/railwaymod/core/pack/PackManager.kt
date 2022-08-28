package dev.siro256.mcmod.railwaymod.core.pack

import java.util.Optional

object PackManager {
    private val packs = mutableListOf<Pack>()

    fun getPack(name: String): Optional<Pack> =
        packs.find { it.name == name }.let {
            if (it == null) Optional.empty() else Optional.of(it)
        }

    fun addPack(pack: Pack) = packs.add(pack)
}
