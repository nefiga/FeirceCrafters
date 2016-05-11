package game.graphics

import javax.imageio.ImageIO
import java.awt.*
import java.awt.image.BufferedImage
import java.io.IOException

object ImageManager {

    fun getImage(fileName: String): BufferedImage {
        try {
            return ImageIO.read(ImageManager::class.java.getResourceAsStream(fileName + ".png"))
        } catch (e: IOException) {
            throw RuntimeException("Image did not load: " + fileName)
        }

    }

    fun combineImages(vararg images: BufferedImage): BufferedImage {
        val width = images[0].width
        val height = images[0].height
        val returnImage = BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR)

        val g = returnImage.graphics
        for (i in images.indices) {
            g.drawImage(images[i], 0, 0, null)
        }

        return returnImage
    }
}
