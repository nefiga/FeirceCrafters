package game.graphics;

import game.GameLoop;
import game.util.FileUtil;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public class ShaderManager {

    private int program, vertexShader, fragmentShader;

    public static final String NORMAL_TEXTURE = "game/glsl/texture";

    public ShaderManager() {
        program = glCreateProgram();
    }

    public void attachVertexShader(String name) {
        String vertexShaderSource = FileUtil.readFromFile(name);

        vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, vertexShaderSource);

        glCompileShader(vertexShader);

        if (glGetShaderi(vertexShader, GL_COMPILE_STATUS) ==  GL_FALSE) {
            System.err.println("Error in ShaderManager. Unable to create vertex shader");
            System.err.println(glGetShaderInfoLog(vertexShader, glGetShaderi(vertexShader, GL_INFO_LOG_LENGTH)));
            dispose();
            GameLoop.exit();
        }

        glAttachShader(program, vertexShader);
    }

    public void attachFragmentShader(String name) {
        String fragmentShaderSource = FileUtil.readFromFile(name);

        fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, fragmentShaderSource);

        glCompileShader(fragmentShader);

        if (glGetShaderi(vertexShader, GL_COMPILE_STATUS) ==  GL_FALSE) {
            System.err.println("Error in ShaderManager. Unable to create fragment shader");
            System.err.println(glGetShaderInfoLog(fragmentShader, glGetShaderi(fragmentShader, GL_INFO_LOG_LENGTH)));
            dispose();
            GameLoop.exit();
        }

        glAttachShader(program, fragmentShader);
    }

    public void setUniforms(String name, float... values) {
        if (values.length > 4) {
            System.err.println("Error in ShaderManager. Uniforms cannot have more than 4 values");
            GameLoop.exit();
        }

        // Get the location of the uniforms
        int location = glGetUniformLocation(program, name);

        // Set the uniform values
        switch (values.length) {
            case 1:
                glUniform1f(location, values[0]);
                break;
            case 2:
                glUniform2f(location, values[0], values[1]);
                break;
            case 3:
                glUniform3f(location, values[0], values[1], values[2]);
                break;
            case 4:
                glUniform4f(location, values[0], values[1], values[2], values[3]);
        }
    }

    public void setUniform(String name, FloatBuffer matrix) {
        int location = glGetUniformLocation(program, name);
        glUniformMatrix4(location, false, matrix);
    }

    public void link() {
        glLinkProgram(program);

        if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
            System.err.println("Error in ShaderManager. Unable to link program: ");
            dispose();
            GameLoop.exit();
        }
    }

    public void bind() {
        glUseProgram(program);
    }

    public void unBind() {
        glUseProgram(program);
    }

    public void dispose() {
        unBind();

        glDetachShader(program, vertexShader);
        glDetachShader(program, fragmentShader);

        glDetachShader(program, vertexShader);
        glDetachShader(program, fragmentShader);

        glDeleteProgram(program);
    }
}
