package game.graphics

import game.GameLoop
import game.util.FileUtil

import java.nio.FloatBuffer

import org.lwjgl.opengl.GL11.GL_FALSE
import org.lwjgl.opengl.GL20.*

class ShaderManager {

    private val program: Int
    private var vertexShader: Int = 0
    private var fragmentShader: Int = 0

    init {
        program = glCreateProgram()
    }

    fun attachVertexShader(name: String) {
        val vertexShaderSource = FileUtil.readFromFile(name)

        vertexShader = glCreateShader(GL_VERTEX_SHADER)
        glShaderSource(vertexShader, vertexShaderSource)

        glCompileShader(vertexShader)

        if (glGetShaderi(vertexShader, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Error in ShaderManager. Unable to create vertex shader")
            System.err.println(glGetShaderInfoLog(vertexShader, glGetShaderi(vertexShader, GL_INFO_LOG_LENGTH)))
            dispose()
            GameLoop.exit()
        }

        glAttachShader(program, vertexShader)
    }

    fun attachFragmentShader(name: String) {
        val fragmentShaderSource = FileUtil.readFromFile(name)

        fragmentShader = glCreateShader(GL_FRAGMENT_SHADER)
        glShaderSource(fragmentShader, fragmentShaderSource)

        glCompileShader(fragmentShader)

        if (glGetShaderi(vertexShader, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Error in ShaderManager. Unable to create fragment shader")
            System.err.println(glGetShaderInfoLog(fragmentShader, glGetShaderi(fragmentShader, GL_INFO_LOG_LENGTH)))
            dispose()
            GameLoop.exit()
        }

        glAttachShader(program, fragmentShader)
    }

    fun setUniforms(name: String, vararg values: Float) {
        if (values.size > 4) {
            System.err.println("Error in ShaderManager. Uniforms cannot have more than 4 values")
            GameLoop.exit()
        }

        // Get the location of the uniforms
        val location = glGetUniformLocation(program, name)

        // Set the uniform values
        when (values.size) {
            1 -> glUniform1f(location, values[0])
            2 -> glUniform2f(location, values[0], values[1])
            3 -> glUniform3f(location, values[0], values[1], values[2])
            4 -> glUniform4f(location, values[0], values[1], values[2], values[3])
        }
    }

    fun setUniform(name: String, matrix: FloatBuffer) {
        val location = glGetUniformLocation(program, name)
        glUniformMatrix4(location, false, matrix)
    }

    fun link() {
        glLinkProgram(program)

        if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
            System.err.println("Error in ShaderManager. Unable to link program: ")
            dispose()
            GameLoop.exit()
        }
    }

    fun bind() {
        glUseProgram(program)
    }

    fun unBind() {
        glUseProgram(program)
    }

    fun dispose() {
        unBind()

        glDetachShader(program, vertexShader)
        glDetachShader(program, fragmentShader)

        glDetachShader(program, vertexShader)
        glDetachShader(program, fragmentShader)

        glDeleteProgram(program)
    }

    companion object {

        val NORMAL_TEXTURE = "game/glsl/texture"
    }
}
