package game.util

import game.GameLoop
import game.graphics.ShaderManager

import java.io.BufferedReader
import java.io.InputStreamReader

object FileUtil {

    fun readFromFile(name: String): String {
        val source = StringBuffer()

        try {
            val reader = BufferedReader(InputStreamReader(ShaderManager::class.java.classLoader.getResourceAsStream(name)))

            var line: String?
            line = reader.readLine()
            while (line != null) {
                source.append(line).append("\n")

                line = reader.readLine()
            }

            reader.close()
        } catch (e: Exception) {
            System.err.println("Error loading source code: " + name)
            e.printStackTrace()
            GameLoop.exit()
        }

        return source.toString()
    }

    fun readAllLines(name: String): Array<String> {
        return readFromFile(name).split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    }
}
