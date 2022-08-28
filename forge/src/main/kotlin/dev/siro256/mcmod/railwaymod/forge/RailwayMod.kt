package dev.siro256.mcmod.railwaymod.forge

import dev.siro256.mcmod.railwaymod.core.Values
import dev.siro256.mcmod.railwaymod.core.block.BlockTest
import dev.siro256.mcmod.railwaymod.core.block.BlockTest2
import dev.siro256.mcmod.railwaymod.core.entity.EntityTest
import dev.siro256.mcmod.railwaymod.core.entity.RailwayModEntityType
import dev.siro256.mcmod.railwaymod.core.item.ItemCreativeTabIcon
import dev.siro256.mcmod.railwaymod.core.item.ItemTestBlock
import dev.siro256.mcmod.railwaymod.core.item.ItemTestBlock2
import dev.siro256.mcmod.railwaymod.core.renderer.RendererTestEntity
import dev.siro256.mcmod.railwaymod.core.util.blockList
import dev.siro256.mcmod.railwaymod.core.util.itemList
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.EntityRenderersEvent
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import org.apache.logging.log4j.LogManager

@Suppress("unused")
@Mod(Values.MOD_ID)
@EventBusSubscriber(modid = Values.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
class RailwayMod {
    companion object {
        @JvmStatic
        @SubscribeEvent
        fun registerBlocks(event: RegistryEvent.Register<Block>) {
            val logger = LogManager.getLogger("${Values.MOD_ID}/RegisterBlocks")

            logger.info("Now: Register blocks")

            blockList(
                BlockTest,
                BlockTest2,
            ).forEach {
                event.registry.register((it as Block).setRegistryName(it.identifier))
                logger.debug("Register ${it.identifier}")
            }
        }

        @JvmStatic
        @SubscribeEvent
        fun registerItems(event: RegistryEvent.Register<Item>) {
            val logger = LogManager.getLogger("${Values.MOD_ID}/RegisterItems")

            logger.info("Now: Register items")

            ItemCreativeTabIcon.let {
                event.registry.register((it as Item).setRegistryName(it.identifier))
                logger.debug("Register ${it.identifier}")
            }

            dev.siro256.mcmod.railwaymod.core.CreativeTab.creativeTab.set(CreativeTab)

            itemList(
                ItemTestBlock,
                ItemTestBlock2
            ).forEach {
                event.registry.register((it as Item).setRegistryName(it.identifier))
                logger.debug("Register ${it.identifier}")
            }
        }

        @JvmStatic
        @SubscribeEvent
        fun registerEntities(event: RegistryEvent.Register<EntityType<*>>) {
            with(RailwayModEntityType.TEST) {
                entityType.set(
                    EntityType.Builder.of(::EntityTest, MobCategory.MISC)
                        .build(resourceLocation.toString())
                        .apply { registryName = resourceLocation }
                )
                event.registry.register(entityType.get())
            }
        }
    }
}

@EventBusSubscriber(modid = Values.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = [Dist.CLIENT])
class ClientEventHandler {
    companion object {
        @JvmStatic
        @SubscribeEvent
        fun registerEntityRenderers(event: EntityRenderersEvent.RegisterRenderers) {
            val logger = LogManager.getLogger("${Values.MOD_ID}/RegisterEntityRenderers")

            logger.info("Now: Register entity renderers")

            dev.siro256.mcmod.railwaymod.core.RailwayMod.initialize()
            event.registerEntityRenderer(RailwayModEntityType.TEST.entityType.get(), ::RendererTestEntity)
        }
    }
}
