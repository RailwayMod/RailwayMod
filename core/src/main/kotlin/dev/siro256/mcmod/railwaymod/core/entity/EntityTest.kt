package dev.siro256.mcmod.railwaymod.core.entity

import dev.siro256.mcmod.railwaymod.core.Values
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level

class EntityTest(entityType: EntityType<EntityTest>, level: Level,): IdentifiedEntity(entityType, level){
    override val identifier: ResourceLocation = Companion.identifier
    val textureLocation = ResourceLocation(Values.MOD_ID, "textures/entity/test/test.png")

    override fun defineSynchedData() {
        entityData.define(entityDataTag.RIGHT_CLICK_COUNT.accessor, 0)
    }

    override fun addAdditionalSaveData(compoundTag: CompoundTag) {
        with(entityDataTag.RIGHT_CLICK_COUNT) { compoundTag.putInt(tagKey, entityData.get(accessor) as Int) }
    }

    override fun readAdditionalSaveData(compoundTag: CompoundTag) {
        with(entityDataTag.RIGHT_CLICK_COUNT) { entityData.set(accessor, compoundTag.getInt(tagKey)) }
    }

    override fun getAddEntityPacket() = ClientboundAddEntityPacket(this)

    companion object {
        val identifier = ResourceLocation(Values.MOD_ID, "test")

        private val entityDataTag = object {
            val RIGHT_CLICK_COUNT = EntityData(
                "right_click_count",
                SynchedEntityData.defineId(EntityTest::class.java, EntityDataSerializers.INT)
            )
        }
    }

    private data class EntityData<T>(val tagKey: String, val accessor: EntityDataAccessor<T>)
}
