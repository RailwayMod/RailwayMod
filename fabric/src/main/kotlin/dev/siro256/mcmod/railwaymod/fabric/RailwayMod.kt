package dev.siro256.mcmod.railwaymod.fabric

import dev.siro256.mcmod.railwaymod.core.CreativeTab
import dev.siro256.mcmod.railwaymod.core.Values
import dev.siro256.mcmod.railwaymod.core.block.BlockTest
import dev.siro256.mcmod.railwaymod.core.block.BlockTest2
import dev.siro256.mcmod.railwaymod.core.entity.EntityTest
import dev.siro256.mcmod.railwaymod.core.entity.RailwayModEntityType
import dev.siro256.mcmod.railwaymod.core.item.ItemCreativeTabIcon
import dev.siro256.mcmod.railwaymod.core.item.ItemTestBlock
import dev.siro256.mcmod.railwaymod.core.item.ItemTestBlock2
import dev.siro256.mcmod.railwaymod.core.renderer.RendererTestEntity
import dev.siro256.mcmod.railwaymod.core.util.blockAndItemList
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.MobCategory
import org.apache.logging.log4j.LogManager

class RailwayMod: ModInitializer, ClientModInitializer {
    override fun onInitialize() {
        val logger = LogManager.getLogger("${Values.MOD_ID}/Initialize")

        logger.info("Now: Initialize")

        ItemCreativeTabIcon.let {
            Registry.register(Registry.ITEM, it.identifier, it)
        }

        CreativeTab.creativeTab.set(
            FabricItemGroupBuilder.build(ResourceLocation(Values.MOD_ID, "creative_tab")) {
                ItemCreativeTabIcon.defaultInstance
            }
        )

        with(RailwayModEntityType.TEST) {
            entityType.set(
                Registry.register(
                    Registry.ENTITY_TYPE,
                    resourceLocation,
                    FabricEntityTypeBuilder.create(MobCategory.MISC, ::EntityTest).build()
                )
            )
        }

        blockAndItemList(
            BlockTest to ItemTestBlock,
            BlockTest2 to ItemTestBlock2
        ).forEach {
            Registry.register(Registry.BLOCK, it.first.identifier, it.first)
            Registry.register(Registry.ITEM, it.second.identifier, it.second)
        }
    }

    @Environment(EnvType.CLIENT)
    override fun onInitializeClient() {
        val initializeClientLogger = LogManager.getLogger("${Values.MOD_ID}/InitializeClient")

        initializeClientLogger.info("Now: Initialize client")

        ClientLifecycleEvents.CLIENT_STARTED.register {
            val clientStartedLogger = LogManager.getLogger("${Values.MOD_ID}/ClientStarted")

            clientStartedLogger.info("Now: Client started")

            dev.siro256.mcmod.railwaymod.core.RailwayMod.initialize()
            EntityRendererRegistry.register(RailwayModEntityType.TEST.entityType.get()) { RendererTestEntity(it) }
        }
    }
}
