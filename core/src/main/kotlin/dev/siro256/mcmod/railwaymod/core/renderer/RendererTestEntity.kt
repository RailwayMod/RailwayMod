package dev.siro256.mcmod.railwaymod.core.renderer

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Vector4f
import dev.siro256.mcmod.railwaymod.core.Values
import dev.siro256.mcmod.railwaymod.core.entity.EntityTest
import dev.siro256.mcmod.railwaymod.core.math.vector.Vector2f
import dev.siro256.mcmod.railwaymod.core.math.vector.Vector3f
import dev.siro256.mcmod.railwaymod.core.pack.model.*
import dev.siro256.mcmod.railwaymod.core.renderer.rendertypes.EntityCutoutNoCullWithTriangles
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.culling.Frustum
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.ResourceLocation
import org.lwjgl.opengl.GL32.*
import java.awt.Color
import java.util.*
import kotlin.properties.Delegates

class RendererTestEntity(context: EntityRendererProvider.Context) : EntityRenderer<EntityTest>(context) {
    override fun getTextureLocation(entity: EntityTest) = ResourceLocation(Values.MOD_ID, "textures/entity/test.png")

    override fun shouldRender(entity: EntityTest, frustum: Frustum, d: Double, e: Double, f: Double): Boolean {
        return super.shouldRender(entity, frustum, d, e, f)
    }

    override fun render(
        entity: EntityTest,
        yaw: Float,
        tickDelta: Float,
        poseStack: PoseStack,
        multiBufferSource: MultiBufferSource,
        light: Int
    ) {
        val bufferBuilder =
            multiBufferSource.getBuffer(EntityCutoutNoCullWithTriangles.memoized(entity.textureLocation))

        model.objects.flatMap { it.surface }.flatMap { it.vertices }.forEach {
            val vertex = model.vertices[it.vertexIndex - 1]
            val uv = model.uvs[it.uvIndex.get() - 1]
            val normalVector = model.normalVectors[it.normalVectorIndex.get() - 1]

            val vertex4f = Vector4f(vertex.x, vertex.y, vertex.z, 1.0F).apply { transform(poseStack.last().pose()) }
            val normal3f = com.mojang.math.Vector3f(normalVector.x, normalVector.y, normalVector.z)
                .apply { transform(poseStack.last().normal()) }

            bufferBuilder.vertex(
                vertex4f.x(), vertex4f.y(), vertex4f.z(),
                1F, 1F, 1F, 1F,
                uv.x, uv.y,
                OverlayTexture.NO_OVERLAY,
                light,
                normal3f.x(), normal3f.y(), normal3f.z()
            )
        }
    }

