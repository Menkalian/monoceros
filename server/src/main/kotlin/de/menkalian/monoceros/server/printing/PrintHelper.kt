package de.menkalian.monoceros.server.printing

object PrintHelper {
    fun fitRectangles(bigRectangle: RectangleSize, smallRectangle: RectangleSize): List<RectanglePosition> {
        val version1 = fitRectangles(bigRectangle, smallRectangle, Orientation.HORIZONTAL)
        val version2 = fitRectangles(bigRectangle, smallRectangle, Orientation.VERTICAL)

        return if (version1.size >= version2.size) version1 else version2
    }

    private fun fitRectangles(bigRectangle: RectangleSize, smallRectangle: RectangleSize, firstOrientation: Orientation): List<RectanglePosition> {
        val secondOrientation = if(firstOrientation == Orientation.VERTICAL) Orientation.HORIZONTAL else Orientation.VERTICAL
        val toReturn = mutableListOf<RectanglePosition>()

        var currentX: Float
        var currentY = 0.0f
        var maxX = 0.0f
        var resetX = 0.0f

        val iterateWithOrientation = {orientation: Orientation ->
            while (currentY + smallRectangle.getHeight(orientation) <= bigRectangle.height) {
                currentX = resetX
                while (currentX + smallRectangle.getWidth(orientation) <= bigRectangle.width) {
                    toReturn.add(RectanglePosition(currentX, currentY, orientation))
                    currentX += smallRectangle.getWidth(orientation)
                    maxX = maxX.coerceAtLeast(currentX)
                }
                currentY += smallRectangle.getHeight(orientation)
            }
        }

        iterateWithOrientation(firstOrientation)
        resetX = maxX
        currentY = 0.0f
        iterateWithOrientation(secondOrientation)

        return toReturn
    }
}