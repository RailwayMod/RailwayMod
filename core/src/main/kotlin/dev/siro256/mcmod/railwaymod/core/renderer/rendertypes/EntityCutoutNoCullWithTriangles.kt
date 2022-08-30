package dev.siro256.mcmod.railwaymod.core.renderer.rendertypes

import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.VertexFormat
import net.minecraft.Util
import net.minecraft.client.renderer.RenderStateShard
import net.minecraft.client.renderer.RenderType
import net.minecraft.resources.ResourceLocation
import java.util.*

private const val name = "entity-cutout-no-cull-with-triangles"
private val vertexFormat = DefaultVertexFormat.NEW_ENTITY
private val vertexFormatMode = VertexFormat.Mode.TRIANGLES
private const val bufferSize = 256
private const val affectCrumbling = true
private const val sortOnUpload = false

class EntityCutoutNoCullWithTriangles private constructor(
    setupState: () -> Unit,
    clearState: () -> Unit
) : RenderType(
    name,
    vertexFormat,
    vertexFormatMode,
    bufferSize,
    affectCrumbling,
    sortOnUpload,
    { setupState() },
    { clearState() }) {

    override fun isOutline(): Boolean = false

    // TODO: we may want to return a filled Optional if we want the entity to glow
    override fun outline(): Optional<RenderType> = Optional.empty()

    companion object {
        val memoized = run {
            val memoizedFunction = Util.memoize { textureResourceLocation: ResourceLocation ->
                val states = createRenderStates(textureResourceLocation)

                EntityCutoutNoCullWithTriangles(
                    { states.forEach(RenderStateShard::setupRenderState) },
                    { states.forEach(RenderStateShard::clearRenderState) }
                )
            };

            { textureResourceLocation: ResourceLocation -> memoizedFunction.apply(textureResourceLocation) }
        }

        private fun createRenderStates(textureResourceLocation: ResourceLocation): List<RenderStateShard> =
            CompositeState(
                textureState = TextureStateShard(textureResourceLocation, false, false),
                shaderState = RENDERTYPE_ENTITY_CUTOUT_NO_CULL_SHADER,
                transparencyState = NO_TRANSPARENCY,
                cullState = NO_CULL,
                lightmapState = LIGHTMAP,
                overlayState = OVERLAY,
            ).states

        private data class CompositeState(
            val textureState: EmptyTextureStateShard = RenderStateShard.NO_TEXTURE,
            private val shaderState: ShaderStateShard = RenderStateShard.NO_SHADER,
            private val transparencyState: TransparencyStateShard = RenderStateShard.NO_TRANSPARENCY,
            private val depthTestState: DepthTestStateShard = RenderStateShard.LEQUAL_DEPTH_TEST,
            private val cullState: CullStateShard = RenderStateShard.CULL,
            private val lightmapState: LightmapStateShard = RenderStateShard.NO_LIGHTMAP,
            private val overlayState: OverlayStateShard = RenderStateShard.NO_OVERLAY,
            private val layeringState: LayeringStateShard = RenderStateShard.NO_LAYERING,
            private val outputState: OutputStateShard = RenderStateShard.MAIN_TARGET,
            private val texturingState: TexturingStateShard = RenderStateShard.DEFAULT_TEXTURING,
            private val writeMaskState: WriteMaskStateShard = RenderStateShard.COLOR_DEPTH_WRITE,
            private val lineState: LineStateShard = RenderStateShard.DEFAULT_LINE,
        ) {
            val states
                get() = listOf(
                    this.textureState,
                    this.shaderState,
                    this.transparencyState,
                    this.depthTestState,
                    this.cullState,
                    this.lightmapState,
                    this.overlayState,
                    this.layeringState,
                    this.outputState,
                    this.texturingState,
                    this.writeMaskState,
                    this.lineState
                )
        }
    }
}