    companion object {
        var vertexArrayObject by Delegates.notNull<Int>()
        var vertexAttributeBuffer by Delegates.notNull<Int>()
        var numberOfVertices by Delegates.notNull<Int>()
        var indexBuffer by Delegates.notNull<Int>()

        fun init() {
            registerModel()
        }

        private fun registerModel() {
            vertexArrayObject = glGenVertexArrays()
            glBindVertexArray(vertexArrayObject)

            model.objects.flatMap { it.surface }.flatMap { it.vertices }
                .also { numberOfVertices = it.size }
                .flatMap {
                    listOf(
                        model.vertices[it.vertexIndex - 1].toList(),
                        model.uvs[it.uvIndex.get() - 1].toList(),
                        //model.normalVectors[it.normalVectorIndex.get() - 1].toList()
                    ).flatten()
                }.run {
                    vertexAttributeBuffer = glGenBuffers()
                    glBindBuffer(GL_ARRAY_BUFFER, vertexAttributeBuffer)
                    glBufferData(GL_ARRAY_BUFFER, this.toFloatArray(), GL_STATIC_DRAW)
                }

            indexBuffer = glGenBuffers()
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer)
            glBufferData(
                GL_ELEMENT_ARRAY_BUFFER,
                IntArray(numberOfVertices).apply { for (i in 0 until numberOfVertices) this[i] = i },
                GL_STATIC_DRAW
            )

//            vertexAttributeBuffer = glGenBuffers()
//            glBindBuffer(GL_ARRAY_BUFFER, vertexAttributeBuffer)
//            glBufferData(
//                GL_ARRAY_BUFFER,
//                //頂点左上から右回り
//                floatArrayOf(
//                    0.0F, 1.0F, 0.0F, 0.0F, 0.0F,
//                    1.0F, 1.0F, 0.0F, 1.0F, 0.0F,
//                    1.0F, 0.0F, 0.0F, 1.0F, 1.0F,
//                    0.0F, 0.0F, 0.0F, 0.0F, 1.0F
//                ),
//                GL_STATIC_DRAW
//            )
//            numberOfVertices = 6

//            indexBuffer = glGenBuffers()
//            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer)
//            glBufferData(
//                GL_ELEMENT_ARRAY_BUFFER,
//                intArrayOf(
//                    0, 1, 2,
//                    2, 3, 0
//                ),
//                GL_STATIC_DRAW
//            )

            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0)
            glBindBuffer(GL_ARRAY_BUFFER, 0)
            glBindVertexArray(0)
        }

        private val model = Model(
            "testEntity",
            listOf(
                Material(
                    "Material.001",
                    ResourceLocation(Values.MOD_ID, "textures/entity/test.png"),
                    Color(1.0F, 1.0F, 1.0F),
                    Color(0.8F, 0.8F, 0.8F),
                    Color(0.5F, 0.5F, 0.5F),
                    250.0F,
                    1.0F
                )
            ),
            listOf(
                Vector3f(1.250000F, 0.000000F, -1.000000F),
                Vector3f(1.250000F, 2.000000F, -1.000000F),
                Vector3f(1.250000F, 0.000000F, 1.000000F),
                Vector3f(1.250000F, 2.000000F, 1.000000F),
                Vector3f(-1.250000F, 0.000000F, -1.000000F),
                Vector3f(-1.250000F, 2.000000F, -1.000000F),
                Vector3f(-1.250000F, 0.000000F, 1.000000F),
                Vector3f(-1.250000F, 2.000000F, 1.000000F),
                Vector3f(1.250000F, 1.000000F, -0.500000F),
                Vector3f(1.250000F, 2.000000F, -0.500000F),
                Vector3f(1.250000F, 2.000000F, 0.500000F),
                Vector3f(1.250000F, 1.000000F, 0.500000F),
                Vector3f(-1.250000F, 2.000000F, -0.500000F),
                Vector3f(-1.250000F, 1.000000F, 0.500000F),
                Vector3f(-1.250000F, 2.000000F, 0.500000F),
                Vector3f(-1.250000F, 1.000000F, -0.500000F)
            ),
            listOf(
                Vector2f(0.509766F, 0.509766F),
                Vector2f(0.509766F, 0.283203F),
                Vector2f(0.623047F, 0.453125F),
                Vector2f(0.509766F, 0.736328F),
                Vector2f(0.226562F, 0.509766F),
                Vector2f(0.226562F, 0.283203F),
                Vector2f(0.113281F, 0.339844F),
                Vector2f(0.226562F, 0.056641F),
                Vector2f(0.509766F, 0.056641F),
                Vector2f(0.226562F, 0.000000F),
                Vector2f(0.509766F, 0.000000F),
                Vector2f(0.226562F, 0.736328F),
                Vector2f(0.509766F, 0.792969F),
                Vector2f(0.226562F, 0.792969F),
                Vector2f(0.792969F, 0.623047F),
                Vector2f(0.509766F, 0.509766F),
                Vector2f(0.791992F, 0.509766F),
                Vector2f(0.792969F, 0.736328F),
                Vector2f(0.510742F, 0.849609F),
                Vector2f(0.509766F, 0.736328F),
                Vector2f(0.509766F, 0.623047F),
                Vector2f(0.736328F, 0.453125F),
                Vector2f(0.736328F, 0.509766F),
                Vector2f(0.736328F, 0.283203F),
                Vector2f(0.623047F, 0.339844F),
                Vector2f(0.736328F, 0.339844F),
                Vector2f(0.000000F, 0.339844F),
                Vector2f(0.000000F, 0.283203F),
                Vector2f(0.000000F, 0.509766F),
                Vector2f(0.113281F, 0.453125F),
                Vector2f(0.000000F, 0.453125F),
                Vector2f(0.792969F, 0.849609F)
            ),
            listOf(
                Vector3f(1.0000F, 0.0000F, 0.0000F),
                Vector3f(0.0000F, 0.0000F, 1.0000F),
                Vector3f(-1.0000F, 0.0000F, 0.0000F),
                Vector3f(0.0000F, 0.0000F, -1.0000F),
                Vector3f(0.0000F, -1.0000F, 0.0000F),
                Vector3f(0.0000F, 1.0000F, 0.0000F)
            ),
            listOf(
                ModelObject(
                    "Cube_Cube.001",
                    Optional.of("Material.001"),
                    listOf(
                        Surface(
                            listOf(
                                Vertex(
                                    3,
                                    Optional.of(1),
                                    Optional.of(1)
                                ),
                                Vertex(
                                    1,
                                    Optional.of(2),
                                    Optional.of(1)
                                ),
                                Vertex(
                                    12,
                                    Optional.of(3),
                                    Optional.of(1)
                                )
                            )
                        ),
                        Surface(
                            listOf(
                                Vertex(
                                    4,
                                    Optional.of(4),
                                    Optional.of(2)
                                ),
                                Vertex(
                                    7,
                                    Optional.of(5),
                                    Optional.of(2)
                                ),
                                Vertex(
                                    3,
                                    Optional.of(1),
                                    Optional.of(2)
                                )
                            )
                        ),
                        Surface(
                            listOf(
                                Vertex(
                                    5,
                                    Optional.of(6),
                                    Optional.of(3)
                                ),
                                Vertex(
                                    7,
                                    Optional.of(5),
                                    Optional.of(3)
                                ),
                                Vertex(
                                    16,
                                    Optional.of(7),
                                    Optional.of(3)
                                )
                            )
                        ),
                        Surface(
                            listOf(
                                Vertex(
                                    6,
                                    Optional.of(8),
                                    Optional.of(4)
                                ),
                                Vertex(
                                    1,
                                    Optional.of(2),
                                    Optional.of(4)
                                ),
                                Vertex(
                                    5,
                                    Optional.of(6),
                                    Optional.of(4)
                                )
                            )
                        ),
                        Surface(
                            listOf(
                                Vertex(
                                    7,
                                    Optional.of(5),
                                    Optional.of(5)
                                ),
                                Vertex(
                                    1,
                                    Optional.of(2),
                                    Optional.of(5)
                                ),
                                Vertex(
                                    3,
                                    Optional.of(1),
                                    Optional.of(5)
                                )
                            )
                        ),
                        Surface(
                            listOf(
                                Vertex(
                                    2,
                                    Optional.of(9),
                                    Optional.of(6)
                                ),
                                Vertex(
                                    13,
                                    Optional.of(10),
                                    Optional.of(6)
                                ),
                                Vertex(
                                    10,
                                    Optional.of(11),
                                    Optional.of(6)
                                )
                            )
                        ),
                        Surface(
                            listOf(
                                Vertex(
                                    8,
                                    Optional.of(12),
                                    Optional.of(6)
                                ),
                                Vertex(
                                    11,
                                    Optional.of(13),
                                    Optional.of(6)
                                ),
                                Vertex(
                                    15,
                                    Optional.of(14),
                                    Optional.of(6)
                                )
                            )
                        ),
                        Surface(
                            listOf(
                                Vertex(
                                    12,
                                    Optional.of(15),
                                    Optional.of(4)
                                ),
                                Vertex(
                                    15,
                                    Optional.of(16),
                                    Optional.of(4)
                                ),
                                Vertex(
                                    11,
                                    Optional.of(17),
                                    Optional.of(4)
                                )
                            )
                        ),
                        Surface(
                            listOf(
                                Vertex(
                                    9,
                                    Optional.of(18),
                                    Optional.of(2)
                                ),
                                Vertex(
                                    13,
                                    Optional.of(19),
                                    Optional.of(2)
                                ),
                                Vertex(
                                    16,
                                    Optional.of(20),
                                    Optional.of(2)
                                )
                            )
                        ),
                        Surface(
                            listOf(
                                Vertex(
                                    9,
                                    Optional.of(18),
                                    Optional.of(6)
                                ),
                                Vertex(
                                    14,
                                    Optional.of(21),
                                    Optional.of(6)
                                ),
                                Vertex(
                                    12,
                                    Optional.of(15),
                                    Optional.of(6)
                                )
                            )
                        ),
                        Surface(
                            listOf(
                                Vertex(
                                    12,
                                    Optional.of(3),
                                    Optional.of(1)
                                ),
                                Vertex(
                                    11,
                                    Optional.of(22),
                                    Optional.of(1)
                                ),
                                Vertex(
                                    4,
                                    Optional.of(23),
                                    Optional.of(1)
                                )
                            )
                        ),
                        Surface(
                            listOf(
                                Vertex(
                                    1,
                                    Optional.of(2),
                                    Optional.of(1)
                                ),
                                Vertex(
                                    2,
                                    Optional.of(24),
                                    Optional.of(1)
                                ),
                                Vertex(
                                    9,
                                    Optional.of(25),
                                    Optional.of(1)
                                )
                            )
                        ),
                        Surface(
                            listOf(
                                Vertex(
                                    2,
                                    Optional.of(24),
                                    Optional.of(1)
                                ),
                                Vertex(
                                    10,
                                    Optional.of(26),
                                    Optional.of(1)
                                ),
                                Vertex(
                                    9,
                                    Optional.of(25),
                                    Optional.of(1)
                                )
                            )
                        ),
                        Surface(
                            listOf(
                                Vertex(
                                    12,
                                    Optional.of(3),
                                    Optional.of(1)
                                ),
                                Vertex(
                                    4,
                                    Optional.of(23),
                                    Optional.of(1)
                                ),
                                Vertex(
                                    3,
                                    Optional.of(1),
                                    Optional.of(1)
                                )
                            )
                        ),
                        Surface(
                            listOf(
                                Vertex(
                                    1,
                                    Optional.of(2),
                                    Optional.of(1)
                                ),
                                Vertex(
                                    9,
                                    Optional.of(25),
                                    Optional.of(1)
                                ),
                                Vertex(
                                    12,
                                    Optional.of(3),
                                    Optional.of(1)
                                )
                            )
                        ),
                        Surface(
                            listOf(
                                Vertex(
                                    4,
                                    Optional.of(4),
                                    Optional.of(2)
                                ),
                                Vertex(
                                    8,
                                    Optional.of(12),
                                    Optional.of(2)
                                ),
                                Vertex(
                                    7,
                                    Optional.of(5),
                                    Optional.of(2)
                                )
                            )
                        ),
                        Surface(
                            listOf(
                                Vertex(
                                    13,
                                    Optional.of(27),
                                    Optional.of(3)
                                ),
                                Vertex(
                                    6,
                                    Optional.of(28),
                                    Optional.of(3)
                                ),
                                Vertex(
                                    16,
                                    Optional.of(7),
                                    Optional.of(3)
                                )
                            )
                        ),
                        Surface(
                            listOf(
                                Vertex(
                                    6,
                                    Optional.of(28),
                                    Optional.of(3)
                                ),
                                Vertex(
                                    5,
                                    Optional.of(6),
                                    Optional.of(3)
                                ),
                                Vertex(
                                    16,
                                    Optional.of(7),
                                    Optional.of(3)
                                )
                            )
                        ),
                        Surface(
                            listOf(
                                Vertex(
                                    7,
                                    Optional.of(5),
                                    Optional.of(3)
                                ),
                                Vertex(
                                    8,
                                    Optional.of(29),
                                    Optional.of(3)
                                ),
                                Vertex(
                                    14,
                                    Optional.of(30),
                                    Optional.of(3)
                                )
                            )
                        ),
                        Surface(
                            listOf(
                                Vertex(
                                    8,
                                    Optional.of(29),
                                    Optional.of(3)
                                ),
                                Vertex(
                                    15,
                                    Optional.of(31),
                                    Optional.of(3)
                                ),
                                Vertex(
                                    14,
                                    Optional.of(30),
                                    Optional.of(3)
                                )
                            )
                        ),
                        Surface(
                            listOf(
                                Vertex(
                                    7,
                                    Optional.of(5),
                                    Optional.of(3)
                                ),
                                Vertex(
                                    14,
                                    Optional.of(30),
                                    Optional.of(3)
                                ),
                                Vertex(
                                    16,
                                    Optional.of(7),
                                    Optional.of(3)
                                )
                            )
                        ),
                        Surface(
                            listOf(
                                Vertex(
                                    6,
                                    Optional.of(8),
                                    Optional.of(4)
                                ),
                                Vertex(
                                    2,
                                    Optional.of(9),
                                    Optional.of(4)
                                ),
                                Vertex(
                                    1,
                                    Optional.of(2),
                                    Optional.of(4)
                                )
                            )
                        ),
                        Surface(
                            listOf(
                                Vertex(
                                    7,
                                    Optional.of(5),
                                    Optional.of(5)
                                ),
                                Vertex(
                                    5,
                                    Optional.of(6),
                                    Optional.of(5)
                                ),
                                Vertex(
                                    1,
                                    Optional.of(2),
                                    Optional.of(5)
                                )
                            )
                        ),
                        Surface(
                            listOf(
                                Vertex(
                                    2,
                                    Optional.of(9),
                                    Optional.of(6)
                                ),
                                Vertex(
                                    6,
                                    Optional.of(8),
                                    Optional.of(6)
                                ),
                                Vertex(
                                    13,
                                    Optional.of(10),
                                    Optional.of(6)
                                )
                            )
                        ),
                        Surface(
                            listOf(
                                Vertex(
                                    8,
                                    Optional.of(12),
                                    Optional.of(6)
                                ),
                                Vertex(
                                    4,
                                    Optional.of(4),
                                    Optional.of(6)
                                ),
                                Vertex(
                                    11,
                                    Optional.of(13),
                                    Optional.of(6)
                                )
                            )
                        ),
                        Surface(
                            listOf(
                                Vertex(
                                    12,
                                    Optional.of(15),
                                    Optional.of(4)
                                ),
                                Vertex(
                                    14,
                                    Optional.of(21),
                                    Optional.of(4)
                                ),
                                Vertex(
                                    15,
                                    Optional.of(16),
                                    Optional.of(4)
                                )
                            )
                        ),
                        Surface(
                            listOf(
                                Vertex(
                                    9,
                                    Optional.of(18),
                                    Optional.of(2)
                                ),
                                Vertex(
                                    10,
                                    Optional.of(32),
                                    Optional.of(2)
                                ),
                                Vertex(
                                    13,
                                    Optional.of(19),
                                    Optional.of(2)
                                )
                            )
                        ),
                        Surface(
                            listOf(
                                Vertex(
                                    9,
                                    Optional.of(18),
                                    Optional.of(6)
                                ),
                                Vertex(
                                    16,
                                    Optional.of(20),
                                    Optional.of(6)
                                ),
                                Vertex(
                                    14,
                                    Optional.of(21),
                                    Optional.of(6)
                                )
                            )
                        )
                    )
                )
            )
        )
    }
}
