package dev.siro256.mcmod.railwaymod.core.render

import net.minecraft.resources.ResourceLocation
import org.apache.logging.log4j.LogManager
import org.lwjgl.opengl.GL32.*
import java.util.Optional

object RenderDataManager {
    private val logger = LogManager.getLogger("RailwayMod/RenderDataManager")

    private val shaders = HashMap<ResourceLocation, Int>()

    fun registerShader(identifier: ResourceLocation, vertexShaderSource: String, fragmentShaderSource: String): Boolean {
        if (shaders.containsKey(identifier)) {
            logger.error("The shader identified by $identifier already registered.")
            return false
        }

        val vertexShader = getShaderId(GL_VERTEX_SHADER)
            .getOrElse {
                logger.error("Failed to get vertex shader ID: ${it.message}")
                return false
            }
        glShaderSource(vertexShader, vertexShaderSource)
        glCompileShader(vertexShader)

        val fragmentShader = getShaderId(GL_FRAGMENT_SHADER)
            .getOrElse {
                logger.error("Failed to get fragment shader ID: ${it.message}")
                return false
            }
        glShaderSource(fragmentShader, fragmentShaderSource)
        glCompileShader(fragmentShader)

        val programId = getProgramId()
            .getOrElse {
                logger.error("Failed to get program ID: ${it.message}")
                return false
            }
        glAttachShader(programId, vertexShader)
        glAttachShader(programId, fragmentShader)

        glLinkProgram(programId)

        if (glGetProgrami(programId, GL_LINK_STATUS) == GL_FALSE) {
            logger.error("Failed to link program object:\n${glGetProgramInfoLog(programId)}")
            return false
        }

        shaders[identifier] = programId
        return true
    }

    fun getShader(identifier: ResourceLocation) =
        when (val shader = shaders[identifier]) {
            null -> Optional.empty()
            else -> Optional.of(shader)
        }

    private fun getShaderId(type: Int): Result<Int> {
        val id = glCreateShader(type)

        if (glGetError() == GL_INVALID_ENUM)
            return Result.failure(IllegalArgumentException("Type $type is not an accepted value."))

        return when (id) {
            0 -> Result.failure(IllegalStateException("An error occurred while creating shader object."))
            else -> Result.success(id)
        }
    }

    private fun getProgramId() =
        when (val id = glCreateProgram()) {
            0 -> Result.failure(IllegalStateException("An error occurred while creating program object."))
            else -> Result.success(id)
        }
}
